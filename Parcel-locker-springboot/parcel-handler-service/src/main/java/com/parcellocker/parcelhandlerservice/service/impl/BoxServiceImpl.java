package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;
import com.parcellocker.parcelhandlerservice.repository.BoxRepository;
import com.parcellocker.parcelhandlerservice.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Override
    public List<Box> findAll() {
        return boxRepository.findAll();
    }

    @Override
    public Box findById(Long id) {
        return boxRepository.findById(id).get();
    }

    @Override
    public void save(Box box) {
        boxRepository.save(box);

    }
}
