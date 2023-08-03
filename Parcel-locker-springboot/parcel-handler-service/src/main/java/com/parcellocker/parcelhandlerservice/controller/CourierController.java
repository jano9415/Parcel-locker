package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.service.impl.CourierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Futár jogosultságának ellenőrzése az automatához
    //Csak a saját körzetében lévő automatákba tud bejelentkezni
    @GetMapping("/iscouriereligible/{parcelLockerId}/{uniqueCourierId}")
    public ResponseEntity<StringResponse> isCourierEligible(@PathVariable Long parcelLockerId,
                                                            @PathVariable String uniqueCourierId){
        return courierService.isCourierEligible(parcelLockerId, uniqueCourierId);
    }
}
