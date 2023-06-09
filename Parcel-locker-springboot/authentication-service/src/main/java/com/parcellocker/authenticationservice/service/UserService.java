package com.parcellocker.authenticationservice.service;

import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.payload.request.*;
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

    //Létezik ez a jelszó?
    boolean existsByPassword(String password);

    //Keresés email cím és jelszó szerint
    User findByEmailAddressAndPassword(String emailAddress, String password);

    //Keresés email cím szerint
    User findByEmailAddress(String emailAddress);

    //Keresés jelszó szerint
    User findByPassword(String password);

    //Létezik ez az email cím és jelszó?
    boolean existsByEmailAddressAndPassword(String emailAddress, String password);

    //Futár bejelentkezés
    ResponseEntity<?> courierLogin(LoginCourier request);

    //Regisztráció aktiválása
    ResponseEntity<?> signUpActivation(String activationCode);

    //Keresés aktivációs kód szerint
    User findByActivationCode(String activationCode);

    //Új futár létrehozása
    ResponseEntity<?> createCourier(CreateCourierDTO courierDTO);

    //Új admin létrehozása
    ResponseEntity<?> createAdmin(CreateAdminDTO adminDTO);
}
