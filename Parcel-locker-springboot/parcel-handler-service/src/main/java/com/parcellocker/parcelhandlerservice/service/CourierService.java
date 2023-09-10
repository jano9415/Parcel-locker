package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.payload.CourierDTO;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourierService {

    //Összes futár keresése
    List<Courier> findAll();

    //Keresés id alapján
    Courier findById(Long id);

    //Futár mentése
    void save(Courier courier);

    //Új futár hozzáadása az adatbázishoz.
    ResponseEntity<String> createCourier(CreateCourierDTO courierDTO);

    //Keresés egyedi futár azonosító alapján
    Courier findByUniqueCourierId(String uniqueCourierId);

    //Futár jogosultságának ellenőrzése az automatához
    //Csak a saját körzetében lévő automatákba tud bejelentkezni
    ResponseEntity<StringResponse> isCourierEligible(Long parcelLockerId, String uniqueCourierId);

    //Összes futár lekérése
    ResponseEntity<List<CourierDTO>> getCouriers();

    //Futár valamely adatának módosítása
    ResponseEntity<StringResponse> updateCourier(UpdateCourierRequest request);

    //Futár lekérése id alapján
    ResponseEntity<CourierDTO> findCourierById(Long courierId);
}
