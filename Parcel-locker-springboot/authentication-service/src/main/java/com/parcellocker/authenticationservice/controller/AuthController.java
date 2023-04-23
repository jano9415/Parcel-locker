package com.parcellocker.authenticationservice.controller;

import com.parcellocker.authenticationservice.payload.request.LogInRequest;
import com.parcellocker.authenticationservice.payload.request.SignUpRequest;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import com.parcellocker.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    //Bejelentkezés, ideiglenes
    //Jwt token generálása
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String emailAddress, @RequestParam String password) {

        return userService.logIn(emailAddress, password);
    }

    //Bejelentkezés
    //Jwt token generálása
    @PostMapping("/loginpost")
    public ResponseEntity<?> loginPost(@RequestBody LogInRequest logInRequest) {
        return userService.logIn(logInRequest);
    }

    //Regisztráció, ideiglenes
    @GetMapping("/signup")
    public ResponseEntity<?> signUp(@RequestParam String emailAddress, @RequestParam String password,
                                    @RequestParam String firstName, @RequestParam String lastName) {

        return userService.signUp(emailAddress, password, firstName, lastName);
    }

    //Regisztráció
    @PostMapping("/signuppost")
    public ResponseEntity<?> signUpPost(@RequestBody SignUpRequest signUpRequest) {

        return userService.signUp(signUpRequest);
    }

    //Teszt
    @GetMapping("/testauth")
    public ResponseEntity<?> testAuth(){
        return ResponseEntity.ok("Ez működik!");

    }

    //Teszt post
    @PostMapping("/testpost")
    public ResponseEntity<?> testPost(){
        System.out.println("A post is működik");
        return ResponseEntity.ok("A post is működik.");
    }

    //Futár bejelentkezés ideiglenes
    @GetMapping("/courierlogin")
    public ResponseEntity<?> courierLogin(@RequestParam String uniqueCourierId){
        return userService.courierLogin(uniqueCourierId);
    }
    //Futár bejelentkezés
    @PostMapping("/courierloginpost")
    public ResponseEntity<?> courierLoginPost(@RequestParam String uniqueCourierId){
        return userService.courierLogin(uniqueCourierId);
    }
    //Futár bejelentkezés
    @PutMapping("/courierloginput")
    public ResponseEntity<?> courierLoginPut(@RequestParam String uniqueCourierId){
        return userService.courierLogin(uniqueCourierId);
    }

    //Futár bejelentkezés
    @PatchMapping("/courierloginpatch")
    public ResponseEntity<?> courierLoginPatch(@RequestParam String uniqueCourierId){
        return userService.courierLogin(uniqueCourierId);
    }
}
