package com.parcellocker.parcelhandlerservice.service.impl;


import com.parcellocker.parcelhandlerservice.kafka.Producer;
import com.parcellocker.parcelhandlerservice.model.Box;
import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.*;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLocker;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ParcelServiceImpl implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private ParcelLockerServiceImpl parcelLockerService;

    @Autowired
    private BoxServiceImpl boxService;

    @Autowired
    private Producer producer;

    @Autowired
    private CourierServiceImpl courierService;


    @Override
    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    @Override
    public Parcel findById(Long id) {
        return parcelRepository.findById(id).get();
    }

    @Override
    public void save(Parcel parcel) {
        parcelRepository.save(parcel);

    }

    //Csomag küldése feladási kód nélkül
    @Override
    public ResponseEntity<?> sendParcelWithoutCode(ParcelSendingWithoutCodeRequest request, Long senderParcelLockerId) {

        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);
        ParcelLocker receiverParcelLocker = parcelLockerService.findById(request.getSelectedParcelLockerId());
        StringResponse stringResponse = new StringResponse();
        ParcelSendingWithoutCodeResponse response = new ParcelSendingWithoutCodeResponse();

        //Feladási automata csomagjai
        Set<Parcel> parcelsInSenderParcelLocker = senderParcelLocker.getParcels();

        //A kiválasztott mérethez tartozó rekeszek
        List<Box> allBoxesInSelectedSize = boxService.findBySize(request.getParcelSize());

        //Feladási automata teli rekeszei
        List<Box> fullBoxes = new ArrayList<>();

        for(Parcel p : parcelsInSenderParcelLocker){
            fullBoxes.add(p.getBox());
        }

        //Üres rekeszek keresése
        List<Box> emptyBoxes = new ArrayList<>();

        for(Box b : allBoxesInSelectedSize){
            if (!fullBoxes.contains(b)) {
                emptyBoxes.add(b);
            }
        }

        //Csomaghoz rekesz hozzárendelése
        //A szabad rekeszek elérhetőségét már frontend oldalon ellenőrzöm. Ha idáig eljut a kérés, akkor van szabad rekesz.
        //Viszont lehetőség van a csomagküldő honlapjáról is csomagot feladni.
        //Ezért elvileg előfordulhat az, hogy az ügyfél elkezdi a csomagfeladást az automatánál, az elején még vannak
        //szabad rekeszek. De miközben kitölti az adatokat, adnak fel csomagot a honlapról, ezért azok a rekeszek betelnek.
        //Ezért itt is ellenőrizni kell a szabad rekeszek elérhetőségét.
        if(emptyBoxes.isEmpty()){
            //Minden rekesz tele
            stringResponse.setMessage("full");
            return ResponseEntity.ok(stringResponse);
        }
        else{
            parcel.setBox(emptyBoxes.get(0));
        }

        //Csomag változóinak beállítása
        parcel.setUniqueParcelId(generateRandomString(10));
        parcel.setUser(null);
        parcel.setSenderName(request.getSenderName());
        parcel.setSenderEmailAddress(request.getSenderEmailAddress());
        parcel.setShippingFrom(senderParcelLocker);
        parcel.setShippingTo(receiverParcelLocker);
        parcel.setSize(request.getParcelSize());
        parcel.setPrice(request.getPrice());
        parcel.setReceiverName(request.getReceiverName());
        parcel.setReceiverEmailAddress(request.getReceiverEmailAddress());
        parcel.setShipped(false);
        parcel.setPickedUp(false);

        LocalDate currentDate = LocalDate.now();
        parcel.setSendingDate(currentDate);

        LocalTime currentTime = LocalTime.now();
        parcel.setSendingTime(currentTime);

        parcel.setShippingDate(null);
        parcel.setShippingTime(null);
        parcel.setPickingUpDate(null);
        parcel.setPickingUpTime(null);

        parcel.setPlaced(true);

        //Ha a csomag ára 0, akkor már ki van fizetve. Különben nincs
        if(request.getPrice() == 0){
            parcel.setPaid(true);
        }

        parcel.setPaid(false);
        parcel.setStore(null);
        parcel.setCourier(null);
        parcel.setSendingCode(null);
        parcel.setPickingUpCode(generateRandomString(5));

        //Csomag és csomag automata összerendlése
        senderParcelLocker.getParcels().add(parcel);
        parcel.setParcelLocker(senderParcelLocker);

        save(parcel);
        parcelLockerService.save(senderParcelLocker);


        //Email küldése a feladónak
        //Értesítési objektum küldése a(z) ("parcelSendingNotificationForSender") topicnak

        ParcelSendingNotification notification = new ParcelSendingNotification();

        notification.setReceiverName(request.getReceiverName());
        notification.setSenderName(request.getSenderName());
        notification.setSenderEmailAddress(request.getSenderEmailAddress());
        notification.setReceiverEmailAddress(request.getReceiverEmailAddress());
        notification.setPrice(request.getPrice());
        notification.setUniqueParcelId(parcel.getUniqueParcelId());
        notification.setSenderParcelLockerPostCode(senderParcelLocker.getLocation().getPostCode());
        notification.setSenderParcelLockerCity(senderParcelLocker.getLocation().getCity());
        notification.setSenderParcelLockerStreet(senderParcelLocker.getLocation().getStreet());

        notification.setReceiverParcelLockerPostCode(receiverParcelLocker.getLocation().getPostCode());
        notification.setReceiverParcelLockerCity(receiverParcelLocker.getLocation().getCity());
        notification.setReceiverParcelLockerStreet(receiverParcelLocker.getLocation().getStreet());
        notification.setSendingDate(currentDate.toString());
        notification.setSendingTime(currentTime.toString());

        producer.sendNotificationForSender(notification);

        //Email küldése az átvevőnek
        //Értesítési objektum küldése a(z) ("parcelSendingNotificationForReceiver") topicnak
        producer.sendNotificationForReceiver(notification);


        response.setMessage("successSending");
        response.setBoxNumber(parcel.getBox().getBoxNumber());
        return ResponseEntity.ok(response);
    }

    //Csomagok lekérése, amik készen állnak az elszállításra
    @Override
    public ResponseEntity<List<GetParcelsForShippingResponse>> getParcelsForShipping(Long senderParcelLockerId) {

        List<GetParcelsForShippingResponse> response = new ArrayList<>();

        for(Parcel parcel : getReadyParcelsForShipping(senderParcelLockerId)){

            GetParcelsForShippingResponse responseObject = new GetParcelsForShippingResponse();

            responseObject.setUniqueParcelId(parcel.getUniqueParcelId());
            response.add(responseObject);
        }

        return ResponseEntity.ok(response);
    }

    //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
    //Jwt token szükséges
    @Override
    public ResponseEntity<StringResponse> emptyParcelLocker(EmptyParcelLocker request) {

        Courier courier = courierService.findByUniqueCourierId(request.getUniqueCourierId());

        for(Parcel parcel : getReadyParcelsForShipping(request.getParcelLockerId())){
            //Csomag és futár összerendelése
            courier.getParcels().add(parcel);
            parcel.setCourier(courier);

            //Csomag és csomag automata összerendelés megszüntetése
            parcel.setParcelLocker(null);

            save(parcel);
            courierService.save(courier);
        }

        return null;
    }

    //Random string generálása
    public String generateRandomString(int length) {

        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for(int i = 0; i < length; i++) {

            int index = random.nextInt(alphaNumeric.length());

            char randomChar = alphaNumeric.charAt(index);

            sb.append(randomChar);
        }

        String randomString = sb.toString();

        //Ide lehet kell egy rekurzivitás

        return randomString;
    }

    //Automatában megtalálható csomagok keresése. Ezek a csomagok készen állnak az elszállításra
    public List<Parcel> getReadyParcelsForShipping(Long senderParcelLockerId){

        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);

        Set<Parcel> parcels = senderParcelLocker.getParcels();

        List<Parcel> readyParcels = new ArrayList<>();

        for(Parcel parcel : parcels){
            if(parcel.isShipped() == false && parcel.isPlaced() == true && parcel.isPickedUp() == false){
                readyParcels.add(parcel);
            }
        }
        return readyParcels;
    }
}
