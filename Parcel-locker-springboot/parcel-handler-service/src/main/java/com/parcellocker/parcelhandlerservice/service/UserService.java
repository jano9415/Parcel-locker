package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.User;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelHandlerServiceUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    //Összes user keresése
    List<User> findAll();

    //Keresés id alapján
    User findById(Long id);

    //User mentése
    void save(User user);

    //Új user hozzáadása az adatbázishoz. A user objektum az authentication service-től érkezik szinkron kommunikációval.
    ResponseEntity<String> createUser(ParcelHandlerServiceUserDTO user);

    //Keresés email cím alapján
    User findByEmailAddress(String emailAddress);

    //Felhasználói adatok lekérése
    ResponseEntity<?> getPersonalData(String emailAddress);
}
