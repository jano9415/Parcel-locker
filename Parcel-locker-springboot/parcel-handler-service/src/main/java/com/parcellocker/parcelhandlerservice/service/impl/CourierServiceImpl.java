package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Courier;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.model.Store;
import com.parcellocker.parcelhandlerservice.payload.CourierDTO;
import com.parcellocker.parcelhandlerservice.payload.CreateCourierDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.payload.request.UpdateCourierRequest;
import com.parcellocker.parcelhandlerservice.repository.CourierRepository;
import com.parcellocker.parcelhandlerservice.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            courierDTO.setStorePostCode(courier.getArea().getAddress().getPostCode());
            courierDTO.setStoreCounty(courier.getArea().getAddress().getCounty());
            courierDTO.setStoreCity(courier.getArea().getAddress().getCity());
            courierDTO.setStoreStreet(courier.getArea().getAddress().getStreet());

            response.add(courierDTO);
        }

        return ResponseEntity.ok(response);
    }

    //Futár valamely adatának módosítása
    @Override
    public ResponseEntity<StringResponse> updateCourier(UpdateCourierRequest request) {

        Courier courier = findById(request.getId());



        //Ebben az esetben az admin módosítani szeretné a futár jelszavát is
        //A jelszó egyben az rfid azonosító is
        //Az auth database-t is frissíteni kell
        //Kérés küldése az authentical service-nek
        if(request.getPassword() != null){

        }

        //Keresztnév és vezetéknév frissítése
        //Az auth database-t is frissíteni kell
        //Kérés küldése az authentical service-nek
        if(!courier.getFirstName().equals(request.getFirstName()) || !courier.getLastName().equals(request.getLastName())){

        }

        return null;
    }
}
