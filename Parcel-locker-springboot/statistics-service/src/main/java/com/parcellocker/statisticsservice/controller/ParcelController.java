package com.parcellocker.statisticsservice.controller;

import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.service.serviceimpl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics/parcel")
public class ParcelController {

    @Autowired
    private ParcelServiceImpl parcelService;

    //Csomag hozzáadása az adatbázishoz
    //A kérés a parcel-handler-service-ből érkezik
    @PostMapping("/addparceltodb")
    public ResponseEntity<StringResponse> addParcelToDb(@RequestBody ParcelToStaticticsServiceRequest request){
        return parcelService.addParcelToDb(request);
    }

    //Összes kézbesített csomagok száma
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/numberofparcels")
    public ResponseEntity<StringResponse> numberOfParcels(){
        return parcelService.numberOfParcels();
    }

    //Automaták telítettsége diagramon

    //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/mostcommonparcelsize")
    public ResponseEntity<StringResponse> mostCommonParcelSize(){
        return parcelService.mostCommonParcelSize();
    }

    //Csomagok száma méret szerint
    //Lista első eleme: kicsi csomagok száma
    //Lista második eleme: közepes csomagok száma
    //Lista harmadik eleme: nagy csomagok száma
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/numberofparcelsbysize")
    public ResponseEntity<List<StringResponse>> numberOfParcelsBySize(){
        return parcelService.numberOfParcelsBySize();
    }

    //Összes bevétel a kézbesített csomagokból
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/totalrevenue")
    public ResponseEntity<StringResponse> totalRevenue(){
        return parcelService.totalRevenue();
    }

    //Bevétel a kicsi, közepes és nagy csomagokból

    //Kicsi, közepes vagy nagy csomagból származik a legnagyobb bevétel?

    //Csomagok értékének átlaga forintban
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/averageparcelvalue")
    public ResponseEntity<StringResponse> averageParcelValue(){
        return parcelService.averageParcelValue();
    }

    //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
    //Lista első eleme: automatából
    //Lista második eleme: online
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/amountofparcelsfromonlineandparcellocker")
    public ResponseEntity<List<StringResponse>> amountOfParcelsFromOnlineAndParcelLocker(){
        return parcelService.amountOfParcelsFromOnlineAndParcelLocker();
    }

    //Honnan adják fel a legtöbb csomagot?
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/mostcommonsendinglocation")
    public ResponseEntity<StringResponse> mostCommonSendingLocation(){
        return parcelService.mostCommonSendingLocation();
    }

    //Hova érkezik a legtöbb csomag?
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/mostcommonreceivinglocation")
    public ResponseEntity<StringResponse> mostCommonReceivingLocation(){
        return parcelService.mostCommonReceivingLocation();
    }

    //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?
    //Online - lista első objektuma
    //Automatánál - lista második objektuma
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/paymentdatas")
    public ResponseEntity<List<StringResponse>> paymentDatas(){
        return parcelService.paymentDatas();
    }

    //Átlagos szállítási idő - response lista első objektuma
    //Leggyorsabb szállítási idő - response lista második objektuma
    //Leglassabb szállítási idő - response lista harmadik objektuma
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/averageminmaxshippingtime")
    public ResponseEntity<List<StringResponse>> averageMinMaxShippingTime(){
        return parcelService.averageMinMaxShippingTime();
    }

    //Átlagos szállítási idő adott x automatától y automatához

    //Átlagos szállítási idő x városból x városba

    //Mikor adják fel a legtöbb csomagot? Hétköznap? Hétvégén? Melyik nap?

    //Melyik napszakban a leginkább telítettek az automaták?

    //Melyik nap vagy napszakban adják fel a legtöbb csomagot? Melyik nap vagy napszakban veszik át a legtöbb csomagot?

    //Át nem vett, lejárt csomagok száma. Ezek aránya az összes kézbesített csomag arányához

    //Szállítási késések. Mondjuk ami több, mint 3 nap




}
