package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface ParcelLockerService {

    //Összes csomag automata keresése
    List<ParcelLocker> findAll();

    //Keresés id alapján
    ParcelLocker findById(Long id);

    //Csomag automata mentése
    void save(ParcelLocker parcelLocker);

    //Csomag automaták lekérése. Ezekből lehet kiválasztani az angular alkalmazásban a feladási automatát.
    ResponseEntity<List<ParcelLockerDTO>> getParcelLockersForChoice();

    //Feladási automata tele van?
    ResponseEntity<StringResponse> isParcelLockerFull(Long id);

    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
    ResponseEntity<List<StringResponse>> areBoxesFull(Long senderParcelLockerId);
}
