package com.parcellocker.statisticsservice.service.serviceimpl;

import com.netflix.discovery.converters.Auto;
import com.parcellocker.statisticsservice.model.Store;
import com.parcellocker.statisticsservice.repository.StoreRepository;
import com.parcellocker.statisticsservice.service.StoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Store findById(String id) {
        return storeRepository.findById(id).get();
    }

    @Override
    public void save(Store store) {
        storeRepository.save(store);

    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
