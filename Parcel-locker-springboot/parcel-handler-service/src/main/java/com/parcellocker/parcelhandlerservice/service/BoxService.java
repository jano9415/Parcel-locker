package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;

import java.util.List;

public interface BoxService {

    //Összes rekesz keresése
    List<Box> findAll();

    //Keresés id alapján
    Box findById(Long id);

    //Rekesz mentése
    void save(Box box);
}
