package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.repository.ParcelLockerRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ParcelLockerServiceImpl implements ParcelLockerService {

    @Autowired
    private ParcelLockerRepository parcelLockerRepository;

    @Override
    public List<ParcelLocker> findAll() {
        return parcelLockerRepository.findAll();
    }

    @Override
    public ParcelLocker findById(Long id) {
        return parcelLockerRepository.findById(id).get();
    }

    @Override
    public void save(ParcelLocker parcelLocker) {
        parcelLockerRepository.save(parcelLocker);

    }

    //Csomag automaták lekérése. Ezekből lehet kiválasztani az angular alkalmazásban a feladási automatát.
    @Override
    public ResponseEntity<List<ParcelLockerDTO>> getParcelLockersForChoice() {
        List<ParcelLocker> parcelLockers = findAll();
        List<ParcelLockerDTO> parcelLockerDTOS = new ArrayList<>();

        for(ParcelLocker parcelLocker : parcelLockers){
            ParcelLockerDTO parcelLockerDTO = new ParcelLockerDTO();

            parcelLockerDTO.setId(parcelLocker.getId());
            parcelLockerDTO.setPostCode(parcelLocker.getLocation().getPostCode());
            parcelLockerDTO.setCounty(parcelLocker.getLocation().getCounty());
            parcelLockerDTO.setCity(parcelLocker.getLocation().getCity());
            parcelLockerDTO.setStreet(parcelLocker.getLocation().getStreet());
            parcelLockerDTO.setAmountOfBoxes(parcelLocker.getAmountOfBoxes());

            parcelLockerDTOS.add(parcelLockerDTO);
        }
        return ResponseEntity.ok(parcelLockerDTOS);
    }

    //Feladási automata tele van?
    @Override
    public ResponseEntity<StringResponse> isParcelLockerFull(Long id) {
        ParcelLocker parcelLocker = findById(id);
        StringResponse stringResponse = new StringResponse();

        if(parcelLocker.getParcels().size() == parcelLocker.getAmountOfBoxes()){
            stringResponse.setMessage("full");
            return ResponseEntity.ok(stringResponse);
        }
        stringResponse.setMessage("notfull");
        return ResponseEntity.ok(stringResponse);
    }

    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
    @Override
    public ResponseEntity<List<StringResponse>>areBoxesFull(Long senderParcelLockerId) {
        List<StringResponse> stringResponses = new ArrayList<>();

        stringResponses.add(checkBoxes("small", senderParcelLockerId));
        stringResponses.add(checkBoxes("medium", senderParcelLockerId));
        stringResponses.add(checkBoxes("large", senderParcelLockerId));

        return ResponseEntity.ok(stringResponses);
    }

    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
    public StringResponse checkBoxes(String size, Long senderParcelLockerId){

        ParcelLocker parcelLocker = findById(senderParcelLockerId);
        StringResponse stringResponse = new StringResponse();
        int counter = 0;

        for(Parcel parcel : parcelLocker.getParcels()){
            if(parcel.getBox().getSize().equals(size)){
                counter++;
            }
        }

        //Automata kis rekeszeinek ellenőrzése
        if(size.equals("small")){

            //Automata kis rekeszei tele vannak
            if(counter == parcelLocker.getAmountOfSmallBoxes()){
                stringResponse.setMessage("full");
                return stringResponse;
            }

            //Automata kis rekeszei nincsenek tele
            stringResponse.setMessage("notfull");
            return stringResponse;

        }
        //Automata közepes rekeszeinek ellenőrzése
        if(size.equals("medium")){

            //Automata közepes rekeszei tele vannak
            if(counter == parcelLocker.getAmountOfMediumBoxes()){
                stringResponse.setMessage("full");
                return stringResponse;
            }

            //Automata közepes rekeszei nincsenek tele
            stringResponse.setMessage("notfull");
            return stringResponse;

        }
        //Automata nagy rekeszeinek ellenőrzése
        if(size.equals("large")){

            //Automata nagy rekeszei tele vannak
            if(counter == parcelLocker.getAmountOfLargeBoxes()){
                stringResponse.setMessage("full");
                return stringResponse;
            }
        }
        //Automata nagy rekeszei nincsenek tele
        stringResponse.setMessage("notfull");
        return stringResponse;
    }
}
