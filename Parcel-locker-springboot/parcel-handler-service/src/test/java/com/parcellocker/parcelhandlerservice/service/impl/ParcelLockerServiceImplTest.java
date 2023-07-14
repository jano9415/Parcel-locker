package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.service.ParcelLockerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParcelLockerServiceImplTest {

    private ParcelLockerService parcelLockerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parcelLockerShouldBeFull() {

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setId(1L);
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfSmallBoxes(10);
        parcelLocker.setAmountOfMediumBoxes(10);
        parcelLocker.setAmountOfLargeBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<30; i++){
            parcels.add(new Parcel());
        }

        parcelLocker.setParcels(parcels);
    }


}