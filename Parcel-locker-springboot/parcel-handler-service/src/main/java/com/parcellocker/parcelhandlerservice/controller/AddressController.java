package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.service.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;
}
