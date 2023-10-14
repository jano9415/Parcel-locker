package com.parcellocker.parcelhandlerservice.service.impl;


import com.parcellocker.parcelhandlerservice.kafka.Producer;
import com.parcellocker.parcelhandlerservice.model.*;
import com.parcellocker.parcelhandlerservice.payload.*;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelPickingUpNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelSendingFromWebPageNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelShippingNotification;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.parcelhandlerservice.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.parcellocker.parcelhandlerservice.payload.response.*;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StoreServiceImpl storeService;

    @Autowired
    private WebClient.Builder webClientBuilder;


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

    //Csomag törlése
    @Override
    public void delete(Parcel parcel) {

        parcelRepository.delete(parcel);
    }

    //Keresés átvételi kód szerint
    @Override
    public Parcel findByPickingUpCode(String pickingUpCode) {
        return parcelRepository.findByPickingUpCode(pickingUpCode);
    }

    //Csomag küldése feladási kód nélkül
    @Override
    public ResponseEntity<?> sendParcelWithoutCode(ParcelSendingWithoutCodeRequest request, Long senderParcelLockerId) {

        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);
        ParcelLocker receiverParcelLocker = parcelLockerService.findById(request.getSelectedParcelLockerId());
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

        //Itt lehetne ellenőrizni, hogy az automatában van-e hely
        //De az már megtörtént küldés előtt
        //Frontenden csak akkor enged küldeni, ha van szabad hely
        /*if(emptyBoxes == null){
            response.setMessage("parcelLockerIsFull");
            return ResponseEntity.ok(response);
        }*/

        //Csomaghoz rekesz hozzárendelése
        parcel.setBox(emptyBoxes.get(0));

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

        //parcel.setSendingDate(currentDate());
        parcel.setSendingDate(date1());

        //parcel.setSendingTime(currentTime());
        parcel.setSendingTime(time1());

        parcel.setShippingDate(null);
        parcel.setShippingTime(null);
        parcel.setPickingUpDate(null);
        parcel.setPickingUpTime(null);

        parcel.setPlaced(true);

        //Ha a csomag ára 0, akkor már ki van fizetve. Különben nincs
        if(request.getPrice() == 0){
            parcel.setPaid(true);
        }
        else{
            parcel.setPaid(false);
        }

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
        notification.setSendingDate(currentDate().toString());
        notification.setSendingTime(currentTime().toString());

        producer.sendNotificationForSender(notification);

        //Email küldése az átvevőnek
        //Értesítési objektum küldése a(z) ("parcelSendingNotificationForReceiver") topicnak
        producer.sendNotificationForReceiver(notification);


        response.setMessage("successSending");
        response.setBoxNumber(parcel.getBox().getBoxNumber());
        return ResponseEntity.ok(response);
    }

    //Csomagok lekérése az automatából, amik készen állnak az elszállításra
    @Override
    public ResponseEntity<List<GetParcelsForShippingResponse>> getParcelsForShipping(Long senderParcelLockerId) {

        List<GetParcelsForShippingResponse> response = new ArrayList<>();

        for(Parcel parcel : getReadyParcelsForShipping(senderParcelLockerId)){

            GetParcelsForShippingResponse responseObject = new GetParcelsForShippingResponse();

            responseObject.setUniqueParcelId(parcel.getUniqueParcelId());
            responseObject.setPrice(parcel.getPrice());

            responseObject.setSenderParcelLockerPostCode(parcel.getShippingFrom().getLocation().getPostCode());
            responseObject.setSenderParcelLockerCounty(parcel.getShippingFrom().getLocation().getCounty());
            responseObject.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
            responseObject.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());

            responseObject.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
            responseObject.setReceiverParcelLockerCounty(parcel.getShippingTo().getLocation().getCounty());
            responseObject.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
            responseObject.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());

            responseObject.setBoxNumber(parcel.getBox().getBoxNumber());


            response.add(responseObject);
        }

        return ResponseEntity.ok(response);
    }

    //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
    //Jwt token szükséges
    @Override
    public ResponseEntity<List<EmptyParcelLockerResponse>> emptyParcelLocker(EmptyParcelLockerRequest request) {

        Courier courier = courierService.findByUniqueCourierId(request.getUniqueCourierId());

        List<EmptyParcelLockerResponse> response = new ArrayList<>();

        for(Parcel parcel : getReadyParcelsForShipping(request.getParcelLockerId())){
            EmptyParcelLockerResponse boxNumber = new EmptyParcelLockerResponse();
            boxNumber.setBoxNumber(parcel.getBox().getBoxNumber());
            response.add(boxNumber);

            //Csomagnak nincs rekesz száma
            parcel.setBox(null);

            //Csomag és futár összerendelése
            courier.getParcels().add(parcel);
            parcel.setCourier(courier);

            //Csomag és csomag automata összerendelés megszüntetése
            parcel.setParcelLocker(null);


            //Dátum és időpont, amikor a futár kiveszi a csomagot
            //parcel.setPickingUpDateFromParcelLockerByCourier(currentDate());
            parcel.setPickingUpDateFromParcelLockerByCourier(date2());
            //parcel.setPickingUpTimeFromParcelLockerByCourier(currentTime());
            parcel.setPickingUpTimeFromParcelLockerByCourier(time2());

            save(parcel);
            courierService.save(courier);
        }

        return ResponseEntity.ok(response);
    }

    //Futárnál lévő csomagok lekérése. Csak olyan csomagok, amik az adott automatához tartoznak és van nekik szabad rekesz
    //Jwt token szükséges
    @Override
    public ResponseEntity<List<GetParcelsForParcelLockerResponse>>getParcelsForParcelLocker(Long senderParcelLockerId, String uniqueCourierId) {

        //Automata, amit fel szeretne tölteni a futár
        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);

        //Futár és nála lévő csomagok
        Courier courier = courierService.findByUniqueCourierId(uniqueCourierId);
        Set<Parcel> parcelsOfCourier = courier.getParcels();

        List<GetParcelsForParcelLockerResponse> response = new ArrayList<>();

        List<Parcel> parcelsForParcelLocker = new ArrayList<>();


        //Automata teli rekeszei
        List<Box> fullBoxes = new ArrayList<>();

        //Automata csomagjai
        Set<Parcel> parcelsInSenderParcelLocker = senderParcelLocker.getParcels();

        for(Parcel parcel : parcelsOfCourier){
            //Ha a csomag érkezési helye ez az automata
            if(parcel.getShippingTo().getId() == senderParcelLockerId){

                //A csomag méretéhez tartozó összes rekesz
                List<Box> allBoxesInSelectedSize = boxService.findBySize(parcel.getSize());

                //Teli rekeszek keresése
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

                //Ha van szabad rekesz
                if(!emptyBoxes.isEmpty()){
                    GetParcelsForParcelLockerResponse responseObj = new GetParcelsForParcelLockerResponse();
                    responseObj.setUniqueParcelId(parcel.getUniqueParcelId());
                    responseObj.setBoxNumber(emptyBoxes.get(0).getBoxNumber());
                    responseObj.setPrice(parcel.getPrice());

                    responseObj.setSenderParcelLockerPostCode(parcel.getShippingFrom().getLocation().getPostCode());
                    responseObj.setSenderParcelLockerCounty(parcel.getShippingFrom().getLocation().getCounty());
                    responseObj.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
                    responseObj.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());

                    responseObj.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
                    responseObj.setReceiverParcelLockerCounty(parcel.getShippingTo().getLocation().getCounty());
                    responseObj.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
                    responseObj.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());

                    fullBoxes.add(emptyBoxes.get(0));

                    response.add(responseObj);
                }
            }
        }

        return ResponseEntity.ok(response);
    }

    //Automata feltöltése
    //Ez a kérés csak akkor van meghívva, ha a futárnak vannak csomagjai az adott automatához, és azoknak van hely
    //Frontend oldalon a gomb aktív vagy inaktív
    //Ezt tartalmazza az előző függvény
    //Jwt token szükséges
    //Visszatérés a csomagazonosítókkal és a rekesz számokkal
    @Override
    public ResponseEntity<List<FillParcelLockerResponse>> fillParcelLocker(Long senderParcelLockerId, String uniqueCourierId) {

        //Automata, amit fel szeretne tölteni a futár
        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);

        //Futár és nála lévő csomagok
        Courier courier = courierService.findByUniqueCourierId(uniqueCourierId);
        Set<Parcel> parcelsOfCourier = courier.getParcels();

        List<FillParcelLockerResponse> response = new ArrayList<>();

        List<Parcel> parcelsForParcelLocker = new ArrayList<>();

        for(Parcel parcel : parcelsOfCourier){
            //Ha a csomag érkezési helye ez az automata és a csomag átvételi ideje még nem járt le.
            //Így elkerülhető, hogy olyan csomagot tegyen be, amit már a központi raktárba kellene szállítani
            if(parcel.getShippingTo().getId() == senderParcelLockerId && parcel.isPickingUpExpired() == false){

                //Automata csomagjai
                Set<Parcel> parcelsInSenderParcelLocker = senderParcelLocker.getParcels();

                //A csomag méretéhez tartozó összes rekesz
                List<Box> allBoxesInSelectedSize = boxService.findBySize(parcel.getSize());

                //Automata teli rekeszei
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

                //Ha van szabad rekesz
                if(!emptyBoxes.isEmpty()){

                    //Csomag paramétereinek frissítése
                    //Csomag el van helyezve
                    //Elhelyezési dátum és időpont
                    parcel.setShipped(true);
                    //parcel.setShippingDate(currentDate());
                    parcel.setShippingDate(date5());

                    //parcel.setShippingTime(currentTime());
                    parcel.setShippingTime(time5());

                    //Átvételi lejárati dátum. Amikor a futár elhelyezi a csomagot az automatába + három nap
                    parcel.setPickingUpExpirationDate(currentDate().plusDays(3));
                    //Átvételi lejárati időpont. Megegyezik az elhelyezési időponttal
                    parcel.setPickingUpExpirationTime(currentTime());


                    //Csomag és automata összerendlése
                    parcel.setParcelLocker(senderParcelLocker);
                    senderParcelLocker.getParcels().add(parcel);

                    //Csomag éa futár összerendelés megszüntetése
                    parcel.setCourier(null);

                    //Csomaghoz rekesz hozzáadása
                    parcel.setBox(emptyBoxes.get(0));

                    save(parcel);
                    parcelLockerService.save(senderParcelLocker);

                    //Email küldés
                    //Értesítési objektum kafka számára
                    ParcelShippingNotification notification = new ParcelShippingNotification();

                    notification.setReceiverName(parcel.getReceiverName());
                    notification.setReceiverEmailAddress(parcel.getReceiverEmailAddress());
                    notification.setPrice(parcel.getPrice());
                    notification.setUniqueParcelId(parcel.getUniqueParcelId());
                    notification.setSenderParcelLockerPostCode(parcel.getShippingFrom().getLocation().getPostCode());
                    notification.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
                    notification.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());

                    notification.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
                    notification.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
                    notification.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());
                    notification.setShippingDate(currentDate().toString());
                    notification.setShippingTime(currentTime().toString());

                    notification.setPickingUpCode(parcel.getPickingUpCode());

                    if(parcel.getUser() == null){

                        notification.setSenderName(parcel.getSenderName());
                        notification.setSenderEmailAddress(parcel.getSenderEmailAddress());
                    }
                    else{
                        notification.setSenderName(parcel.getUser().getLastName() + " " + parcel.getUser().getFirstName());
                        notification.setSenderEmailAddress(parcel.getUser().getEmailAddress());
                    }

                    //Email küldése az feladójának
                    //Értesítési objektum küldése a(z) ("parcelShippingNotificationForSender") topicnak
                    producer.sendShippingNotificationForSender(notification);
                    //Email küldése az átvevőnek
                    //Értesítési objektum küldése a(z) ("parcelShippingNotificationForReceiver") topicnak
                    producer.sendShippingNotificationForReceiver(notification);


                    //Válasz objektum
                    FillParcelLockerResponse responseObj = new FillParcelLockerResponse();
                    responseObj.setUniqueParcelId(parcel.getUniqueParcelId());
                    responseObj.setBoxNumber(emptyBoxes.get(0).getBoxNumber());
                    response.add(responseObj);

                }

            }
        }

        return ResponseEntity.ok(response);
    }

    //Csomag átvétele
    @Override
    public ResponseEntity<PickUpParcelResponse> pickUpParcel(String pickingUpCode, Long senderParcelLockerId) {

        Parcel parcel = findByPickingUpCode(pickingUpCode);
        PickUpParcelResponse response = new PickUpParcelResponse();

        //Nincs ilyen csomag
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //Nem ebben az automatában van
        if(senderParcelLockerId != parcel.getShippingTo().getId()){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //A csomag átvételi ideje már lejárt
        if(isPickingUpDateTimeExpired(parcel)){
            response.setMessage("expired");
            return ResponseEntity.ok(response);
        }

        //Már át van véve
        if(parcel.isPickedUp()){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }


        //Válasz objektum
        response.setBoxNumber(parcel.getBox().getBoxNumber());
        response.setPrice(parcel.getPrice());

        //Ha a csomag ki van fizetve, akkor nincs több tennivaló. Át lehet venni
        if(parcel.getPrice() == 0 && parcel.isPaid()){

            response.setMessage("pickedUp");

            //Adatbázis frissítése és értesítési email küldése
            updateDbAfterPickUpParcel(pickingUpCode,senderParcelLockerId);
            //Csomag objektum küldése a statictics service-nek
            sendParcelToStatisticsService(parcel);

            return ResponseEntity.ok(response);
        }

        //A csomagot átvétel előtt még ki kell fizetni
        response.setMessage("notPickedUp");
        return ResponseEntity.ok(response);
    }

    //Csomag átvétele fizetés után. Adatbázis frissítése.
    //Nem szükséges jwt token
    @Override
    public ResponseEntity<StringResponse> pickUpParcelAfterPayment(String pickingUpCode, Long senderParcelLockerId) {

        Parcel parcel = findByPickingUpCode(pickingUpCode);


        //Csomagadatok frissítése az adatbázisban
        updateDbAfterPickUpParcel(pickingUpCode,senderParcelLockerId);

        //Csomag objektum küldése a statictics service-nek
        sendParcelToStatisticsService(parcel);

        StringResponse response = new StringResponse();
        response.setMessage("pickedUp");
        return ResponseEntity.ok(response);
    }

    //Keresés feladási kód szerint
    @Override
    public Parcel findBySendingCode(String sendingCode) {
        return parcelRepository.findBySendingCode(sendingCode);
    }

    //Keresés feladási kód szerint
    //Ha van csomag és a feladási automata megegyezik a kérésben érkező feladási automatával, akkor visszatérek
    //a rekesz számával
    //Különben a csomag nem található
    @Override
    public ResponseEntity<GetParcelForSendingWithCodeResponse> getParcelForSendingWithCode(String sendingCode, Long senderParcelLockerId) {
        Parcel parcel = findBySendingCode(sendingCode);

        GetParcelForSendingWithCodeResponse response = new GetParcelForSendingWithCodeResponse();

        //Csomag nem található
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);

        }
        //Van csomag, de azt nem ebbe az automatába kell elhelyezni
        if(parcel.getShippingFrom().getId() != senderParcelLockerId){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);

        }
        //Csomag már el van helyezve
        if(parcel.isPlaced()){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        response.setMessage("found");
        response.setBoxNumber(parcel.getBox().getBoxNumber());
        return ResponseEntity.ok(response);
    }

    //Csomag küldése feladási kóddal
    //Az előző kérésben már ellenőrizve lett, hogy megtalálható a csomag
    //Ez a kérés már fizetés után van
    //Itt már csak frissítem a csomag adatait és elküldöm az email értesítéseket
    //Nem szükséges jwt token
    @Override
    public ResponseEntity<StringResponse> sendParcelWithCode(String sendingCode, Long senderParcelLockerId) {

        Parcel parcel = findBySendingCode(sendingCode);
        ParcelLocker senderParcelLocker = parcelLockerService.findById(parcel.getShippingFrom().getId());
        ParcelLocker receiverParcelLocker = parcelLockerService.findById(parcel.getShippingTo().getId());
        StringResponse response = new StringResponse();

        //Csomag adatainak frissítése
        //Amit frissíteni kell: a csomag el van helyezve, feladási dátum és időpont
        //parcel.setSendingDate(currentDate());
        parcel.setSendingDate(date1());

        //parcel.setSendingTime(currentTime());
        parcel.setSendingTime(time1());

        parcel.setPlaced(true);

        save(parcel);



        //Email küldése a feladónak
        //Értesítési objektum küldése a(z) ("parcelSendingNotificationForSender") topicnak

        ParcelSendingNotification notification = new ParcelSendingNotification();

        notification.setReceiverName(parcel.getReceiverName());
        notification.setSenderName(parcel.getUser().getLastName() + " " + parcel.getUser().getFirstName());
        notification.setSenderEmailAddress(parcel.getUser().getEmailAddress());
        notification.setReceiverEmailAddress(parcel.getReceiverEmailAddress());
        notification.setPrice(parcel.getPrice());
        notification.setUniqueParcelId(parcel.getUniqueParcelId());
        notification.setSenderParcelLockerPostCode(senderParcelLocker.getLocation().getPostCode());
        notification.setSenderParcelLockerCity(senderParcelLocker.getLocation().getCity());
        notification.setSenderParcelLockerStreet(senderParcelLocker.getLocation().getStreet());

        notification.setReceiverParcelLockerPostCode(receiverParcelLocker.getLocation().getPostCode());
        notification.setReceiverParcelLockerCity(receiverParcelLocker.getLocation().getCity());
        notification.setReceiverParcelLockerStreet(receiverParcelLocker.getLocation().getStreet());
        notification.setSendingDate(currentDate().toString());
        notification.setSendingTime(currentTime().toString());

        producer.sendNotificationForSender(notification);

        //Email küldése az átvevőnek
        //Értesítési objektum küldése a(z) ("parcelSendingNotificationForReceiver") topicnak
        producer.sendNotificationForReceiver(notification);


        response.setMessage("successSending");
        return ResponseEntity.ok(response);
    }

    //Csomag küldése a weblapról feladási kóddal
    //Ez még csak egy előzetes csomagfeladás. A felhasználó megkapja email-ben a csomagfeladási kódot
    //A végleges csomagfeladás az automatánál történik
    //Jwt token szükséges
    @Override
    public ResponseEntity<StringResponse> sendParcelWithCodeFromWebpage(SendParcelWithCodeFromWebpageRequest request) {

        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = parcelLockerService.findById(request.getParcelLockerFromId());
        ParcelLocker receiverParcelLocker = parcelLockerService.findById(request.getParcelLockerToId());
        StringResponse response = new StringResponse();
        User user = userService.findByEmailAddress(request.getSenderEmailAddress());

        //Feladási automata csomagjai
        Set<Parcel> parcelsInSenderParcelLocker = senderParcelLocker.getParcels();

        //A kiválasztott mérethez tartozó rekeszek
        List<Box> allBoxesInSelectedSize = boxService.findBySize(request.getSize());

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

        //Itt még meg lehetne vizsgálni, hogy az emptyBoxes lista nem üres-e.
        //De ha frontendről ideáig eljut a kérés, akkor biztos hogy van szabad rekesz
        //Mert ha nincs, akkor nem is jelenik meg frontend oldalon

        //Csomaghoz rekesz hozzárendelése
        parcel.setBox(emptyBoxes.get(0));

        //Csomag változóinak beállítása
        parcel.setUniqueParcelId(generateRandomString(10));
        parcel.setUser(user);
        parcel.setSenderName(null);
        parcel.setSenderEmailAddress(null);
        parcel.setShippingFrom(senderParcelLocker);
        parcel.setShippingTo(receiverParcelLocker);
        parcel.setSize(request.getSize());
        parcel.setPrice(request.getPrice());
        parcel.setReceiverName(request.getReceiverName());
        parcel.setReceiverEmailAddress(request.getReceiverEmailAddress());
        parcel.setShipped(false);
        parcel.setPickedUp(false);

        //Az adatbázisban még nem jelenik meg a küldési dátum. Majd akkor, ha már ténylegesen feladta az automatánál
        //Ez a dátum csak az email értesítéshez kell
        LocalDate currentDate = LocalDate.now();
        parcel.setSendingDate(null);

        //Az adatbázisban még nem jelenik meg a küldési időpont. Majd akkor, ha már ténylegesen feladta az automatánál
        //Ez az időpont csak az email értesítéshez kell
        LocalTime currentTime = LocalTime.now();
        parcel.setSendingTime(null);

        parcel.setShippingDate(null);
        parcel.setShippingTime(null);
        parcel.setPickingUpDate(null);
        parcel.setPickingUpTime(null);

        //Fontos, hogy ebben az esetben nincs elhelyezve a csomag
        parcel.setPlaced(false);

        //Ha a csomag ára 0, akkor már ki van fizetve. Különben nincs
        if(request.getPrice() == 0){
            parcel.setPaid(true);
        }
        else{
            parcel.setPaid(false);
        }

        parcel.setStore(null);
        parcel.setCourier(null);
        //Szükség van egy feladási kódra
        parcel.setSendingCode(generateRandomString(5));
        //Átvételi kód
        parcel.setPickingUpCode(generateRandomString(5));


        //Átvételi lejárati dátum és idő. Ez majd akkor lesz megadva, amikor a futár elhelyezi az automatába
        parcel.setPickingUpDate(null);
        parcel.setPickingUpTime(null);
        parcel.setPickedUp(false);

        //Feladási lejárati dátum és idő
        //A dátum a feladási dátum + három nap
        //A lejárati időpont minden csomag esetnén 5:29
        //A rendszer automatikusan minden nap 5:30-kor törli az aznap lejárt előzetes feladásokat
        parcel.setSendingExpirationDate(currentDate.plusDays(3));
        //Megegyezik a feladási időpnttal
        parcel.setSendingExpirationTime(LocalTime.of(5,29));
        parcel.setSendingExpired(false);

        //Csomag és csomag automata összerendlése
        senderParcelLocker.getParcels().add(parcel);
        parcel.setParcelLocker(senderParcelLocker);

        save(parcel);
        parcelLockerService.save(senderParcelLocker);


        //Email küldése a feladónak
        //Értesítési objektum küldése a(z) ("parcelSendingFromWebPageNotification") topicnak

        ParcelSendingFromWebPageNotification notification = new ParcelSendingFromWebPageNotification();

        notification.setSenderName(user.getLastName() + " " + user.getFirstName());
        notification.setSenderEmailAddress(user.getEmailAddress());
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
        notification.setSendingCode(parcel.getSendingCode());

        producer.parcelSendingFromWebPageNotification(notification);



        response.setMessage("successSending");
        return ResponseEntity.ok(response);
    }

    //Csomag követése
    //Nem szükséges jwt token
    @Override
    public ResponseEntity<FollowParcelResponse> followParcel(String uniqueParcelId) {
        Parcel parcel = findByUniqueParcelId(uniqueParcelId);
        FollowParcelResponse response = new FollowParcelResponse();

        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        response.setShippingFromPostCode(parcel.getShippingFrom().getLocation().getPostCode());
        response.setShippingFromCounty(parcel.getShippingFrom().getLocation().getCounty());
        response.setShippingFromCity(parcel.getShippingFrom().getLocation().getCity());
        response.setShippingFromStreet(parcel.getShippingFrom().getLocation().getStreet());

        response.setShippingToPostCode(parcel.getShippingTo().getLocation().getPostCode());
        response.setShippingToCounty(parcel.getShippingTo().getLocation().getCounty());
        response.setShippingToCity(parcel.getShippingTo().getLocation().getCity());
        response.setShippingToStreet(parcel.getShippingTo().getLocation().getStreet());

        if(parcel.getStore() != null){
            response.setStorePostCode(parcel.getStore().getAddress().getPostCode());
            response.setStoreCounty(parcel.getStore().getAddress().getCounty());
            response.setStoreCity(parcel.getStore().getAddress().getCity());
            response.setStoreStreet(parcel.getStore().getAddress().getStreet());
        }

        response.setShipped(parcel.isShipped());
        response.setPickedUp(parcel.isPickedUp());

        if(parcel.getSendingDate() != null){
            response.setSendingDate(parcel.getSendingDate().toString());
            response.setSendingTime(parcel.getSendingTime().toString());
        }


        if(parcel.getPickingUpDate() != null){
            response.setPickingUpDate(parcel.getPickingUpDate().toString());
            response.setPickingUpTime(parcel.getPickingUpTime().toString());
        }

        if(parcel.getShippingDate() != null){
            response.setShippingDate(parcel.getShippingDate().toString());
            response.setShippingTime(parcel.getShippingTime().toString());
        }



        response.setPlaced(parcel.isPlaced());
        response.setPaid(parcel.isPaid());

        if(parcel.getPickingUpExpirationDate() != null){
            response.setPickingUpExpirationDate(parcel.getPickingUpExpirationDate().toString());
            response.setPickingUpExpirationTime(parcel.getPickingUpExpirationTime().toString());
        }

        response.setPickedUp(parcel.isPickedUp());

        if(parcel.getSendingExpirationDate() != null){
            response.setSendingExpirationDate(parcel.getSendingExpirationDate().toString());
            response.setSendingExpirationTime(parcel.getSendingExpirationTime().toString());
        }

        if(parcel.getUser() != null){
            response.setSenderName(parcel.getUser().getLastName() + " " + parcel.getUser().getFirstName());
            response.setSenderEmailAddress(parcel.getUser().getEmailAddress());
        }
        else{
            response.setSenderName(parcel.getSenderName());
            response.setSenderEmailAddress(parcel.getSenderEmailAddress());
        }

        response.setSendingExpired(parcel.isSendingExpired());
        response.setPickingUpExpired(parcel.isPickingUpExpired());


        //Futár kiveszi a csomagot időpont
        if(parcel.getPickingUpDateFromParcelLockerByCourier() != null){
            response.setPickingUpDateFromParcelLockerByCourier(parcel.getPickingUpDateFromParcelLockerByCourier().toString());
            response.setPickingUpTimeFromParcelLockerByCourier(parcel.getPickingUpTimeFromParcelLockerByCourier().toString());
        }

        //Futár elhelyezi a csomagot a raktárba időpont
        if(parcel.getHandingDateToFirstStoreByCourier() != null){
            response.setHandingDateToFirstStoreByCourier(parcel.getHandingDateToFirstStoreByCourier().toString());
            response.setHandingTimeToFirstStoreByCourier(parcel.getHandingTimeToFirstStoreByCourier().toString());
        }

        //Futár felveszi a csomagot a raktárból időpont
        if(parcel.getPickingUpDateFromSecondStoreByCourier() != null){
            response.setPickingUpDateFromSecondStoreByCourier(parcel.getPickingUpDateFromSecondStoreByCourier().toString());
            response.setPickingUpTimeFromSecondStoreByCourier(parcel.getPickingUpTimeFromSecondStoreByCourier().toString());
        }

        return ResponseEntity.ok(response);
    }

    //Keresés egyedi csomagazonosító szerint
    @Override
    public Parcel findByUniqueParcelId(String uniqueParcelId) {
        return parcelRepository.findByUniqueParcelId(uniqueParcelId);
    }

    //Futár lead egy csomagot a központi raktárban
    //Jwt token szükséges
    @Override
    public ResponseEntity<StringResponse> handParcelToStore(String uniqueCourierId, String uniqueParcelId) {

        Courier courier = courierService.findByUniqueCourierId(uniqueCourierId);
        Parcel parcel = findByUniqueParcelId(uniqueParcelId);
        Store store = courier.getArea();

        StringResponse response = new StringResponse();

        //Csomag nem található
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //Dátum és időpont, amikor a futár leadja a csomagot
        //parcel.setHandingDateToFirstStoreByCourier(currentDate());
        parcel.setHandingDateToFirstStoreByCourier(date3());
        //parcel.setHandingTimeToFirstStoreByCourier(currentTime());
        parcel.setHandingTimeToFirstStoreByCourier(time3());

        //Csomag leadása
        //Csomag és futár összerendelés megszüntetése. Kapcsolótábla frissítése
        parcel.setCourier(null);
        //Csomag és raktár összerendelése. Kapcsolótábla frissítése
        parcel.setStore(store);
        store.getParcels().add(parcel);
        save(parcel);
        storeService.save(store);

        response.setMessage("successHand");

        return ResponseEntity.ok(response);
    }

    //Futár felvesz egy csomagot a központi raktárból
    //Jwt token szükséges
    @Override
    public ResponseEntity<StringResponse> pickUpParcelFromStore(String uniqueCourierId, String uniqueParcelId) {

        Courier courier = courierService.findByUniqueCourierId(uniqueCourierId);
        Parcel parcel = findByUniqueParcelId(uniqueParcelId);
        Store store = courier.getArea();

        StringResponse response = new StringResponse();

        //Csomag nem található
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //Dátum és időpont, amikor a futár leadja a csomagot
        //parcel.setPickingUpDateFromSecondStoreByCourier(currentDate());
        parcel.setPickingUpDateFromSecondStoreByCourier(date4());
        //parcel.setPickingUpTimeFromSecondStoreByCourier(currentTime());
        parcel.setPickingUpTimeFromSecondStoreByCourier(time4());

        //Csomag felvétele
        //Futár és csomag összerendelése. Kapcsolótábla frissítése
        parcel.setCourier(courier);
        courier.getParcels().add(parcel);
        //Raktár és csomag összerendelés megszüntetése. Kapcsolótábla frissítése
        parcel.setStore(null);
        save(parcel);
        courierService.save(courier);


        response.setMessage("successPickUp");

        return ResponseEntity.ok(response);
    }

    //Felhasználó csomagjainak lekérése
    @Override
    public ResponseEntity<?> getParcelsOfUser(String emailAddress, String type) {

        User user = userService.findByEmailAddress(emailAddress);
        List<ParcelDTO> response = new ArrayList<>();

        //User nem található
        //Bár frontenden a csomagjait csak akkor tudja lekérni a user, ha be van jelentkezve
        //Szóval mindenképp találni fog usert
        if(user == null){
            StringResponse stringResponse = new StringResponse();
            stringResponse.setMessage("userNotFound");
            return ResponseEntity.ok(stringResponse);

        }

        //all - összes csomag
        if(type.equals("all")){
            for(Parcel parcel : user.getParcels()){
                response.add(parcelToParcelDTO(parcel));
            }
        }

        //reserved - online feladott, automatában még nem elhelyezett csomagok
        if(type.equals("reserved")){
            for(Parcel parcel : user.getParcels()){
                if(parcel.getSendingCode() != null && parcel.isPlaced() == false){
                    response.add(parcelToParcelDTO(parcel));
                }

            }

        }
        //notPickedUp - még át nem vett csomagok. Szállítás alatti csomagok
        if(type.equals("notPickedUp")){
            for(Parcel parcel : user.getParcels()){
                if(parcel.isPlaced() && parcel.isPickedUp() == false){
                    response.add(parcelToParcelDTO(parcel));
                }

            }

        }
        //pickedUp - átvett csomagok. Sikeresen lezárt küldések
        if(type.equals("pickedUp")){
            for(Parcel parcel : user.getParcels()){
                if(parcel.isPickedUp()){
                    response.add(parcelToParcelDTO(parcel));
                }

            }

        }


        return ResponseEntity.ok(response);
    }

    //Csomag törlése
    //Felhasználó kitörli az előzetes csomagfeladást
    @Override
    public ResponseEntity<StringResponse> deleteMyParcel(Long parcelId) {

        Parcel parcel = findById(parcelId);
        StringResponse response = new StringResponse();

        //Ha a csomag nem található. Bár ha a kérés idáig eljut, akkor a frontenden a felhasználó
        // be van jelentkezve és megjelentek a csomagok, amelyeket ki tud törölni
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //Csomag és automata kapcsolótábla frissítése
        parcel.setParcelLocker(null);
        save(parcel);

        //Csomag törlése
        delete(parcel);

        response.setMessage("successfulDeleting");
        return ResponseEntity.ok(response);
    }

    //Központi raktárak csomagjainak lekérése
    @Override
    public ResponseEntity<?> getParcelsOfStore(Long storeId) {

        List<ParcelDTO> response = new ArrayList<>();
        StringResponse stringResponse = new StringResponse();
        Store store = storeService.findById(storeId);

        //Nem valószínű, mert a frontenden kiválasztja a megjelenített központi raktárat
        if(store == null){
            stringResponse.setMessage("notFound");
            ResponseEntity.ok(stringResponse);
        }

        for(Parcel parcel : store.getParcels()){
            response.add(parcelToParcelDTO(parcel));
        }


        return ResponseEntity.ok(response);
    }

    //Csomag átvételi ideje lejárt, ezért az a központi raktárban van
    //Csomag újraindítása az automatához
    //pickingUpExpired mező módosítása. True vagy false
    @Override
    public ResponseEntity<StringResponse> updatePickingUpExpired(Long parcelId) {

        Parcel parcel = findById(parcelId);
        StringResponse response = new StringResponse();

        //Nem valószínű, mert a csomagok meg vannak jelenítve az admin számára a fonrontend oldalon
        if(parcel == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);

        }

        parcel.setPickingUpExpired(false);
        save(parcel);

        response.setMessage("successfulUpdating");
        return ResponseEntity.ok(response);
    }

    //Futár csomagjainak lekérése
    @Override
    public ResponseEntity<?> getParcelsOfCourier(Long courierId) {

        List<ParcelDTO> response = new ArrayList<>();
        StringResponse stringResponse = new StringResponse();
        Courier courier = courierService.findById(courierId);

        //Nem valószínű, mert a frontenden kiválasztja a megjelenített futárokat
        if(courier == null){
            stringResponse.setMessage("notFound");
            ResponseEntity.ok(stringResponse);
        }

        for(Parcel parcel : courier.getParcels()){
            response.add(parcelToParcelDTO(parcel));
        }


        return ResponseEntity.ok(response);
    }

    //Automata csomagjainak lekérése
    @Override
    public ResponseEntity<?> getParcelsOfParcelLocker(Long parcelLockerId) {

        List<ParcelDTO> response = new ArrayList<>();
        StringResponse stringResponse = new StringResponse();
        ParcelLocker parcelLocker = parcelLockerService.findById(parcelLockerId);

        //Nem valószínű, mert a frontenden kiválasztja a megjelenített automatákat
        if(parcelLocker == null){
            stringResponse.setMessage("notFound");
            ResponseEntity.ok(stringResponse);
        }

        for(Parcel parcel : parcelLocker.getParcels()){
            response.add(parcelToParcelDTO(parcel));
        }


        return ResponseEntity.ok(response);
    }

    //Csomagátvételi lejárati idő meghosszabbítása
    @Override
    public ResponseEntity<StringResponse> updatePickingUpExpirationDate(Long parcelId, String newDate) {

        Parcel parcel = findById(parcelId);
        StringResponse response = new StringResponse();

        //Nem valószínű, mert frontenden megjelennek a csomagok
        if(parcel == null){
            response.setMessage("notFound");
            ResponseEntity.ok(response);
        }

        //Frontend csak azt jeleníti meg, ami nem null
        //De azért egy kis plusz validáció
        if(parcel.getPickingUpExpirationDate() == null){
            response.setMessage("expirationDateIsNull");
            ResponseEntity.ok(response);
        }

        parcel.setPickingUpExpirationDate(LocalDate.parse(newDate));
        save(parcel);
        response.setMessage("successFulUpdating");
        return ResponseEntity.ok(response);
    }

    //Csomagfeladási lejárati idő meghosszabbítása
    @Override
    public ResponseEntity<StringResponse> updateSendingExpirationDate(Long parcelId, String newDate) {

        Parcel parcel = findById(parcelId);
        StringResponse response = new StringResponse();

        //Nem valószínű, mert frontenden megjelennek a csomagok
        if(parcel == null){
            response.setMessage("notFound");
            ResponseEntity.ok(response);
        }

        //Frontend csak azt jeleníti meg, ami nem null
        //De azért egy kis plusz validáció
        if(parcel.getSendingExpirationDate() == null){
            response.setMessage("expirationDateIsNull");
            ResponseEntity.ok(response);
        }

        parcel.setSendingExpirationDate(LocalDate.parse(newDate));
        save(parcel);
        response.setMessage("successFulUpdating");
        return ResponseEntity.ok(response);
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
    //Még nincs leszállítva, el van helyezve, nincs átvéve, a csomag érkezési automatája nem ez az automata
    //Azok a csomagok is átkerülnek a futárhoz, ami már ahhoz az automatához le lett szállítva,
    //de az ügyfél nem vette át. Tehát lejárt az átvételi időpont
    public List<Parcel> getReadyParcelsForShipping(Long senderParcelLockerId){

        ParcelLocker senderParcelLocker = parcelLockerService.findById(senderParcelLockerId);

        Set<Parcel> parcels = senderParcelLocker.getParcels();

        List<Parcel> readyParcels = new ArrayList<>();

        for(Parcel parcel : parcels){

            //Csomagok, amiket el kell szállítani majd az érkezési automatához
            if(parcel.isShipped() == false && parcel.isPlaced() == true && parcel.isPickedUp() == false
            && parcel.getShippingTo().getId() != senderParcelLockerId){
                readyParcels.add(parcel);
            }



            //Csomag ami már ide lett szállítva, de lejárt az átvételi dátum
            if(isPickingUpDateTimeExpired(parcel)){
                parcel.setPickingUpExpired(true);
                parcel.setShippingDate(null);
                parcel.setShippingTime(null);
                save(parcel);

                readyParcels.add(parcel);
            }

        }
        return readyParcels;
    }

    //Csomag átvétele utáni adatbázis frissítés
    public void updateDbAfterPickUpParcel(String pickingUpCode, Long senderParcelLockerId){

        Parcel parcel = findByPickingUpCode(pickingUpCode);

        //Átvétel dátuma
        parcel.setPickingUpDate(currentDate());
        parcel.setPickingUpTime(currentTime());

        //Email értesítés objektum
        ParcelPickingUpNotification notification = new ParcelPickingUpNotification();
        notification.setReceiverName(parcel.getReceiverName());
        notification.setReceiverEmailAddress(parcel.getReceiverEmailAddress());
        notification.setUniqueParcelId(parcel.getUniqueParcelId());

        notification.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
        notification.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
        notification.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());

        notification.setPickingUpDate(currentDate().toString());
        notification.setPickingUpTime(currentTime().toString());

        if(parcel.getUser() == null){
            notification.setSenderName(parcel.getSenderName());
            notification.setSenderEmailAddress(parcel.getSenderEmailAddress());
        }
        else{
            notification.setSenderName(parcel.getUser().getLastName() + " " + parcel.getUser().getFirstName());
            notification.setSenderEmailAddress(parcel.getUser().getEmailAddress());
        }
        //Email küldése a csomag feladójának
        producer.sendPickingUpNotificationForSender(notification);
        //Email küldése a csomag átvevőjének
        producer.sendPickingUpNotificationForReceiver(notification);

        //Csomag adatainak frissítése az adatbázisban
        parcel.setPickedUp(true);
        parcel.setBox(null);
        parcel.setParcelLocker(null);

        save(parcel);
    }

    //Csomag objektum küldése a statistics service-nek
    //Ezt az objektumot elmenti az adatbázisba
    public void sendParcelToStatisticsService(Parcel parcel){

        //Kérés objektum
        ParcelToStaticticsServiceRequest request = new ParcelToStaticticsServiceRequest();
        request.setUniqueParcelId(parcel.getUniqueParcelId());
        //Ha a csomagnak van user objektuma
        if(parcel.getUser() != null){
            request.setSenderEmailAddress(parcel.getUser().getEmailAddress());
            request.setSenderName(parcel.getUser().getLastName() + " " +  parcel.getUser().getFirstName());
        }
        else{
            request.setSenderName(parcel.getSenderName());
            request.setSenderEmailAddress(parcel.getSenderEmailAddress());

        }
        //Feladási automata
        request.setSenderParcelLockerPostCode(parcel.getShippingFrom().getLocation().getPostCode());
        request.setSenderParcelLockerCounty(parcel.getShippingFrom().getLocation().getCounty());
        request.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
        request.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());
        //Érkezési automata
        request.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
        request.setReceiverParcelLockerCounty(parcel.getShippingTo().getLocation().getCounty());
        request.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
        request.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());

        request.setSize(parcel.getSize());
        request.setPrice(parcel.getPrice());
        request.setReceiverName(parcel.getReceiverName());
        request.setReceiverEmailAddress(parcel.getReceiverEmailAddress());

        request.setShipped(parcel.isShipped());
        request.setPickedUp(parcel.isPickedUp());
        request.setSendingDate(parcel.getSendingDate().toString());
        request.setSendingTime(parcel.getSendingTime().toString());

        request.setPickingUpDate(parcel.getPickingUpDate().toString());
        request.setPickingUpTime(parcel.getPickingUpTime().toString());
        request.setShippingDate(parcel.getShippingDate().toString());
        request.setShippingTime(parcel.getShippingTime().toString());

        request.setPickingUpDateFromParcelLockerByCourier(parcel.getPickingUpDateFromParcelLockerByCourier().toString());
        request.setPickingUpTimeFromParcelLockerByCourier(parcel.getPickingUpTimeFromParcelLockerByCourier().toString());

        request.setHandingDateToFirstStoreByCourier(parcel.getHandingDateToFirstStoreByCourier().toString());
        request.setHandingTimeToFirstStoreByCourier(parcel.getHandingTimeToFirstStoreByCourier().toString());

        request.setPickingUpDateFromSecondStoreByCourier(parcel.getPickingUpDateFromSecondStoreByCourier().toString());
        request.setPickingUpTimeFromSecondStoreByCourier(parcel.getPickingUpTimeFromSecondStoreByCourier().toString());


        request.setPlaced(parcel.isPlaced());
        request.setPaid(parcel.isPaid());
        request.setPickingUpExpirationDate(parcel.getPickingUpExpirationDate().toString());
        request.setPickingUpExpirationTime(parcel.getPickingUpExpirationTime().toString());

        request.setPickedUp(parcel.isPickedUp());

        if(parcel.getSendingExpirationDate() != null){
            request.setSendingExpirationDate(parcel.getSendingExpirationDate().toString());
            request.setSendingExpirationTime(parcel.getSendingExpirationTime().toString());
        }




        //Válasz objektum
        StringResponse responseFromStatisticsService;

        responseFromStatisticsService = webClientBuilder.build().post()
                .uri("http://statistics-service/statistics/parcel/addparceltodb")
                .body(Mono.just(request), ParcelToStaticticsServiceRequest.class)
                .retrieve()
                .bodyToMono(StringResponse.class)
                .block();
    }

    //Parcel objektum konvertálása parcelDTO objektumba
    public ParcelDTO parcelToParcelDTO(Parcel parcel){

        ParcelDTO parcelDTO = new ParcelDTO();

        //Feladási automata
        parcelDTO.setShippingFromPostCode(parcel.getShippingFrom().getLocation().getPostCode());
        parcelDTO.setShippingFromCounty(parcel.getShippingFrom().getLocation().getCounty());
        parcelDTO.setShippingFromCity(parcel.getShippingFrom().getLocation().getCity());
        parcelDTO.setShippingFromStreet(parcel.getShippingFrom().getLocation().getStreet());

        //Érkezési automata
        parcelDTO.setShippingToPostCode(parcel.getShippingTo().getLocation().getPostCode());
        parcelDTO.setShippingToCounty(parcel.getShippingTo().getLocation().getCounty());
        parcelDTO.setShippingToCity(parcel.getShippingTo().getLocation().getCity());
        parcelDTO.setShippingToStreet(parcel.getShippingTo().getLocation().getStreet());

        //Raktár
        if(parcel.getStore() != null){
            parcelDTO.setStorePostCode(parcel.getStore().getAddress().getPostCode());
            parcelDTO.setStoreCounty(parcel.getStore().getAddress().getCounty());
            parcelDTO.setStoreCity(parcel.getStore().getAddress().getCity());
            parcelDTO.setStoreStreet(parcel.getStore().getAddress().getStreet());
        }

        parcelDTO.setId(parcel.getId());
        parcelDTO.setUniqueParcelId(parcel.getUniqueParcelId());
        parcelDTO.setSize(parcel.getSize());
        parcelDTO.setPrice(parcel.getPrice());
        parcelDTO.setReceiverName(parcel.getReceiverName());
        parcelDTO.setReceiverEmailAddress(parcel.getReceiverEmailAddress());

        //Ha kell, akkor user vagy a feladó adatai

        parcelDTO.setShipped(parcel.isShipped());
        parcelDTO.setPickedUp(parcel.isPickedUp());

        if(parcel.getSendingDate() != null){
            parcelDTO.setSendingDate(parcel.getSendingDate().toString());
            parcelDTO.setSendingTime(parcel.getSendingTime().toString());
        }


        if(parcel.getPickingUpDate() != null){
            parcelDTO.setPickingUpDate(parcel.getPickingUpDate().toString());
            parcelDTO.setPickingUpTime(parcel.getPickingUpTime().toString());
        }

        if(parcel.getShippingDate() != null){
            parcelDTO.setShippingDate(parcel.getShippingDate().toString());
            parcelDTO.setShippingTime(parcel.getShippingTime().toString());
        }

        //Ha a csomag még valamelyik automatában van
        if(parcel.getBox() != null){
            parcelDTO.setMaxBoxWidth(parcel.getBox().getMaxWidth());
            parcelDTO.setMaxBoxHeight(parcel.getBox().getMaxHeight());
            parcelDTO.setMaxBoxLength(parcel.getBox().getMaxLength());
            parcelDTO.setMaxBoxWeight(parcel.getBox().getMaxWeight());
            parcelDTO.setBoxSize(parcel.getBox().getSize());
            parcelDTO.setBoxNumber(parcel.getBox().getBoxNumber());
        }

        parcelDTO.setPlaced(parcel.isPlaced());
        parcelDTO.setPaid(parcel.isPaid());

        parcelDTO.setPickingUpCode(parcel.getPickingUpCode());

        if(parcel.getSendingCode() != null){
            parcelDTO.setSendingCode(parcel.getSendingCode());
        }

        if(parcel.getPickingUpExpirationDate() != null){
            parcelDTO.setPickingUpExpirationDate(parcel.getPickingUpExpirationDate().toString());
            parcelDTO.setPickingUpExpirationTime(parcel.getPickingUpExpirationTime().toString());
        }
        parcelDTO.setPickingUpExpired(parcel.isPickingUpExpired());

        if(parcel.getSendingExpirationDate() != null){
            parcelDTO.setSendingExpirationDate(parcel.getSendingExpirationDate().toString());
            parcelDTO.setSendingExpirationTime(parcel.getSendingExpirationTime().toString());
        }

        parcelDTO.setSendingExpired(parcel.isSendingExpired());

        if(parcel.getPickingUpDateFromParcelLockerByCourier() != null){
            parcelDTO.setPickingUpDateFromParcelLockerByCourier(parcel.getPickingUpDateFromParcelLockerByCourier().toString());
            parcelDTO.setPickingUpTimeFromParcelLockerByCourier(parcel.getPickingUpTimeFromParcelLockerByCourier().toString());
        }

        if(parcel.getHandingDateToFirstStoreByCourier() != null){
            parcelDTO.setHandingDateToFirstStoreByCourier(parcel.getHandingDateToFirstStoreByCourier().toString());
            parcelDTO.setHandingTimeToFirstStoreByCourier(parcel.getHandingTimeToFirstStoreByCourier().toString());
        }

        if(parcel.getPickingUpDateFromSecondStoreByCourier() != null){
            parcelDTO.setPickingUpDateFromSecondStoreByCourier(parcel.getPickingUpDateFromSecondStoreByCourier().toString());
            parcelDTO.setPickingUpTimeFromSecondStoreByCourier(parcel.getPickingUpTimeFromSecondStoreByCourier().toString());
        }

        return parcelDTO;
    }

    //Jelenlegi dátum lekérése
    public LocalDate currentDate(){

        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }
    //Jelenlegi időpont lekérése
    public LocalTime currentTime(){

        LocalTime currentTime = LocalTime.now();
        return currentTime;
    }

    //Csomag átvételi ideje lejárt?
    public boolean isPickingUpDateTimeExpired(Parcel parcel){

        boolean result = false;

        //Ha a csomag már le van szállítva
        if(parcel.isShipped() && parcel.getPickingUpExpirationDate() != null && parcel.getPickingUpExpirationTime() != null){

            LocalDate expirationDate = parcel.getPickingUpExpirationDate();
            LocalTime expirationTime = parcel.getPickingUpExpirationTime();

            //Ha a jelenlegi dátum és a lejárati dátum megegyezik, akkor az időpontokat kell megvizsgálni
            if(currentDate().isEqual(expirationDate) && currentTime().isAfter(expirationTime)){
                result = true;
            }
            //Ha a jelenlegi dátum nagyobb, mint a lejárati dátum
            if(currentDate().isAfter(expirationDate)){
                result = true;
            }

        }

        return result;
    }

    //Ügyfél feladja a csomagot
    public LocalDate date1(){
        return LocalDate.of(2023,10, 7);
    }
    public LocalTime time1(){
        return LocalTime.of(17,10);
    }

    //Futár kiveszi a csomagot
    public LocalDate date2(){
        return LocalDate.of(2023,10, 8);
    }
    public LocalTime time2(){
        return LocalTime.of(9,2);
    }

    //Futár leadja a raktárba
    public LocalDate date3(){
        return LocalDate.of(2023,10, 8);
    }
    public LocalTime time3(){
        return LocalTime.of(15,31);
    }

    //Futár felveszi a raktárból
    public LocalDate date4(){
        return LocalDate.of(2023,10, 10);
    }
    public LocalTime time4(){
        return LocalTime.of(7,23);
    }

    //Futár elhelyezi az automatába
    public LocalDate date5(){
        return LocalDate.of(2023,10, 10);
    }
    public LocalTime time5(){
        return LocalTime.of(9,3);
    }

}
