package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.service.impl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParcelController {

    @Autowired
    private ParcelServiceImpl parcelService;
}
