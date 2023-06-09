package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingRequest;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.response.EmptyParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.FillParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcelhandler/parcel")
public class ParcelController {

    @Autowired
    private ParcelServiceImpl parcelService;

    //Csomag küldése feladási kód nélkül
    //Autentikációhoz nem szükséges ehhez a végponthoz
    @PostMapping("/sendparcelwithoutcode/{senderParcelLockerId}")
    public ResponseEntity<?> sendParcelWithoutCode(@RequestBody ParcelSendingWithoutCodeRequest request,
                                                   @PathVariable Long senderParcelLockerId) {

        return parcelService.sendParcelWithoutCode(request, senderParcelLockerId);
    }

    //Csomagok lekérése, amik készen állnak az elszállításra
    //Autentikációhoz szükséges ehhez a végponthoz
    @GetMapping("/getparcelsforshipping/{senderParcelLockerId}")
    public ResponseEntity<List<GetParcelsForShippingResponse>> getParcelsForShipping(@PathVariable Long senderParcelLockerId) {

        return parcelService.getParcelsForShipping(senderParcelLockerId);
    }

    //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
    //Jwt token szükséges
    @PostMapping("/emptyparcellocker")
    public ResponseEntity<List<EmptyParcelLockerResponse>> emptyParcelLocker(@RequestBody EmptyParcelLocker request){
        return parcelService.emptyParcelLocker(request);
    }

    //Futárnál lévő csomagok lekérése. Csak olyan csomagok, amik az adott automatához tartoznak és van nekik szabad rekesz
    //Jwt token szükséges
    @GetMapping("/getparcelsforparcellocker/{senderParcelLockerId}/{uniqueCourierId}")
    public ResponseEntity<List<FillParcelLockerResponse>> getParcelsForParcelLocker(@PathVariable Long senderParcelLockerId,
                                                                                    @PathVariable String uniqueCourierId){
        return parcelService.getParcelsForParcelLocker(senderParcelLockerId, uniqueCourierId);
    }

    //Automata feltöltése
    //Jwt token szükséges
    @GetMapping("/fillparcellocker/{senderParcelLockerId}/{uniqueCourierId}")
    public ResponseEntity<List<FillParcelLockerResponse>> fillParcelLocker(@PathVariable Long senderParcelLockerId,
                                                                                    @PathVariable String uniqueCourierId){
        return parcelService.fillParcelLocker(senderParcelLockerId, uniqueCourierId);
    }
}
