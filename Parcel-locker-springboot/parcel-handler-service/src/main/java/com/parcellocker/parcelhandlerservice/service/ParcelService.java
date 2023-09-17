package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.parcellocker.parcelhandlerservice.payload.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParcelService {

    //Összes csomag keresése
    List<Parcel> findAll();

    //Keresés id alapján
    Parcel findById(Long id);

    //Csomag mentése
    void save(Parcel parcel);

    //Csomag törlése
    void delete(Parcel parcel);

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

    //Keresés feladási kód szerint
    Parcel findBySendingCode(String sendingCode);

    //Keresés feladási kód szerint
    //Ha van csomag és a feladási automata megegyezik a kérésben érkező feladási automatával, akkor visszatérek
    //a rekesz számával
    //Különben a csomag nem található
    ResponseEntity<GetParcelForSendingWithCodeResponse> getParcelForSendingWithCode(String sendingCode, Long senderParcelLockerId);


    //Csomag küldése feladási kóddal
    //Nem szükséges jwt token
    ResponseEntity<StringResponse> sendParcelWithCode(String sendingCode, Long senderParcelLockerId);

    //Csomag küldése a weblapról feladási kóddal
    //Ez még csak egy előzetes csomagfeladás. A felhasználó megkapja email-ben a csomagfeladási kódot
    //A végleges csomagfeladás az automatánál történik
    //Jwt token szükséges
    ResponseEntity<StringResponse> sendParcelWithCodeFromWebpage(SendParcelWithCodeFromWebpageRequest request);

    //Csomag követése
    //Nem szükséges jwt token
    ResponseEntity<FollowParcelResponse> followParcel(String uniqueParcelId);

    //Keresés egyedi csomagazonosító szerint
    Parcel findByUniqueParcelId(String uniqueParcelId);

    //Futár lead egy csomagot a központi raktárban
    //Jwt token szükséges
    ResponseEntity<StringResponse> handParcelToStore(String uniqueCourierId, String uniqueParcelId);

    //Futár felvesz egy csomagot a központi raktárból
    //Jwt token szükséges
    ResponseEntity<StringResponse> pickUpParcelFromStore(String uniqueCourierId, String uniqueParcelId);

    //Felhasználó csomagjainak lekérése
    ResponseEntity<?> getParcelsOfUser(String emailAddress, String type);

    //Csomag törlése
    //Felhasználó kitörli az előzetes csomagfeladást
    ResponseEntity<StringResponse> deleteMyParcel(Long parcelId);

    //Központi raktárak csomagjainak lekérése
    ResponseEntity<?> getParcelsOfStore(Long storeId);

    //Csomag átvételi ideje lejárt, ezért az a központi raktárban van
    //Csomag újraindítása az automatához
    //pickingUpExpired mező módosítása. True vagy false
    ResponseEntity<StringResponse> updatePickingUpExpired(Long parcelId);

    //Futár csomagjainak lekérése
    ResponseEntity<?> getParcelsOfCourier(Long courierId);

    //Automata csomagjainak lekérése
    ResponseEntity<?> getParcelsOfParcelLocker(Long parcelLockerId);

    //Csomagátvételi lejárati idő meghosszabbítása
    ResponseEntity<StringResponse> updatePickingUpExpirationDate(Long parcelId, String newDate);

    //Csomagfeladási lejárati idő meghosszabbítása
    ResponseEntity<StringResponse> updateSendingExpirationDate(Long parcelId, String newDate);
}
