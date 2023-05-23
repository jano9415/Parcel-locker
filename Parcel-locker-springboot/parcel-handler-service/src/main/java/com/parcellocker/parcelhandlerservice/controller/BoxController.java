package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.service.impl.BoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoxController {

    @Autowired
    private BoxServiceImpl boxService;
}
