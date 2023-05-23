package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    //Összes cím keresése
    List<Address> findAll();

    //Keresés id alapján
    Address findById(Long id);

    //Cím mentése
    void save(Address address);
}
