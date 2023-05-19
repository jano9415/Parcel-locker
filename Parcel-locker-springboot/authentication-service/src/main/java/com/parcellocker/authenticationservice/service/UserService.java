package com.parcellocker.authenticationservice.service;

import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.payload.request.LogInRequest;
import com.parcellocker.authenticationservice.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    //Keresés id szerint
    User findById(Long id);

    //Regisztráció. Új user hozzáadása
    ResponseEntity<?> signUp(SignUpRequest signUpRequest);


    //Bejelentkezés
    ResponseEntity<?> logIn(LogInRequest logInRequest);

    //Létezik ez az email cím?
    boolean existsByEmailAddress(String emailAddress);

    //Keresés email cím és jelszó szerint
    User findByEmailAddressAndPassword(String emailAddress, String password);

    //Keresés email cím szerint
    User findByEmailAddress(String emailAddress);

    //Keresés jelszó szerint
    User findByPassword(String password);

    //Létezik ez az email cím és jelszó?
    boolean existsByEmailAddressAndPassword(String emailAddress, String password);

    //Futár bejelentkezés
    ResponseEntity<?> courierLogin(String uniqueCourierId);

    //Regisztráció aktiválása
    ResponseEntity<?> signUpActivation(String activationCode);

    //Keresés aktivációs kód szerint
    User findByActivationCode(String activationCode);
}
