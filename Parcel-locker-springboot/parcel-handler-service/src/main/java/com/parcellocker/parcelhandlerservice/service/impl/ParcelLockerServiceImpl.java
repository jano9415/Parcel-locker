package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.repository.ParcelLockerRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    //Csomag küldése feladási kód nélkül
    @Override
    public ResponseEntity<String> sendParcelWithoutCode(ParcelSendingWithoutCodeRequest request) {
        System.out.println(request);
        return ResponseEntity.ok("ok");
    }

    //Feladási automata tele van?
    @Override
    public ResponseEntity<String> isParcelLockerFull(Long id) {
        return null;
    }

}
