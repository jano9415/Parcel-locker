package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.model.User;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelHandlerServiceUserDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.response.GetPersonalDataResponse;
import com.parcellocker.parcelhandlerservice.repository.UserRepository;
import com.parcellocker.parcelhandlerservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    //Új user hozzáadása az adatbázishoz. A user objektum az authentication service-től érkezik szinkron kommunikációval.
    @Override
    public ResponseEntity<String> createUser(ParcelHandlerServiceUserDTO user) {
        User userForDB = new User();

        userForDB.setEmailAddress(user.getEmailAddress());
        userForDB.setFirstName(user.getFirstName());
        userForDB.setLastName(user.getLastName());
        userForDB.setPhoneNumber(user.getPhoneNumber());

        save(userForDB);

        return ResponseEntity.ok("User hozzáadva az adatbázishoz");
    }

    //Keresés email cím alapján
    @Override
    public User findByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    //Felhasználói adatok lekérése
    //Vezetéknév, keresztnév, telefonszám
    @Override
    public ResponseEntity<?> getPersonalData(String emailAddress) {

        StringResponse response = new StringResponse();

        User user = userRepository.findByEmailAddress(emailAddress);

        //Nem valószínű, mert bejelentkezés után jön a kérés
        if(user == null){
            response.setMessage("notFound");
            ResponseEntity.badRequest().body(response);
        }

        GetPersonalDataResponse responseDTO = new GetPersonalDataResponse();
        responseDTO.setLastName(user.getLastName());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setPhoneNumber(user.getPhoneNumber());

        return ResponseEntity.ok(responseDTO);
    }


}
