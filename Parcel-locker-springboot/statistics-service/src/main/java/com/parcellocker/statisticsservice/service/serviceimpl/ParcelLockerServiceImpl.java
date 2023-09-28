package com.parcellocker.statisticsservice.service.serviceimpl;

import com.parcellocker.statisticsservice.model.ParcelLocker;
import com.parcellocker.statisticsservice.repository.ParcelLockerRepository;
import com.parcellocker.statisticsservice.service.ParcelLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelLockerServiceImpl implements ParcelLockerService {

    @Autowired
    private ParcelLockerRepository parcelLockerRepository;

    @Override
    public ParcelLocker findById(String id) {
        return parcelLockerRepository.findById(id).get();
    }

    @Override
    public void save(ParcelLocker parcelLocker) {

        parcelLockerRepository.save(parcelLocker);

    }

    @Override
    public List<ParcelLocker> findAll() {
        return parcelLockerRepository.findAll();
    }


}
