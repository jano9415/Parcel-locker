package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.model.Store;
import com.parcellocker.parcelhandlerservice.payload.CourierDTO;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequest;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequestToAuthService;
import com.parcellocker.parcelhandlerservice.repository.CourierRepository;
import com.parcellocker.parcelhandlerservice.service.CourierService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private StoreServiceImpl storeService;

    @Autowired
    private ParcelLockerServiceImpl parcelLockerService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Override
    public Courier findById(Long id) {
        return courierRepository.findById(id).get();
    }

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);

    }

    //Új futár hozzáadása az adatbázishoz.
    //A futár objektum az authentication-service-ből jön
    @Override
    public ResponseEntity<String> createCourier(CreateCourierDTO courierDTO) {
        Courier courier = new Courier();
        Store store = storeService.findById(courierDTO.getStoreId());

        courier.setUniqueCourierId(courierDTO.getUniqueCourierId());
        courier.setFirstName(courierDTO.getFirstName());
        courier.setLastName(courierDTO.getLastName());
        courier.setArea(store);

        save(courier);

        return ResponseEntity.ok("SuccesCreation");
    }

    //Keresés egyedi futár azonosító alapján
    @Override
    public Courier findByUniqueCourierId(String uniqueCourierId) {
        return courierRepository.findByUniqueCourierId(uniqueCourierId);
    }

    //Futár jogosultságának ellenőrzése az automatához
    //Csak a saját körzetében lévő automatákba tud bejelentkezni
    @Override
    public ResponseEntity<StringResponse> isCourierEligible(Long parcelLockerId, String uniqueCourierId) {

        Courier courier = findByUniqueCourierId(uniqueCourierId);
        ParcelLocker parcelLocker = parcelLockerService.findById(parcelLockerId);

        StringResponse response = new StringResponse();

        if(courier.getArea().getId().equals(parcelLocker.getStore().getId())){
            response.setMessage("eligible");
            return ResponseEntity.ok(response);
        }

        response.setMessage("notEligible");
        return ResponseEntity.ok(response);
    }

    //Összes futár lekérése
    @Override
    public ResponseEntity<List<CourierDTO>> getCouriers() {

        List<CourierDTO> response = new ArrayList<>();

        for(Courier courier : findAll()){
            CourierDTO courierDTO = new CourierDTO();

            courierDTO.setId(courier.getId());
            courierDTO.setUniqueCourierId(courier.getUniqueCourierId());
            courierDTO.setFirstName(courier.getFirstName());
            courierDTO.setLastName(courier.getLastName());
            courierDTO.setStoreId(courier.getArea().getId());
            courierDTO.setStorePostCode(courier.getArea().getAddress().getPostCode());
            courierDTO.setStoreCounty(courier.getArea().getAddress().getCounty());
            courierDTO.setStoreCity(courier.getArea().getAddress().getCity());
            courierDTO.setStoreStreet(courier.getArea().getAddress().getStreet());

            response.add(courierDTO);
        }

        return ResponseEntity.ok(response);
    }

    //Futár valamely adatának módosítása
    @Transactional
    @Override
    public ResponseEntity<StringResponse> updateCourier(UpdateCourierRequest request) {

        StringResponse response = new StringResponse();

        Courier courier = findById(request.getId());

        //Új futár azonosító
        String newUniqueCourierId = request.getUniqueCourierId();
        //Régi futár azonosító
        String previousUniqueCourierId = courier.getUniqueCourierId();

        //Nem valószínű, mert a frontenden megjelenítem a futárokat és abból választ ki az admin
        if(courier == null){
            response.setMessage("notFound");
            return ResponseEntity.ok(response);
        }

        //A megadott egyedi futár azonosító már létezik az adatbázisban
        //Azt is meg kell nézni, hogy a régi és az új futár azonosító megegyezik-e
        //Mert ha megegyezik, akkor mindig már létezik az adatbázisban hibát fog visszaküldeni
        if(!previousUniqueCourierId.equals(newUniqueCourierId) && findByUniqueCourierId(newUniqueCourierId) != null){
            response.setMessage("uidExists");
            return ResponseEntity.ok(response);
        }

        Store store = storeService.findById(request.getStoreId());


        //Parcel handler database frissítése
        courier.setUniqueCourierId(request.getUniqueCourierId());
        courier.setFirstName(request.getFirstName());
        courier.setLastName(request.getLastName());
        courier.setArea(store);

        save(courier);

        //Ha az admin a futár jelszavát (rfid azonosítóját) vagy email címét (egyedi futár azonosítót) is szeretné módosítani
        //Akkor frissíteni kell az auth database-t is
        //Kérés küldése az authentical service-nek
        //Azt, hogy üres-e a jelszó, még az authentical service-ben is ellenőrizni kell
        if(request.getPassword() != null || !newUniqueCourierId.equals(previousUniqueCourierId)){

            //Kérés objektum az authentication service-nek
            UpdateCourierRequestToAuthService requestForAuthService = new UpdateCourierRequestToAuthService();
            requestForAuthService.setNewUniqueCourierId(newUniqueCourierId);
            requestForAuthService.setPreviousUniqueCourierId(previousUniqueCourierId);

            if(request.getPassword() != null){
                requestForAuthService.setPassword(request.getPassword());
            }

            //Válasz objektum
            StringResponse responseFromAuthService;

            responseFromAuthService = webClientBuilder.build().put()
                    .uri("http://authentication-service/auth/updatecourier")
                    .body(Mono.just(requestForAuthService), UpdateCourierRequestToAuthService.class)
                    .retrieve()
                    .bodyToMono(StringResponse.class)
                    .block();


            if(responseFromAuthService.getMessage().equals("successfulUpdating")){

                response.setMessage("successfulUpdating");
                return ResponseEntity.ok(response);
            }

            //Tranzakció kezelés
            //Ha az authentication database-ben nem sikerül módosítani az adatokat,
            //akkor itt a parcel handler database-be se módosuljanak
            //Futár nem található
            if(responseFromAuthService.getMessage().equals("notFound")){

                throw new DataAccessException("notFound") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                };

            }

            //Ilyen email cím (egyedi futár azonosító) már létezik
            if(responseFromAuthService.getMessage().equals("uidExists")){

                throw new DataAccessException("uidExists") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                };


            }

            //Ilyen jelszó (rfid azonosító) már létezik
            if(responseFromAuthService.getMessage().equals("passwordExists")){

                throw new DataAccessException("passwordExists") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                };


            }

        }



        response.setMessage("successfulUpdating");
        return ResponseEntity.ok(response);
    }

    //Futár lekérése id alapján
    @Override
    public ResponseEntity<CourierDTO> findCourierById(Long courierId) {

        Courier courier = findById(courierId);
        CourierDTO courierDTO = new CourierDTO();

        if(courier != null){
            courierDTO.setUniqueCourierId(courier.getUniqueCourierId());
            courierDTO.setId(courier.getId());
            courierDTO.setLastName(courier.getLastName());
            courierDTO.setFirstName(courier.getFirstName());
            courierDTO.setStoreId(courier.getArea().getId());
            courierDTO.setStorePostCode(courier.getArea().getAddress().getPostCode());
            courierDTO.setStoreCounty(courier.getArea().getAddress().getCounty());
            courierDTO.setStoreCity(courier.getArea().getAddress().getCity());
            courierDTO.setStoreStreet(courier.getArea().getAddress().getStreet());
        }

        return ResponseEntity.ok(courierDTO);
    }
}
