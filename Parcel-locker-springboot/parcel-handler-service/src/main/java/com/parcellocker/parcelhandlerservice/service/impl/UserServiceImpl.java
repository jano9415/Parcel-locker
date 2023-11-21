package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.User;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelHandlerServiceUserDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateUserRequest;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateUserRequestToAuthService;
import com.parcellocker.parcelhandlerservice.payload.response.GetPersonalDataResponse;
import com.parcellocker.parcelhandlerservice.repository.UserRepository;
import com.parcellocker.parcelhandlerservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //Felhasználó valamely adatának módosítása
    @Override
    @Transactional
    public ResponseEntity<StringResponse> updateUser(UpdateUserRequest request) {

        StringResponse response = new StringResponse();

        User user = findById(request.getId());

        //Régi email cím
        String previousEmailAddress = user.getEmailAddress();
        //Új email cím
        String newEmailAddress = request.getEmailAddress();

        //Nem valószínű, mert bejelentkezés után jön a kérés
        if(user == null){
            response.setMessage("notFound");
            return ResponseEntity.badRequest().body(response);
        }

        //A megadott email cím már létezik az adatbázisban
        //Azt is meg kell nézni, hogy a régi és az új email cím megegyezik-e
        //Mert ha megegyezik, akkor mindig már létezik az adatbázisban hibát fog visszaküldeni
        if(!previousEmailAddress.equals(newEmailAddress) && findByEmailAddress(newEmailAddress) != null){
            response.setMessage("emailAddressExists");
            return ResponseEntity.badRequest().body(response);
        }

        //Parcel handler adatbázis frissítése
        user.setEmailAddress(request.getEmailAddress());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());

        save(user);

        //Ha megváltoztatta az email címét vagy a kétfaktoros bejelentkezési lehetőséget
        //akkor módosítani kell az auth database-t is
        //Kérés objektum az authentication service-nek
        UpdateUserRequestToAuthService requestToAuthService = new UpdateUserRequestToAuthService();
        requestToAuthService.setPreviousEmailAddress(previousEmailAddress);
        requestToAuthService.setNewEmailAddress(newEmailAddress);
        requestToAuthService.setTwoFactorAuthentication(request.isTwoFactorAuthentication());


        return null;
    }


}
