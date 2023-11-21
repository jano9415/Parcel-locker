package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelHandlerServiceUserDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequest;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateUserRequest;
import com.parcellocker.parcelhandlerservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcelhandler/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //Új user hozzáadása az adatbázishoz. Az objektum az authentication service-ből jön
    @PostMapping("/createuser")
    public ResponseEntity<String> createUser(@RequestBody ParcelHandlerServiceUserDTO user){
        return userService.createUser(user);

    }

    //Felhasználói adatok lekérése
    @GetMapping("/getpersonaldata/{emailAddress}")
    public ResponseEntity<?> getPersonalData(@PathVariable String emailAddress){
        return userService.getPersonalData(emailAddress);
    }

    //Felhasználó valamely adatának módosítása
    //Jwt token szükséges
    //User szerepkör szükséges
    @PutMapping("/updateuser")
    public ResponseEntity<StringResponse> updateUser(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);

    }



}
