package com.parcellocker.statisticsservice.service.serviceimpl;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.repository.ParcelRepository;
import com.parcellocker.statisticsservice.service.ParcelService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ParcelServiceImpl implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    //Keresés id szerint
    @Override
    public Parcel findById(String id) {
        return parcelRepository.findById(id).get();
    }

    //Csomag hozzáadása az adatbázishoz
    //A kérés a parcel-handler-service-ből érkezik
    @Override
    public ResponseEntity<StringResponse> addParcelToDb(ParcelToStaticticsServiceRequest request) {
        return null;
    }

}
