package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.CourierDTO;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequest;
import com.parcellocker.parcelhandlerservice.service.impl.CourierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //Az authentication service hívja meg ezt a kérést
    @GetMapping("/iscouriereligible/{parcelLockerId}/{uniqueCourierId}")
    public ResponseEntity<StringResponse> isCourierEligible(@PathVariable Long parcelLockerId,
                                                            @PathVariable String uniqueCourierId){
        return courierService.isCourierEligible(parcelLockerId, uniqueCourierId);
    }

    //Összes futár lekérése
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/getcouriers")
    public ResponseEntity<List<CourierDTO>> getCouriers(){
        return courierService.getCouriers();

    }

    //Futár valamely adatának módosítása
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @PutMapping("/updatecourier")
    public ResponseEntity<StringResponse> updateCourier(@RequestBody UpdateCourierRequest request){
        return courierService.updateCourier(request);

    }

    //Futár lekérése id alapján
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @GetMapping("/findcourierbyid/{courierId}")
    public ResponseEntity<CourierDTO> findCourierById(@PathVariable Long courierId){
        return courierService.findCourierById(courierId);
    }
}
