package com.parcellocker.statisticsservice.controller;

import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.service.serviceimpl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //Automaták telítettségi diagramon

    //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy

    //Összes bevétel a kézbesített csomagokból

    //Csomagok értékének átlaga forintban

    //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel

    //Honnan adják fel a legtöbb csomagot?

    //Hova érkezik a legtöbb csomag?

    //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?

    //Átlagos szállítási idő

    //Átlagos szállítási idő adott x automatától y automatához

    //Átlagos szállítási idő x városból x városba

    //Leggyorsabb szállítási idő

    //Leglassabb szállítási idő

    //Mikor adják fel a legtöbb csomagot? Hétköznap? Hétvégén? Melyik nap?

    //Melyik napszakban a leginkább telítettek az automaták?

    //Melyik nap vagy napszakban adják fel a legtöbb csomagot? Melyik nap vagy napszakban veszik át a legtöbb csomagot?

    //Át nem vett, lejárt csomagok száma. Ezek aránya az összes kézbesített csomag arányához

    //Szállítási késések. Mondjuk ami több, mint 3 nap




}
