package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcelhandler/parcel")
public class ParcelController {

    @Autowired
    private ParcelServiceImpl parcelService;

    //Csomag küldése feladási kód nélkül
    @PostMapping("/sendparcelwithoutcode/{senderParcelLockerId}")
    public ResponseEntity<?> sendParcelWithoutCode(@RequestBody ParcelSendingWithoutCodeRequest request,
                                                                @PathVariable Long senderParcelLockerId){

        return parcelService.sendParcelWithoutCode(request, senderParcelLockerId);
    }
}
