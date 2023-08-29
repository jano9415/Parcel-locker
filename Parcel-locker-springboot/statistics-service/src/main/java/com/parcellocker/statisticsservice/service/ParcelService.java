package com.parcellocker.statisticsservice.service;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import org.springframework.http.ResponseEntity;

public interface ParcelService {

    //Keresés id szerint
    Parcel findById(String id);

    //Csomag hozzáadása az adatbázishoz
    //A kérés a parcel-handler-service-ből érkezik
    ResponseEntity<StringResponse> addParcelToDb(ParcelToStaticticsServiceRequest request);
}
