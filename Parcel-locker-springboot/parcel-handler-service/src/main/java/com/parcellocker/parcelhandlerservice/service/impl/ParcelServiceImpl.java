package com.parcellocker.parcelhandlerservice.service.impl;


import com.parcellocker.parcelhandlerservice.kafka.Producer;
import com.parcellocker.parcelhandlerservice.model.*;
import com.parcellocker.parcelhandlerservice.payload.*;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelPickingUpNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelSendingFromWebPageNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelShippingNotification;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.parcellocker.parcelhandlerservice.payload.response.*;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

    @Autowired
    private UserServiceImpl userService;


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
            responseObject.setPrice(parcel.getPrice());

            responseObject.setSenderParcelLockerPostCode(parcel.getShippingFrom().getLocation().getPostCode());
            responseObject.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
            responseObject.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());

            responseObject.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
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
                    responseObj.setSenderParcelLockerCity(parcel.getShippingFrom().getLocation().getCity());
                    responseObj.setSenderParcelLockerStreet(parcel.getShippingFrom().getLocation().getStreet());

                    responseObj.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
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
                    LocalDate currentDate = LocalDate.now();
                    parcel.setShippingDate(currentDate);

                    LocalTime currentTime = LocalTime.now();
                    parcel.setShippingTime(currentTime);

                    //Átvételi lejárati dátum. Amikor a futár elhelyezi a csomagot az automatába + három nap
                    parcel.setPickingUpExpirationDate(currentDate.plusDays(3));
                    //Átvételi lejárati időpont. Megegyezik az elhelyezési időponttal
                    parcel.setPickingUpExpirationTime(currentTime);


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
                    notification.setShippingDate(currentDate.toString());
                    notification.setShippingTime(currentTime.toString());

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

        updateDbAfterPickUpParcel(pickingUpCode,senderParcelLockerId);

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
        LocalDate currentDate = LocalDate.now();
        parcel.setSendingDate(currentDate);

        LocalTime currentTime = LocalTime.now();
        parcel.setSendingTime(currentTime);

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
        notification.setSendingDate(currentDate.toString());
        notification.setSendingTime(currentTime.toString());

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
        //Mert ha nincs, akkor neme is jelenik meg frontend oldalon

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
        parcel.setSendingExpirationDate(currentDate.plusDays(3));
        //Megegyezik a feladási időpnttal
        parcel.setSendingExpirationTime(currentTime);
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
        response.setShippingToCity(parcel.getShippingTo().getLocation().getCity());

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

        response.setSendingExpired(parcel.isSendingExpired());

        return ResponseEntity.ok(response);
    }

    //Keresés egyedi csomagazonosító szerint
    @Override
    public Parcel findByUniqueParcelId(String uniqueParcelId) {
        return parcelRepository.findByUniqueParcelId(uniqueParcelId);
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
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalDate expirationDate = parcel.getPickingUpExpirationDate();
            LocalTime expirationTime = parcel.getPickingUpExpirationTime();

            //Ha a csomag már le van szállítva
            if(parcel.isShipped() && parcel.getPickingUpExpirationDate() != null && parcel.getPickingUpExpirationTime() != null){
                //Ha a jelenlegi dátum és a lejárati dátum megegyezik, akkor az időpontokat kell megvizsgálni
                if(currentDate.isEqual(expirationDate) && currentTime.isAfter(expirationTime)){
                    readyParcels.add(parcel);
                    parcel.setPickingUpExpired(true);
                }
                //Ha a jelenlegi dátum nagyobb, mint a lejárati dátum
                if(currentDate.isAfter(expirationDate)){
                    readyParcels.add(parcel);
                    parcel.setPickingUpExpired(true);
                }

            }
        }
        return readyParcels;
    }

    //Csomag átvétele utáni adatbázis frissítés
    public void updateDbAfterPickUpParcel(String pickingUpCode, Long senderParcelLockerId){

        Parcel parcel = findByPickingUpCode(pickingUpCode);

        //Átvétel dátuma
        LocalDate currentDate = LocalDate.now();
        parcel.setPickingUpDate(currentDate);
        LocalTime currentTime = LocalTime.now();
        parcel.setPickingUpTime(currentTime);

        //Email értesítés objektum
        ParcelPickingUpNotification notification = new ParcelPickingUpNotification();
        notification.setReceiverName(parcel.getReceiverName());
        notification.setReceiverEmailAddress(parcel.getReceiverEmailAddress());
        notification.setUniqueParcelId(parcel.getUniqueParcelId());

        notification.setReceiverParcelLockerPostCode(parcel.getShippingTo().getLocation().getPostCode());
        notification.setReceiverParcelLockerCity(parcel.getShippingTo().getLocation().getCity());
        notification.setReceiverParcelLockerStreet(parcel.getShippingTo().getLocation().getStreet());

        notification.setPickingUpDate(currentDate.toString());
        notification.setPickingUpTime(currentTime.toString());

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
}
