package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.response.EmptyParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.FillParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.GetParcelsForParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.PickUpParcelResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParcelService {

    //Összes csomag keresése
    List<Parcel> findAll();

    //Keresés id alapján
    Parcel findById(Long id);

    //Csomag mentése
    void save(Parcel parcel);

    //Keresés átvételi kód szerint
    Parcel findByPickingUpCode(String pickingUpCode);

    //Csomag küldése feladási kód nélkül
    ResponseEntity<?> sendParcelWithoutCode(ParcelSendingWithoutCodeRequest request, Long senderParcelLockerId);

    //Csomagok lekérése, amik készen állnak az elszállításra
    ResponseEntity<List<GetParcelsForShippingResponse>>  getParcelsForShipping(Long senderParcelLockerId);

    //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
    //Jwt token szükséges
    ResponseEntity<List<EmptyParcelLockerResponse>> emptyParcelLocker(EmptyParcelLockerRequest request);

    //Futárnál lévő csomagok lekérése. Csak olyan csomagok, amik az adott automatához tartoznak és van nekik szabad rekesz
    //Jwt token szükséges
    ResponseEntity<List<GetParcelsForParcelLockerResponse>>getParcelsForParcelLocker(Long senderParcelLockerId, String uniqueCourierId);

    //Automata feltöltése
    //Jwt token szükséges
    ResponseEntity<List<FillParcelLockerResponse>> fillParcelLocker(Long senderParcelLockerId, String uniqueCourierId);

    //Csomag átvétele
    ResponseEntity<PickUpParcelResponse> pickUpParcel(String pickingUpCode, Long senderParcelLockerId);

    //Csomag átvétele fizetés után. Adatbázis frissítése.
    //Nem szükséges jwt token
    ResponseEntity<StringResponse> pickUpParcelAfterPayment(String pickingUpCode, Long senderParcelLockerId);
}
