package com.parcellocker.authenticationservice.controller;

import com.parcellocker.authenticationservice.payload.request.LogInRequest;
import com.parcellocker.authenticationservice.payload.request.SignUpRequest;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import com.parcellocker.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
