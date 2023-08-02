package com.parcellocker.parcelhandlerservice.service;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Store;
import com.parcellocker.parcelhandlerservice.payload.response.GetStoresResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StoreService {

    //Összes raktár keresése
    List<Store> findAll();

    //Keresés id alapján
    Store findById(Long id);

    //Raktár mentése
    void save(Store store);

    //Központi raktárak lekérése
    //Jwt token szükséges
    ResponseEntity<List<GetStoresResponse>> getStores();
}
