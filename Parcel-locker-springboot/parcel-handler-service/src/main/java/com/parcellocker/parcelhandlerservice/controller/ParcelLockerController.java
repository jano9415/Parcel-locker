package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.service.impl.ParcelLockerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParcelLockerController {

    @Autowired
    private ParcelLockerServiceImpl parcelLockerService;
}
