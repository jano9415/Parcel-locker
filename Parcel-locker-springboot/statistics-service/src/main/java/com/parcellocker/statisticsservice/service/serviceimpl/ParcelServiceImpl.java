package com.parcellocker.statisticsservice.service.serviceimpl;

import com.parcellocker.statisticsservice.model.Parcel;
import com.parcellocker.statisticsservice.model.ParcelLocker;
import com.parcellocker.statisticsservice.payload.request.ParcelToStaticticsServiceRequest;
import com.parcellocker.statisticsservice.payload.response.StringResponse;
import com.parcellocker.statisticsservice.repository.ParcelRepository;
import com.parcellocker.statisticsservice.service.ParcelService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ParcelServiceImpl implements ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    //Keresés id szerint
    @Override
    public Parcel findById(String id) {
        return parcelRepository.findById(id).get();
    }

    //Csomag hozzáadása az adatbázishoz
    //A csomag objektum a parcel-handler-service-ből érkezik
    @Override
    public ResponseEntity<StringResponse> addParcelToDb(ParcelToStaticticsServiceRequest request) {

        Parcel parcel = new Parcel();
        ParcelLocker senderParcelLocker = new ParcelLocker();
        ParcelLocker receiverParcelLocker = new ParcelLocker();

        parcel.setUniqueParcelId(request.getUniqueParcelId());
        parcel.setSenderEmailAddress(request.getSenderEmailAddress());
        parcel.setSenderName(parcel.getSenderName());

        //Feladási automata
        senderParcelLocker.setPostCode(request.getSenderParcelLockerPostCode());
        senderParcelLocker.setCounty(request.getSenderParcelLockerCounty());
        senderParcelLocker.setCity(request.getSenderParcelLockerCity());
        senderParcelLocker.setStreet(request.getSenderParcelLockerStreet());
        parcel.setShippingFrom(senderParcelLocker);
        //Érkezési automata
        receiverParcelLocker.setPostCode(request.getReceiverParcelLockerPostCode());
        receiverParcelLocker.setCounty(request.getReceiverParcelLockerCounty());
        receiverParcelLocker.setCity(request.getReceiverParcelLockerCity());
        receiverParcelLocker.setStreet(request.getReceiverParcelLockerStreet());
        parcel.setShippingTo(receiverParcelLocker);

        parcel.setSize(request.getSize());
        parcel.setPrice(request.getPrice());
        parcel.setReceiverName(request.getReceiverName());
        parcel.setReceiverEmailAddress(request.getReceiverEmailAddress());

        parcel.setShipped(request.isShipped());
        parcel.setPickedUp(request.isPickedUp());
        parcel.setSendingDate(LocalDate.parse(request.getSendingDate()));
        parcel.setSendingTime(LocalTime.parse(request.getSendingTime()));

        parcel.setPickingUpDate(LocalDate.parse(request.getPickingUpDate()));
        parcel.setPickingUpTime(LocalTime.parse(request.getPickingUpTime()));
        parcel.setShippingDate(LocalDate.parse(request.getShippingDate()));
        parcel.setShippingTime(LocalTime.parse(request.getShippingTime()));

        parcel.setPlaced(request.isPlaced());
        parcel.setPaid(request.isPaid());
        parcel.setPickingUpExpirationDate(LocalDate.parse(request.getPickingUpExpirationDate()));
        parcel.setPickingUpExpirationTime(LocalTime.parse(request.getPickingUpExpirationTime()));

        parcel.setPickedUp(request.isPickedUp());

        if(request.getSendingExpirationDate() != null){
            parcel.setSendingExpirationDate(LocalDate.parse(request.getSendingExpirationDate()));
            parcel.setSendingExpirationTime(LocalTime.parse(request.getSendingExpirationTime()));
        }

        StringResponse response = new StringResponse();
        response.setMessage("successAdding");

        save(parcel);

        return ResponseEntity.ok(response);
    }

    //Csomag mentése
    @Override
    public void save(Parcel parcel) {
        parcelRepository.save(parcel);
    }


}
