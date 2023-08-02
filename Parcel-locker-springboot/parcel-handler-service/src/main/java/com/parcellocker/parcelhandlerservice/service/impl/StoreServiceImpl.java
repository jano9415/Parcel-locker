package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Store;
import com.parcellocker.parcelhandlerservice.payload.response.GetStoresResponse;
import com.parcellocker.parcelhandlerservice.repository.StoreRepository;
import com.parcellocker.parcelhandlerservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;


    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store findById(Long id) {
        return storeRepository.findById(id).get();
    }

    @Override
    public void save(Store store) {
        storeRepository.save(store);

    }

    //Központi raktárak lekérése
    //Jwt token szükséges
    @Override
    public ResponseEntity<List<GetStoresResponse>> getStores() {

        List<GetStoresResponse> response = new ArrayList<>();

        for(Store store : findAll()){
            GetStoresResponse responseObj = new GetStoresResponse();
            responseObj.setId(store.getId());
            responseObj.setPostCode(store.getAddress().getPostCode());
            responseObj.setCounty(store.getAddress().getCounty());
            responseObj.setCity(store.getAddress().getCity());
            responseObj.setStreet(store.getAddress().getStreet());

            response.add(responseObj);

        }
        return ResponseEntity.ok(response);
    }
}
