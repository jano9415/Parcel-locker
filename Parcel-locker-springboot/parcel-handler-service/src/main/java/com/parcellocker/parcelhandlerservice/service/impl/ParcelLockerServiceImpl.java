package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.repository.ParcelLockerRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelLockerServiceImpl implements ParcelLockerService {

    @Autowired
    private ParcelLockerRepository parcelLockerRepository;


    @Override
    public List<ParcelLocker> findAll() {
        return parcelLockerRepository.findAll();
    }

    @Override
    public ParcelLocker findById(Long id) {
        return parcelLockerRepository.findById(id).get();
    }

    @Override
    public void save(ParcelLocker parcelLocker) {
        parcelLockerRepository.save(parcelLocker);

    }
}
