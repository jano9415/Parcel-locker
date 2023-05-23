package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.service.impl.CourierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parcelhandler/courier")
public class CourierController {

    @Autowired
    private CourierServiceImpl courierService;

    //Új futár hozzáadása az adatbázishoz. Az objektum az authentication service-ből jön
    @PostMapping("/createcourier")
    public ResponseEntity<String> createCourier(@RequestBody CreateCourierDTO courierDTO){
        return courierService.createCourier(courierDTO);
    }
}
