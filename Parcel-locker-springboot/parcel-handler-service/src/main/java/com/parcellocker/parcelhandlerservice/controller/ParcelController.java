package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.parcellocker.parcelhandlerservice.payload.response.*;
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
    public ResponseEntity<List<EmptyParcelLockerResponse>> emptyParcelLocker(@RequestBody EmptyParcelLockerRequest request){
        return parcelService.emptyParcelLocker(request);
    }

    //Futárnál lévő csomagok lekérése. Csak olyan csomagok, amik az adott automatához tartoznak és van nekik szabad rekesz
    //Jwt token szükséges
    @GetMapping("/getparcelsforparcellocker/{senderParcelLockerId}/{uniqueCourierId}")
    public ResponseEntity<List<GetParcelsForParcelLockerResponse>> getParcelsForParcelLocker(@PathVariable Long senderParcelLockerId,
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

    //Csomag átvétele
    //Ha már ki van fizetve, akkor át lehet venni. Adatok frissítése az adatbázisban és visszatérés a csomag adataival
    //Response -> message, boxNumber, price
    //Ha még nincs kifizetve a csomag, az átvétel még nem történik meg, csak visszatérek a csomag adataival.
    //Ekkor az adatbázis még nem lesz frissítve.
    //Nem szükséges jwt token
    @GetMapping("/pickupparcel/{pickingUpCode}/{senderParcelLockerId}")
    public ResponseEntity<PickUpParcelResponse> pickUpParcel(@PathVariable String pickingUpCode,
                                                             @PathVariable Long senderParcelLockerId){
        return parcelService.pickUpParcel(pickingUpCode, senderParcelLockerId);
    }

    //Csomag átvétele fizetés után. Adatbázis frissítése.
    //Nem szükséges jwt token
    @GetMapping("/pickupparcelafterpayment/{pickingUpCode}/{senderParcelLockerId}")
    public ResponseEntity<StringResponse> pickUpParcelAfterPayment(@PathVariable String pickingUpCode,
                                                             @PathVariable Long senderParcelLockerId){
        return parcelService.pickUpParcelAfterPayment(pickingUpCode, senderParcelLockerId);
    }

    //Keresés feladási kód szerint
    //Ha van csomag és a feladási automata megegyezik a kérésben érkező feladási automatával, akkor visszatérek
    //a rekesz számával
    //Különben a csomag nem található
    //Nem szükséges jwt token
    @GetMapping("/getparcelforsendingwithcode/{sendingCode}/{senderParcelLockerId}")
    public ResponseEntity<GetParcelForSendingWithCodeResponse> getParcelForSendingWithCode(@PathVariable String sendingCode,
                                                                                           @PathVariable Long senderParcelLockerId){
        return parcelService.getParcelForSendingWithCode(sendingCode, senderParcelLockerId);
    }

    //Csomag küldése az automatából feladási kóddal
    //Ennek a funkciónak az előzménye a csomagfeladás kóddal a weblapról
    //Ekkor történik meg a tényleges csomagfeladás
    //Nem szükséges jwt token
    @GetMapping("/sendparcelwithcode/{sendingCode}/{senderParcelLockerId}")
    public ResponseEntity<StringResponse> sendParcelWithCode(@PathVariable String sendingCode,
                                                                   @PathVariable Long senderParcelLockerId){
        return parcelService.sendParcelWithCode(sendingCode, senderParcelLockerId);
    }

    //Csomag küldése a weblapról feladási kóddal
    //Ez még csak egy előzetes csomagfeladás. A felhasználó megkapja email-ben a csomagfeladási kódot
    //A végleges csomagfeladás az automatánál történik
    //Jwt token szükséges
    @PostMapping("/sendparcelwithcodefromwebpage")
    public ResponseEntity<StringResponse> sendParcelWithCodeFromWebpage(@RequestBody SendParcelWithCodeFromWebpageRequest request){
        return parcelService.sendParcelWithCodeFromWebpage(request);
    }

    //Csomag követése
    //Nem szükséges jwt token
    @GetMapping("/followparcel/{uniqueParcelId}")
    public ResponseEntity<FollowParcelResponse> followParcel(@PathVariable String uniqueParcelId){
        return parcelService.followParcel(uniqueParcelId);
    }
}
