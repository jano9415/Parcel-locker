package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Store;
import com.parcellocker.parcelhandlerservice.repository.StoreRepository;
import com.parcellocker.parcelhandlerservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
