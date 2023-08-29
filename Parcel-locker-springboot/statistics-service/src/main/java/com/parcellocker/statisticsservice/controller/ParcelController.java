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
}
