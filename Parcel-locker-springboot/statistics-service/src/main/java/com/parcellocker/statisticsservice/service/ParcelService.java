package com.parcellocker.statisticsservice.service;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StoreTurnOverDataResponse;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.payload.response.TotalSendingByLocationsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParcelService {

    //Keresés id szerint
    Parcel findById(String id);

    //Csomag hozzáadása az adatbázishoz
    //A kérés a parcel-handler-service-ből érkezik
    ResponseEntity<StringResponse> addParcelToDb(ParcelToStaticticsServiceRequest request);

    //Csomag mentése
    void save(Parcel parcel);

    //Összes kézbesített csomagok száma
    ResponseEntity<StringResponse> numberOfParcels();

    //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy
    ResponseEntity<StringResponse> mostCommonParcelSize();

    //Számlálás csomagméret szerint
    int countBySize(String size);

    //Számlálás feladási automata utcanév szerint
    int countByShippingFromStreet(String street);

    //Számlálás érkezési automata utcanév szerint
    int countByShippingToStreet(String street);

    //Csomagok száma méret szerint
    ResponseEntity<List<StringResponse>> numberOfParcelsBySize();

    //Összes bevétel a kézbesített csomagokból
    ResponseEntity<StringResponse> totalRevenue();

    //Csomagok értékének átlaga forintban
    ResponseEntity<StringResponse> averageParcelValue();

    //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
    ResponseEntity<List<StringResponse>> amountOfParcelsFromOnlineAndParcelLocker();

    //Honnan adják fel a legtöbb csomagot?
    ResponseEntity<StringResponse> mostCommonSendingLocation();

    //Hova érkezik a legtöbb csomag?
    ResponseEntity<StringResponse> mostCommonReceivingLocation();

    //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?
    ResponseEntity<List<StringResponse>> paymentDatas();

    //Átlagos szállítási idő - response lista első objektuma
    //Leggyorsabb szállítási idő - response lista második objektuma
    //Leglassabb szállítási idő - response lista harmadik objektuma
    ResponseEntity<List<StringResponse>> averageMinMaxShippingTime();

    //Csomagfeladások száma automaták szerint
    //Jwt token szükséges
    //Admin szerepkör szükséges
    ResponseEntity<List<TotalSendingByLocationsResponse>> totalSendingByLocations();

    //Csomagátvételek száma automaták szerint
    //Jwt token szükséges
    //Admin szerepkör szükséges
    ResponseEntity<List<TotalSendingByLocationsResponse>> totalPickingUpByLocations();

    //Ügyfél elhelyezi a csomagot a feladási automatába időpont -> ügyfél átveszi a csomagot az érkezési automatából időpont
    ResponseEntity<List<StringResponse>> placeByCustomerAndPickUpByCustomerTime();

    //Ügyfél elhelyezi a csomagot a feladási automatába időpont -> futár kiveszi a csomagot a feladási automatából időpont
    ResponseEntity<List<StringResponse>> placeByCustomerAndPickUpByCourierTime();

    //Futár kiveszi a csomagot a feladási automatából időpont -> futár elhelyezi a csomagot az érkezési automatába időpont
    ResponseEntity<List<StringResponse>> pickUpByCourierAndPlaceByCourierTime();

    //Futár elhelyezi a csomagot az érkezési automatába időpont -> ügyfél átveszi a csomagot az érkezési automatából időpont
    ResponseEntity<List<StringResponse>> placeByCourierAndPickUpByCustomerTime();

    //Raktárak forgalmi adatai
    ResponseEntity<List<StoreTurnOverDataResponse>> storeTurnOverData();
}
