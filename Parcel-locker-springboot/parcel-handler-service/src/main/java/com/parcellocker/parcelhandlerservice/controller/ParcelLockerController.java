package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelLockerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    //Feladási automata tele van?
    @GetMapping("/isparcellockerfull/{senderParcelLockerId}")
    public ResponseEntity<StringResponse> isParcelLockerFull(@PathVariable Long senderParcelLockerId){
        return parcelLockerService.isParcelLockerFull(senderParcelLockerId);
    }

    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
    @GetMapping("/areboxesfull/{senderParcelLockerId}")
    public ResponseEntity<List<StringResponse>> areBoxesFull(@PathVariable Long senderParcelLockerId){

        return parcelLockerService.areBoxesFull(senderParcelLockerId);
    }
}
