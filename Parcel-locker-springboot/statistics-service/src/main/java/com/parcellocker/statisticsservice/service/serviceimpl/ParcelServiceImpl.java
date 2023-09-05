package com.parcellocker.statisticsservice.service.serviceimpl;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.model.ParcelLocker;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.GetParcelLockersResponse;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.payload.response.TotalSendingByLocationsResponse;
import com.parcellocker.statisticsservice.repository.ParcelRepository;
import com.parcellocker.statisticsservice.service.ParcelService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        parcel.setSenderName(request.getSenderName());

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

        int onlinePayment = 0;
        int paymentAtParcelLocker = 0;
        StringResponse responseObj1 = new StringResponse();
        StringResponse responseObj2 = new StringResponse();
        List<StringResponse> response = new ArrayList<>();

        for(Parcel parcel : parcelRepository.findAll()){
            if(parcel.getPrice() == 0){
                onlinePayment++;
            }
            if(parcel.getPrice() > 0){
                paymentAtParcelLocker++;
            }
        }

        responseObj1.setMessage(String.valueOf(onlinePayment));
        responseObj2.setMessage(String.valueOf(paymentAtParcelLocker));
        response.add(responseObj1);
        response.add(responseObj2);

        return ResponseEntity.ok(response);
    }

    //Átlagos szállítási idő - response lista első objektuma
    //Leggyorsabb szállítási idő - response lista második objektuma
    //Leglassabb szállítási idő - response lista harmadik objektuma
    @Override
    public ResponseEntity<List<StringResponse>> averageMinMaxShippingTime() {

        List<Duration> differences = new ArrayList<>();
        StringResponse responseObj1 = new StringResponse();
        StringResponse responseObj2 = new StringResponse();
        StringResponse responseObj3 = new StringResponse();
        List<StringResponse> response = new ArrayList<>();

        for(Parcel parcel : parcelRepository.findAll()){
            //Dátum és idő konvertálása
            LocalDateTime sendingDateTime = LocalDateTime.of(parcel.getSendingDate(), parcel.getSendingTime());
            LocalDateTime shippingDateTime = LocalDateTime.of(parcel.getShippingDate(), parcel.getShippingTime());

            //A két időpont közötti különbség
            Duration difference = Duration.between(sendingDateTime, shippingDateTime);

            differences.add(difference);
        }

        //Legkisebb idő
        Duration minTime = differences.get(0);
        //Legnagyobb idő
        Duration maxTime = differences.get(0);
        //Összes idő
        Duration totalTime = Duration.ZERO;

        //Összes idő, min és max kiszámítása
        for(Duration duration : differences){

            //Legkisebb idő
            if(duration.compareTo(minTime) < 0){
                minTime = duration;
            }
            //Legnagyobb idő
            if(duration.compareTo(maxTime) > 0){
                maxTime = duration;
            }

            //Összes idő kiszámítása
            totalTime = totalTime.plus(duration);
        }

        //Átlag kiszámítása
        float averageTimeInMinutes = totalTime.toMinutes() / differences.size();
        float averageTimeInDays = averageTimeInMinutes / (24 * 60);
        float averageTimeInHours = (averageTimeInMinutes % (24 * 60)) / 60;

        //Minimum idő órában nem kerekítve
        float minTimeInHours = (float) (minTime.toHours() / 60.0);
        //Maximum idő órában nem kerekítve
        float maxTimeInHours = (float) (maxTime.toHours() / 60.0);

        responseObj1.setMessage(String.valueOf(averageTimeInHours));
        responseObj2.setMessage(String.valueOf(maxTimeInHours));
        responseObj3.setMessage(String.valueOf(minTimeInHours));

        response.add(responseObj1);
        response.add(responseObj2);
        response.add(responseObj3);

        return ResponseEntity.ok(response);
    }

    //Csomagfeladások száma automaták szerint
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @Override
    public ResponseEntity<List<TotalSendingByLocationsResponse>> totalSendingByLocations() {

        List<TotalSendingByLocationsResponse> response = countParcelByLocations("from");

        return ResponseEntity.ok(response);
    }

    //Csomagátvételek száma automaták szerint
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @Override
    public ResponseEntity<List<TotalSendingByLocationsResponse>> totalPickingUpByLocations() {

        List<TotalSendingByLocationsResponse> response = countParcelByLocations("to");

        return ResponseEntity.ok(response);
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

        //Csomagok számlálása utcák szerint
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

    //Csomagok számlálása utcák szerint
    //Feladott csomagok
    //Átvett csomagok
    public List<TotalSendingByLocationsResponse> countParcelByLocations(String fromOrTo){

        List<TotalSendingByLocationsResponse> response = new ArrayList<>();

        //Csomagok számlálása utcák szerint
        for(GetParcelLockersResponse parcelLocker : getParcelLockers()){

            //Feladás
            if(fromOrTo.equals("from")){

                TotalSendingByLocationsResponse responseObj = new TotalSendingByLocationsResponse();

                responseObj.setLocation(parcelLocker.getPostCode() + ", " + parcelLocker.getCity() + ", " +
                        parcelLocker.getStreet());
                responseObj.setAmount(countByShippingFromStreet(parcelLocker.getStreet()));

                response.add(responseObj);

            }
            //Átvétel
            if(fromOrTo.equals("to")){

                TotalSendingByLocationsResponse responseObj = new TotalSendingByLocationsResponse();

                responseObj.setLocation(parcelLocker.getPostCode() + ", " + parcelLocker.getCity() + ", " +
                        parcelLocker.getStreet());
                responseObj.setAmount(countByShippingToStreet(parcelLocker.getStreet()));

                response.add(responseObj);

            }
        }

        return response;

    }


    /*
    public void valami(){
        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = new ParcelLocker();
        ParcelLocker receiverParcelLocker = new ParcelLocker();

        parcel.setUniqueParcelId("fdsfds");
        parcel.setSenderEmailAddress("fdsfdsgfd");
        parcel.setSenderName("Nagy László");

        //Feladási automata
        senderParcelLocker.setPostCode(8100);
        senderParcelLocker.setCounty("aaaa");
        senderParcelLocker.setCity("dsfhgj");
        senderParcelLocker.setStreet("jhgbd");
        parcel.setShippingFrom(senderParcelLocker);
        //Érkezési automata
        receiverParcelLocker.setPostCode(5600);
        receiverParcelLocker.setCounty("lllhh");
        receiverParcelLocker.setCity("sasasc");
        receiverParcelLocker.setStreet("hhtzt");
        parcel.setShippingTo(receiverParcelLocker);

        parcel.setSize("large");
        parcel.setPrice(9000);
        parcel.setReceiverName("Kovács Nóra");
        parcel.setReceiverEmailAddress("gfgfd");

        parcel.setShipped(true);
        parcel.setPickedUp(true);
        parcel.setSendingDate(LocalDate.parse("2023-09-04"));
        parcel.setSendingTime(LocalTime.parse("13:40:20"));

        parcel.setPickingUpDate(LocalDate.parse("2023-09-08"));
        parcel.setPickingUpTime(LocalTime.parse("10:41:10"));
        parcel.setShippingDate(LocalDate.parse("2023-09-07"));
        parcel.setShippingTime(LocalTime.parse("8:41:13"));

        parcel.setPlaced(true);
        parcel.setPaid(true);
        parcel.setPickingUpExpirationDate(LocalDate.parse("2023-09-10"));
        parcel.setPickingUpExpirationTime(LocalTime.parse("16:30:44"));

        parcel.setPickedUp(true);

        parcel.setSendingExpirationDate(LocalDate.parse("2023-09-05"));
        parcel.setSendingExpirationTime(LocalTime.parse("12:30:20"));

        save(parcel);
    }

     */




}
