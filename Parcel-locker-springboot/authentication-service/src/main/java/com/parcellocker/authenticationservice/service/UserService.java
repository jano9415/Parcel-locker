package com.parcellocker.authenticationservice.service;

import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.payload.kafka.SecondFactorDTO;
import com.parcellocker.authenticationservice.payload.request.*;
import com.parcellocker.authenticationservice.payload.response.LoginResponse;
import com.parcellocker.authenticationservice.payload.response.StringResponse;
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
    ResponseEntity<?> courierLogin(LoginCourier request, Long parcelLockerId);

    //Regisztráció aktiválása
    ResponseEntity<StringResponse> signUpActivation(String activationCode);

    //Keresés aktivációs kód szerint
    User findByActivationCode(String activationCode);

    //Keresés második faktor szerint
    User findBySecondFactorCode(String secondFactorCode);

    //Új futár létrehozása
    ResponseEntity<?> createCourier(CreateCourierDTO courierDTO);

    //Új admin létrehozása
    ResponseEntity<?> createAdmin(CreateAdminDTO adminDTO);

    //Futár valamely adatának módosítása
    //A kérés a parcel handler service-ből jön
    ResponseEntity<StringResponse> updateCourier(UpdateCourierRequest request);

    //Bejelentkezés a második faktorral
    ResponseEntity<?> loginWithSecondFactor(SecondFactorDTO request);

    //Személyes adatok lekérése
    ResponseEntity<?> getPersonalData(String emailAddress);

    //Felhasználó valamely adatának módosítása
    //A kérés a parcel handler service-ből jön
    ResponseEntity<StringResponse> updateUser(UpdateUserRequestToAuthService request);
}
