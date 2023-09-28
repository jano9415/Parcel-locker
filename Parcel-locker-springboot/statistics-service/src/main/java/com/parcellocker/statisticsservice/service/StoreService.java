package com.parcellocker.statisticsservice.service;

import com.parcellocker.statisticsservice.model.Store;

import java.util.List;

public interface StoreService {

    //Keresés id szerint
    Store findById(String id);

    //Mentés
    void save(Store store);

    //Összes lekérése
    List<Store> findAll();
}
