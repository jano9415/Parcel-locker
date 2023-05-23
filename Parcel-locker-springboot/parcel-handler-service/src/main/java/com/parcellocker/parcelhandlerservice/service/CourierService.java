package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
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
}
