package com.parcellocker.authenticationservice.repository;

import com.parcellocker.authenticationservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    //Létezik ez az email cím?
    boolean existsByEmailAddress(String emailAddress);

    //Létezik ez a jelszó?
    boolean existsByPassword(String password);

    //Létezik ez az email cím és jelszó?
    boolean existsByEmailAddressAndPassword(String emailAddress, String password);


    //Keresés email cím és jelszó szerint
    User findByEmailAddressAndPassword(String emailAddress, String password);

    //Keresés email cím szerint
    User findByEmailAddress(String emailAddress);

    //Keresés jelszó szerint
    User findByPassword(String password);

    //Keresés aktivációs kód szerint
    User findByActivationCode(String activationCode);


}
