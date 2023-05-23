package com.parcellocker.parcelhandlerservice.service.impl;

import com.netflix.discovery.converters.Auto;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelServiceImpl implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;


    @Override
    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    @Override
    public Parcel findById(Long id) {
        return parcelRepository.findById(id).get();
    }

    @Override
    public void save(Parcel parcel) {
        parcelRepository.save(parcel);

    }
}
