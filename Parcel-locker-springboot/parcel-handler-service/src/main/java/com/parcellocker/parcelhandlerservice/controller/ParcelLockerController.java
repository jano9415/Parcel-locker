package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
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
    @PostMapping("/sendparcelwithoutcode/{senderParcelLockerId}")
    public ResponseEntity<String> sendParcelWithoutCode(@RequestBody ParcelSendingWithoutCodeRequest request,
                                                        @PathVariable Long senderParcelLockerId){

        return parcelLockerService.sendParcelWithoutCode(request, senderParcelLockerId);
    }

    //Feladási automata tele van?
    @GetMapping("/isparcellockerfull/{senderParcelLockerId}")
    public ResponseEntity<StringResponse> isParcelLockerFull(@PathVariable Long senderParcelLockerId){
        return parcelLockerService.isParcelLockerFull(senderParcelLockerId);
    }

    //Kicsi rekeszek tele vannak?
    @GetMapping("/aresmallboxesfull/{senderParcelLockerId}")
    public ResponseEntity<StringResponse> areSmallBoxesFull(@PathVariable Long senderParcelLockerId){

        return parcelLockerService.areSmallBoxesFull(senderParcelLockerId);
    }
    //Közepes rekeszek tele vannak?

    //Nagy rekeszek tele vannak?
}
