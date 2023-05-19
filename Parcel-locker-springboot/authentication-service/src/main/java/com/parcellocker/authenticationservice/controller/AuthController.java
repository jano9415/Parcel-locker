package com.parcellocker.authenticationservice.controller;

import com.parcellocker.authenticationservice.payload.request.LogInRequest;
import com.parcellocker.authenticationservice.payload.request.SignUpRequest;
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

    //Futár bejelentkezés
    @PostMapping("/courierlogin")
    public ResponseEntity<?> courierLogin(@RequestParam String uniqueCourierId){
        return userService.courierLogin(uniqueCourierId);
    }

    //Regisztráció aktiválása
    @GetMapping("/activation/{signUpActivationCode}")
    public ResponseEntity<?> signUpActivation(@PathVariable String signUpActivationCode) {
        return userService.signUpActivation(signUpActivationCode);
    }


}
