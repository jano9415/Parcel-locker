package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;
}
