package com.parcellocker.authenticationservice.controller;

import com.parcellocker.authenticationservice.payload.request.*;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;


    //Bejelentkezés
    //Jwt token generálása
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest) {
        return userService.logIn(logInRequest);
    }

    //Regisztráció
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        return userService.signUp(signUpRequest);
    }

    //Futár bejelentkezése az automatánál
    @PostMapping("/courierlogin/{parcelLockerId}")
    public ResponseEntity<?> courierLogin(@RequestBody LoginCourier request,
                                          @PathVariable Long parcelLockerId){
        return userService.courierLogin(request, parcelLockerId);
    }

    //Regisztráció aktiválása
    @GetMapping("/activation/{signUpActivationCode}")
    public ResponseEntity<?> signUpActivation(@PathVariable String signUpActivationCode) {
        return userService.signUpActivation(signUpActivationCode);
    }

    //Új futár létrehozása
    @PostMapping("/createcourier")
    public ResponseEntity<?> createCourier(@RequestBody CreateCourierDTO courierDTO){
        return userService.createCourier(courierDTO);
    }

    //Új admin létrehozása
    @PostMapping("/createadmin")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminDTO adminDTO){
        return userService.createAdmin(adminDTO);
    }


}
