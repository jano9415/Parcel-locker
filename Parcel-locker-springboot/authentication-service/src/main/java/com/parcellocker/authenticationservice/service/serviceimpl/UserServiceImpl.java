package com.parcellocker.authenticationservice.service.serviceimpl;

import com.parcellocker.authenticationservice.kafka.Producer;
import com.parcellocker.authenticationservice.model.Role;
import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.payload.request.*;
import com.parcellocker.authenticationservice.payload.response.LoginResponse;
import com.parcellocker.authenticationservice.payload.response.SignUpActivationDTO;
import com.parcellocker.authenticationservice.repository.UserRepository;
import com.parcellocker.authenticationservice.service.UserService;
import com.parcellocker.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Producer producer;

    @Autowired
    private WebClient.Builder webClientBuilder;

    //Keresés id szerint
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    //Regisztráció. Új felhasználó hozzáadása
    //DTO objektum küldése apache kafkának
    //DTO objektum küldése a parcel handler service-nek
    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {

        //A megadott email cím már létezik
        if(existsByEmailAddress(signUpRequest.getEmailAddress())){
            return ResponseEntity.badRequest().body("Ez az email cím már regisztrálva van!");
        }

        //Új felhasználó létrehozása
        User user = new User();
        user.setEnable(false);
        user.setEmailAddress(signUpRequest.getEmailAddress());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        //Regisztrációhoz szükséges kód generálása
        user.setActivationCode(generateRandomStringForActivationCode());

        //Új ParcelHandlerServiceUserDTO objektum létrehozása
        //Ezt az objektumot küldöm a parcel handler service-nek
        ParcelHandlerServiceUserDTO parcelHandlerUser = new ParcelHandlerServiceUserDTO();
        parcelHandlerUser.setFirstName(signUpRequest.getFirstName());
        parcelHandlerUser.setLastName(signUpRequest.getLastName());
        parcelHandlerUser.setEmailAddress(signUpRequest.getEmailAddress());
        parcelHandlerUser.setPhoneNumber(signUpRequest.getPhoneNumber());



        //SignUpActivationDTO objektum létrehozása majd küldése az apache kafka topicnak
        //A topic neve: "signup_email_topic"
        //Regisztráció megerősítéséhez szükséges kód küldése email-ben
        SignUpActivationDTO signUpActivationDTO = new SignUpActivationDTO();
        signUpActivationDTO.setFirstName(signUpRequest.getFirstName());
        signUpActivationDTO.setLastName(signUpRequest.getLastName());
        signUpActivationDTO.setActivationCode(user.getActivationCode());
        signUpActivationDTO.setEmailAddress(signUpRequest.getEmailAddress());

        producer.sendActivationCodeForSignUp(signUpActivationDTO);

        //ParcelHandlerServiceUserDTO objektum küldése a parcel-handler service-nek
        //Ez a user objektum máshogy néz ki, mint az authentication-service user objektuma
        //Szinkron kommunikáció. "/parcelhandler/user/createuser" végpont meghívása a parcel handler service-ben.
        webClientBuilder.build().post()
                //Végpont. Elég csak a service nevét megadni. A disovery service meg fogja találni
                .uri("http://parcel-handler-service/parcelhandler/user/createuser")
                //Objektum, amit body-ban küldök és objektum típusa
                .body(Mono.just(parcelHandlerUser), ParcelHandlerServiceUserDTO.class)
                .retrieve()
                //Visszatérési (response) objektum típusa
                .bodyToMono(String.class)
                .block();


        //Felhasználó szerepköreinek beállítása
        Set<Role> roles = new HashSet<>();

        Role userRole = roleService.findByRoleName("user")
                .orElseThrow(() -> new RuntimeException("User szerepkör nem található"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Új felhasználó sikeresen hozzáadva");

    }

    //Bejelentkezés
    //Sikeres bejelentkezés esetén visszatérés egy LoginResponse objektummal
    //A LoginResponse objektum tartalmazza: - user id, token típusa, token, email cím, szerepkörök
    @Override
    public ResponseEntity<?> logIn(LogInRequest logInRequest) {

        User user = findByEmailAddress(logInRequest.getEmailAddress());

        if(user == null){
            return ResponseEntity.badRequest().body("Hibás email cím");
        }

        if(!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("Hibás jelszó");
        }

        if(user.isEnable() == false){
            return ResponseEntity.badRequest().body("Aktiváld a felhasználói fiókodat");
        }

        String token = jwtUtil.generateToken(logInRequest.getEmailAddress());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setEmailAddress(user.getEmailAddress());
        loginResponse.setToken(token);

        List<String> roles = user.getRoles().stream().map(item -> item.getRoleName())
                .collect(Collectors.toList());
        loginResponse.setRoles(roles);

        return ResponseEntity.ok(loginResponse);

    }

    //Létezik ez az email cím?
    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        return userRepository.existsByEmailAddress(emailAddress);
    }

    //Létezik ez a jelszó?
    @Override
    public boolean existsByPassword(String password) {
        return userRepository.existsByPassword(password);
    }

    //Keresés email cím és jelszó szerint
    @Override
    public User findByEmailAddressAndPassword(String emailAddress, String password) {
        return userRepository.findByEmailAddressAndPassword(emailAddress, password);
    }

    //Keresés email cím szerint
    @Override
    public User findByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    //Keresés jelszó szerint
    @Override
    public User findByPassword(String password) {
        return userRepository.findByPassword(password);
    }

    //Létezik ez az email cím és jelszó?
    @Override
    public boolean existsByEmailAddressAndPassword(String emailAddress, String password) {
        return userRepository.existsByEmailAddressAndPassword(emailAddress, password);
    }

    //Futár bejelentkezés
    //Csak jelszó érkezik a kérésbe
    //Futár keresése jelszó szerint
    //Sikeres bejelentkezés esetén visszatérés egy loginResponse objektummal
    //Ez az objektum tartalmazza: jwt token (ami tartalmazza a futár egyedi azonosítóját), id,
    // egyedi azonosító újra (ez nem biztos, hogy kelleni fog) és a szerepkörök
    @Override
    public ResponseEntity<?> courierLogin(LoginCourier request) {

        String sha256Password = sha256Encode(request.getPassword());

        User user = findByPassword(sha256Password);

        if(user == null){
            return ResponseEntity.badRequest().body("Hibás azonosító");
        }

        String token = jwtUtil.generateToken(user.getEmailAddress());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setEmailAddress(user.getEmailAddress());
        loginResponse.setToken(token);

        List<String> roles = user.getRoles().stream().map(item -> item.getRoleName())
                .collect(Collectors.toList());
        loginResponse.setRoles(roles);

        return ResponseEntity.ok(loginResponse);
    }

    //Regisztráció aktiválása
    //Keresés aktivációs kód szerint
    //Aktivációs kód null-ra állítása
    //Enable mező true-ra állítása
    @Override
    public ResponseEntity<?> signUpActivation(String signUpActivationCode) {
        User user = findByActivationCode(signUpActivationCode);

        if(user == null){
            return ResponseEntity.badRequest().body("Sikertelen regisztráció aktiválás");
        }

        user.setActivationCode(null);
        user.setEnable(true);
        userRepository.save(user);

        return ResponseEntity.ok("Felhasználói fiók sikeresen aktiválva");

    }

    //Keresés aktivációs kód szerint
    @Override
    public User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode);
    }

    //Új futár létrehozása
    //Futár objektum küldése a parcel handler service-nek
    @Override
    public ResponseEntity<?> createCourier(CreateCourierDTO courierDTO) {

        //A megadott futár azonosító már létezik
        if(existsByEmailAddress(courierDTO.getUniqueCourierId())){
            return ResponseEntity.badRequest().body("Ez a futár azonosító már regisztrálva van!");
        }

        String sha256Password = sha256Encode(courierDTO.getPassword());

        //Futár esetén a jelszót is ellenőrizni kell. Kettő ugyan olyan nem lehet, mert a jelszó egyben a bejelentkezési
        //RFID azonosító is
        if(existsByPassword(sha256Password)){
            return ResponseEntity.badRequest().body("Ez a jelszó (RFID azonosító) már regisztrálva van");
        }

        //Új futár létrehozása
        User user = new User();
        user.setEnable(true);
        user.setEmailAddress(courierDTO.getUniqueCourierId());
        user.setPassword(sha256Password);

        //CourierDTO objektum létrehozása. Ezt az objektumot külöm a parcel-handler service-nek.
        //Ez az objektum már nem tartalmaz jelszót, viszont tartalmaz vezeték és kereszt nevet
        CourierDTO courierToParcelHandlerService = new CourierDTO();
        courierToParcelHandlerService.setUniqueCourierId(courierDTO.getUniqueCourierId());
        courierToParcelHandlerService.setFirstName(courierDTO.getFirstName());
        courierToParcelHandlerService.setLastName(courierDTO.getLastName());

        //Futár küldése a parcel handler service-nek
        webClientBuilder.build().post()
                .uri("http://parcel-handler-service/parcelhandler/courier/createcourier")
                .body(Mono.just(courierToParcelHandlerService), CourierDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Futár szerepköreinek beállítása
        Set<Role> roles = new HashSet<>();

        Role courierRole = roleService.findByRoleName("courier")
                .orElseThrow(() -> new RuntimeException("Futár szerepkör nem található"));
        roles.add(courierRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Új futár sikeresen hozzáadva");
    }

    //Új admin létrehozása
    @Override
    public ResponseEntity<?> createAdmin(CreateAdminDTO adminDTO) {

        //A megadott email cím már létezik
        if(existsByEmailAddress(adminDTO.getEmailAddress())){
            return ResponseEntity.badRequest().body("Ez az email cím már regisztrálva van!");
        }

        //Új admin létrehozása
        User user = new User();
        user.setEnable(true);
        user.setEmailAddress(adminDTO.getEmailAddress());
        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        //Admin szerepköreinek beállítása
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleService.findByRoleName("admin")
                .orElseThrow(() -> new RuntimeException("Admin szerepkör nem található"));
        roles.add(adminRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Új admin sikeresen hozzáadva");
    }

    //Random string generálása a regisztrációhoz szükséges aktivációs kód számára
    public String generateRandomStringForActivationCode() {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        int length = 8;

        for(int i = 0; i < length; i++) {

            int index = random.nextInt(alphaNumeric.length());

            char randomChar = alphaNumeric.charAt(index);

            sb.append(randomChar);
        }

        String randomString = sb.toString();

        return randomString;
    }

    //SHA-256 kódolás
    public String sha256Encode(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

}
