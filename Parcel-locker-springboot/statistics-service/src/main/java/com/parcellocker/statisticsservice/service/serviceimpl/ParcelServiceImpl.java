package com.parcellocker.statisticsservice.service.serviceimpl;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.model.ParcelLocker;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.GetParcelLockersResponse;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.repository.ParcelRepository;
import com.parcellocker.statisticsservice.service.ParcelService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParcelServiceImpl implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    //Keresés id szerint
    @Override
    public Parcel findById(String id) {
        return parcelRepository.findById(id).get();
    }

    //Csomag hozzáadása az adatbázishoz
    //A csomag objektum a parcel-handler-service-ből érkezik
    @Override
    public ResponseEntity<StringResponse> addParcelToDb(ParcelToStaticticsServiceRequest request) {

        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = new ParcelLocker();
        ParcelLocker receiverParcelLocker = new ParcelLocker();

        parcel.setUniqueParcelId(request.getUniqueParcelId());
        parcel.setSenderEmailAddress(request.getSenderEmailAddress());
        parcel.setSenderName(parcel.getSenderName());

        //Feladási automata
        senderParcelLocker.setPostCode(request.getSenderParcelLockerPostCode());
        senderParcelLocker.setCounty(request.getSenderParcelLockerCounty());
        senderParcelLocker.setCity(request.getSenderParcelLockerCity());
        senderParcelLocker.setStreet(request.getSenderParcelLockerStreet());
        parcel.setShippingFrom(senderParcelLocker);
        //Érkezési automata
        receiverParcelLocker.setPostCode(request.getReceiverParcelLockerPostCode());
        receiverParcelLocker.setCounty(request.getReceiverParcelLockerCounty());
        receiverParcelLocker.setCity(request.getReceiverParcelLockerCity());
        receiverParcelLocker.setStreet(request.getReceiverParcelLockerStreet());
        parcel.setShippingTo(receiverParcelLocker);

        parcel.setSize(request.getSize());
        parcel.setPrice(request.getPrice());
        parcel.setReceiverName(request.getReceiverName());
        parcel.setReceiverEmailAddress(request.getReceiverEmailAddress());

        parcel.setShipped(request.isShipped());
        parcel.setPickedUp(request.isPickedUp());
        parcel.setSendingDate(LocalDate.parse(request.getSendingDate()));
        parcel.setSendingTime(LocalTime.parse(request.getSendingTime()));

        parcel.setPickingUpDate(LocalDate.parse(request.getPickingUpDate()));
        parcel.setPickingUpTime(LocalTime.parse(request.getPickingUpTime()));
        parcel.setShippingDate(LocalDate.parse(request.getShippingDate()));
        parcel.setShippingTime(LocalTime.parse(request.getShippingTime()));

        parcel.setPlaced(request.isPlaced());
        parcel.setPaid(request.isPaid());
        parcel.setPickingUpExpirationDate(LocalDate.parse(request.getPickingUpExpirationDate()));
        parcel.setPickingUpExpirationTime(LocalTime.parse(request.getPickingUpExpirationTime()));

        parcel.setPickedUp(request.isPickedUp());

        if(request.getSendingExpirationDate() != null){
            parcel.setSendingExpirationDate(LocalDate.parse(request.getSendingExpirationDate()));
            parcel.setSendingExpirationTime(LocalTime.parse(request.getSendingExpirationTime()));
        }

        StringResponse response = new StringResponse();
        response.setMessage("successAdding");

        save(parcel);

        return ResponseEntity.ok(response);
    }

    //Csomag mentése
    @Override
    public void save(Parcel parcel) {
        parcelRepository.save(parcel);
    }

    //Számlálás csomagméret szerint
    @Override
    public int countBySize(String size) {
        return parcelRepository.countBySize(size);
    }

    //Számlálás feladási automata utcanév szerint
    @Override
    public int countByShippingFromStreet(String street) {
        return parcelRepository.countByShippingFromStreet(street);
    }

    //Számlálás érkezési automata utcanév szerint
    @Override
    public int countByShippingToStreet(String street) {
        return parcelRepository.countByShippingToStreet(street);
    }

    //Összes kézbesített csomagok száma
    @Override
    public ResponseEntity<StringResponse> numberOfParcels() {

        StringResponse response = new StringResponse();
        response.setMessage(String.valueOf(parcelRepository.count()));

        return ResponseEntity.ok(response);
    }

    //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy
    @Override
    public ResponseEntity<StringResponse> mostCommonParcelSize() {

        StringResponse response = new StringResponse();

        int smallNumber = countBySize("small");
        int mediumNumber = countBySize("medium");
        int largeNumber = countBySize("large");

        //Egy legnagyobb van
        if(smallNumber > mediumNumber && smallNumber > largeNumber){
            response.setMessage("small");
        }
        if(mediumNumber > smallNumber && mediumNumber > largeNumber){
            response.setMessage("medium");
        }
        if(largeNumber > smallNumber && largeNumber > mediumNumber){
            response.setMessage("large");
        }

        //Kettő legnagyobb van
        if(smallNumber == mediumNumber){
            response.setMessage("smallAndMedium");
        }
        if(smallNumber == largeNumber){
            response.setMessage("smallAndLarge");
        }
        if(mediumNumber == largeNumber){
            response.setMessage("mediumAndLarge");
        }

        //Három legnagyobb van
        if(smallNumber == mediumNumber && mediumNumber == largeNumber){
            response.setMessage("smallAndMediumAndLarge");
        }

        return ResponseEntity.ok(response);
    }

    //Csomagok száma méret szerint
    @Override
    public ResponseEntity<List<StringResponse>> numberOfParcelsBySize() {

        List<StringResponse> response = new ArrayList<>();

        StringResponse smallNumber = new StringResponse();
        StringResponse mediumNumber = new StringResponse();
        StringResponse largeNumber = new StringResponse();

        smallNumber.setMessage(String.valueOf(parcelRepository.countBySize("small")));
        mediumNumber.setMessage(String.valueOf(parcelRepository.countBySize("medium")));
        largeNumber.setMessage(String.valueOf(parcelRepository.countBySize("large")));

        response.add(smallNumber);
        response.add(mediumNumber);
        response.add(largeNumber);


        return ResponseEntity.ok(response);
    }

    //Összes bevétel a kézbesített csomagokból
    @Override
    public ResponseEntity<StringResponse> totalRevenue() {

        StringResponse response = new StringResponse();

        int smallPrice = 1950;
        int mediumPrice = 2400;
        int largePrice = 2950;

        int revenue = parcelRepository.countBySize("small") * smallPrice + parcelRepository.countBySize("medium") * mediumPrice
        + parcelRepository.countBySize("large") * largePrice;

        response.setMessage(String.valueOf(revenue));

        return ResponseEntity.ok(response);
    }

    //Csomagok értékének átlaga forintban
    //Beleszámolom a nullát is. Ha nulla egy csomag értéke, akkor azt már előre kifizették átutalással,
    //amiről a csomagküldő rendszernek nincs tudomása
    @Override
    public ResponseEntity<StringResponse> averageParcelValue() {

        StringResponse response = new StringResponse();
        int totalValue = 0;

        for(Parcel parcel : parcelRepository.findAll()){
            totalValue += parcel.getPrice();
        }

        response.setMessage(String.valueOf(totalValue / parcelRepository.count()));

        return ResponseEntity.ok(response);
    }

    //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
    @Override
    public ResponseEntity<List<StringResponse>> amountOfParcelsFromOnlineAndParcelLocker() {

        int online = 0;
        int fromParcelLocker = 0;
        List<StringResponse> response = new ArrayList<>();

        for(Parcel parcel : parcelRepository.findAll()){
            if(parcel.getSendingExpirationDate() == null){
                fromParcelLocker++;
            }
            else{
                online++;
            }
        }


        StringResponse responseObj = new StringResponse();
        responseObj.setMessage(String.valueOf(online));

        StringResponse responseObj2 = new StringResponse();
        responseObj2.setMessage(String.valueOf(fromParcelLocker));

        response.add(responseObj);
        response.add(responseObj2);
        return ResponseEntity.ok(response);
    }

    //Honnan adják fel a legtöbb csomagot?
    @Override
    public ResponseEntity<StringResponse> mostCommonSendingLocation() {

        StringResponse response = new StringResponse();

        response.setMessage(mostCommonLocation("from"));
        return ResponseEntity.ok(response);
    }

    //Hova érkezik a legtöbb csomag?
    @Override
    public ResponseEntity<StringResponse> mostCommonReceivingLocation() {
        StringResponse response = new StringResponse();

        response.setMessage(mostCommonLocation("to"));
        return ResponseEntity.ok(response);
    }

    //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?
    @Override
    public ResponseEntity<List<StringResponse>> paymentDatas() {
        return null;
    }

    //Átlagos szállítási idő
    @Override
    public ResponseEntity<StringResponse> averageShippingTime() {
        return null;
    }

    //Leggyorsabb szállítási idő
    @Override
    public ResponseEntity<StringResponse> mostFastShippingTime() {
        return null;
    }

    //Leglassabb szállítási idő
    @Override
    public ResponseEntity<StringResponse> slowestShippingTime() {
        return null;
    }

    //Csomagautomaták lekérése a parcel handler service-ből
    public List<GetParcelLockersResponse> getParcelLockers(){



        //parcelLockers.add(new ParcelLocker(8200, "Veszprém", "Veszprém", "Pápai út 32"));

        //Mivel a válasz egy objektum lista, ezért itt bodyToFlux-ot kell használni
        List<GetParcelLockersResponse> parcelLockers = webClientBuilder.build().get()
                .uri("http://parcel-handler-service/parcelhandler/parcellocker/getparcellockersforchoice")
                .retrieve()
                .bodyToFlux(GetParcelLockersResponse.class)
                .collectList()
                .block();

        return parcelLockers;
    }

    //Leggyakoribb feladási vagy érkezési automata címe
    public String mostCommonLocation(String fromOrTo){

        Map<String, Integer> streetCount = new HashMap<>();

        //Utcák számlálása
        for(GetParcelLockersResponse parcelLocker : getParcelLockers()){

            if(fromOrTo.equals("from")){
                streetCount.put(parcelLocker.getPostCode() + ", " + parcelLocker.getCity() + ", " +
                        parcelLocker.getStreet(), countByShippingFromStreet(parcelLocker.getStreet()));
            }
            if(fromOrTo.equals("to")){
                streetCount.put(parcelLocker.getPostCode() + ", " + parcelLocker.getCity() + ", " +
                        parcelLocker.getStreet(), countByShippingToStreet(parcelLocker.getStreet()));
            }


        }

        int maxStreetNumber = 0;
        String mostCommonStreet = "";

        //Maximum érték keresése a map-ben
        for(Map.Entry<String, Integer> entry : streetCount.entrySet()){
            if(entry.getValue() > maxStreetNumber){
                maxStreetNumber = entry.getValue();
                mostCommonStreet = entry.getKey();
            }
        }

        return mostCommonStreet;
    }


}
