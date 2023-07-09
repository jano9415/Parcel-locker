package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingRequest;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLocker;
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

    //Csomagok lekérése, amik készen állnak az elszállításra
    ResponseEntity<List<GetParcelsForShippingResponse>>  getParcelsForShipping(Long senderParcelLockerId);

    //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
    //Jwt token szükséges
    ResponseEntity<StringResponse> emptyParcelLocker(EmptyParcelLocker request);
}
