package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelLockerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcelhandler/parcellocker")
public class ParcelLockerController {

    @Autowired
    private ParcelLockerServiceImpl parcelLockerService;


    //Csomag automaták lekérése. Ezekből lehet kiválasztani az angular alkalmazásban a feladási automatát.
    @GetMapping("/getparcellockersforchoice")
    public ResponseEntity<List<ParcelLockerDTO>> getParcelLockersForChoice(){
        return parcelLockerService.getParcelLockersForChoice();
    }
}
