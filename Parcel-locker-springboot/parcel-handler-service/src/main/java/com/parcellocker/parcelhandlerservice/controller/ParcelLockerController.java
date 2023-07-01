package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelLockerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Csomag küldése feladási kód nélkül
    @PostMapping("/sendparcelwithoutcode")
    public ResponseEntity<String> sendParcelWithoutCode(@RequestBody ParcelSendingWithoutCodeRequest request){

        return parcelLockerService.sendParcelWithoutCode(request);
    }

    //Feladási automata tele van?
    @GetMapping("/isparcellockerfull/{id}")
    public ResponseEntity<String> isParcelLockerFull(@PathVariable Long id){
        return parcelLockerService.isParcelLockerFull(id);
    }
}
