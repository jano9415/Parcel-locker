package com.parcellocker.authenticationservice.controller;

import com.parcellocker.authenticationservice.payload.kafka.SecondFactorDTO;
import com.parcellocker.authenticationservice.payload.request.*;
import com.parcellocker.authenticationservice.payload.response.LoginResponse;
import com.parcellocker.authenticationservice.payload.response.StringResponse;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;


    //Bejelentkezés
    //Jwt token generálása
    //Nem szükséges jwt token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest) {
        return userService.logIn(logInRequest);
    }

    //Regisztráció
    //Nem szükséges jwt token
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        return userService.signUp(signUpRequest);
    }

    //Futár bejelentkezése az automatánál
    //Nem szükséges jwt token
    @PostMapping("/courierlogin/{parcelLockerId}")
    public ResponseEntity<?> courierLogin(@RequestBody LoginCourier request,
                                          @PathVariable Long parcelLockerId){
        return userService.courierLogin(request, parcelLockerId);
    }

    //Regisztráció aktiválása
    //Nem szükséges jwt token
    @GetMapping("/activation/{signUpActivationCode}")
    public ResponseEntity<StringResponse> signUpActivation(@PathVariable String signUpActivationCode) {
        return userService.signUpActivation(signUpActivationCode);
    }

    //Új futár létrehozása
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @PostMapping("/createcourier")
    public ResponseEntity<?> createCourier(@RequestBody CreateCourierDTO courierDTO){
        return userService.createCourier(courierDTO);
    }

    //Új admin létrehozása
    //Jwt token szükséges
    //Admin szerepkör szükséges
    @PostMapping("/createadmin")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminDTO adminDTO){
        return userService.createAdmin(adminDTO);
    }

    //Futár valamely adatának módosítása
    //A kérés a parcel handler service-ből jön
    @PutMapping("/updatecourier")
    public ResponseEntity<StringResponse> updateCourier(@RequestBody UpdateCourierRequest request){
        return userService.updateCourier(request);

    }

    //Bejelentkezés a második faktorral
    @PostMapping("/loginwithsecondfactor")
    public ResponseEntity<?> loginWithSecondFactor(@RequestBody SecondFactorDTO request){
        return userService.loginWithSecondFactor(request);
    }

    //Személyes adatok lekérése
    @GetMapping("/getpersonaldata/{emailAddress}")
    public ResponseEntity<?> getPersonalData(@PathVariable String emailAddress){
        return userService.getPersonalData(emailAddress);
    }

    //Felhasználó valamely adatának módosítása
    //A kérés a parcel handler service-ből jön
    @PutMapping("/updateuser")
    public ResponseEntity<StringResponse> updateUser(@RequestBody UpdateUserRequestToAuthService request){
        return userService.updateUser(request);
    }


}
