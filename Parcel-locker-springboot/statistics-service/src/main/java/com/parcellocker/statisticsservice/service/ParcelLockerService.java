package com.parcellocker.statisticsservice.service;

import com.parcellocker.statisticsservice.model.ParcelLocker;

import java.util.List;

public interface ParcelLockerService {

    //Keresés id szerint
    ParcelLocker findById(String id);

    //Mentés
    void save(ParcelLocker parcelLocker);

    //Összes lekérése
    List<ParcelLocker> findAll();
}
