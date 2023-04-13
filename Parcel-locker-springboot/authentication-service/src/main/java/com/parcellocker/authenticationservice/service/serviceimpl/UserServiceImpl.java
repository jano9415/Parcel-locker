package com.parcellocker.authenticationservice.service.serviceimpl;

import com.parcellocker.authenticationservice.model.Role;
import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.payload.request.LogInRequest;
import com.parcellocker.authenticationservice.payload.request.SignUpRequest;
import com.parcellocker.authenticationservice.payload.response.LoginResponse;
import com.parcellocker.authenticationservice.repository.UserRepository;
import com.parcellocker.authenticationservice.service.UserService;
import com.parcellocker.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    //Keresés id szerint
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    //Regisztráció. Új felhasználó hozzáadása
    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {

        //A megadott email cím már létezik
        if(existsByEmailAddress(signUpRequest.getEmailAddress())){
            return ResponseEntity.badRequest().body("Ez az email cím már regisztrálva van!");
        }

        //Új felhasználó létrehozása
        User user = new User();
        user.setLastName(signUpRequest.getLastName());
        user.setFirstName(signUpRequest.getFirstName());
        user.setEmailAddress(signUpRequest.getEmailAddress());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        //Felhasználó szerepköreinek beállítása
        //Ha nem érkezik a klienstől szerepkör, akkor automatikusan az új felhasználó user szerepkört kap
        //Admin és courier szerepkör érkezhet a kérésben, mert ők nem tudnak regisztrálni, hanem
        //az admin adja hozzá őket
        if (strRoles == null) {
            Role userRole = roleService.findByRoleName("user")
                    .orElseThrow(() -> new RuntimeException("User szerepkör nem található"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByRoleName("admin")
                                .orElseThrow(() -> new RuntimeException("Admin szerepkör nem található"));
                        roles.add(adminRole);

                        break;
                    case "courier":
                        Role modRole = roleService.findByRoleName("courier")
                                .orElseThrow(() -> new RuntimeException("Futár szerepkör nem található"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleService.findByRoleName("user")
                                .orElseThrow(() -> new RuntimeException("User szerepkör nem található"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Új felhasználó sikeresen hozzáadva");

    }

    //Bejelentkezés
    //Sikeres bejelentkezés esetén visszatérés egy LoginResponse objektummal
    //A LoginResponse objektum tartalmazza: - user id, token típusa, token, vezetéknév, keresztnév, email cím, szerepkörök
    @Override
    public ResponseEntity<?> logIn(LogInRequest logInRequest) {

        User user = findByEmailAddress(logInRequest.getEmailAddress());

        if(user == null){
            return ResponseEntity.badRequest().body("Hibás email cím");
        }

        if(!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("Hibás jelszó");
        }

        String token = jwtUtil.generateToken(logInRequest.getEmailAddress());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setFirstName(user.getFirstName());
        loginResponse.setLastName(user.getLastName());
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
}
