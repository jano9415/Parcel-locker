package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParcelService {

    //Összes csomag keresése
    List<Parcel> findAll();

    //Keresés id alapján
    Parcel findById(Long id);

    //Csomag mentése
    void save(Parcel parcel);

    //Csomag küldése feladási kód nélkül
    ResponseEntity<?> sendParcelWithoutCode(ParcelSendingWithoutCodeRequest request, Long senderParcelLockerId);
}
