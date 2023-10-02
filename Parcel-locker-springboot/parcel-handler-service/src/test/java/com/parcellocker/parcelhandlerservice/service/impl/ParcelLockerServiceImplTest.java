package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.repository.ParcelLockerRepository;
import com.parcellocker.parcelhandlerservice.service.ParcelLockerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ParcelLockerServiceImplTest {


    @Mock
    private ParcelLockerRepository parcelLockerRepository;

    @InjectMocks
    private ParcelLockerServiceImpl parcelLockerService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGiveParcelLockers(){

        List<ParcelLocker> parcelLockers = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            ParcelLocker parcelLocker = new ParcelLocker();
            Address address = new Address();
            address.setPostCode(8100);
            address.setCounty("Veszprém");
            address.setCity("Veszprém");
            address.setStreet("Kossuth utca 9");

            parcelLocker.setId(1L);
            parcelLocker.setAmountOfBoxes(30);
            parcelLocker.setLocation(address);

            parcelLockers.add(parcelLocker);
        }

        //when
        Mockito.when(parcelLockerRepository.findAll()).thenReturn(parcelLockers);

        ResponseEntity<List<ParcelLockerDTO>> response = parcelLockerService.getParcelLockersForChoice();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
        Mockito.verify(parcelLockerRepository).findAll();
    }

    @Test
    @DisplayName("Parcel locker is full with parcels")
    void parcelLockerShouldBeFull() {

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<30; i++){
            parcels.add(new Parcel());
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        ResponseEntity<StringResponse> response = parcelLockerService.isParcelLockerFull(Mockito.anyLong());

        assertEquals("full", response.getBody().getMessage());
        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());
    }

    @Test
    @DisplayName("There is still empty place in the parcel locker. In this case 1 free place")
    void parcelLockerShouldNotBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<29; i++){
            parcels.add(new Parcel());
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        ResponseEntity<StringResponse> response = parcelLockerService.isParcelLockerFull(Mockito.anyLong());

        assertEquals("notfull", response.getBody().getMessage());
        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }


    @Test
    void smallBoxesOfParcelLockerShouldBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfSmallBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<10; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("small");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("small", Mockito.anyLong());

        assertEquals("full", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }

    @Test
    void smallBoxesOfParcelLockerShouldNotBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfSmallBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<9; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("small");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("small", Mockito.anyLong());

        assertEquals("notfull", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }

    @Test
    void mediumBoxesOfParcelLockerShouldBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfMediumBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<10; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("medium");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("medium", Mockito.anyLong());

        assertEquals("full", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }

    @Test
    void mediumBoxesOfParcelLockerShouldNotBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfMediumBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<9; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("medium");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("medium", Mockito.anyLong());

        assertEquals("notfull", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }

    @Test
    void largeBoxesOfParcelLockerShouldBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfLargeBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<10; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("large");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("large", Mockito.anyLong());

        assertEquals("full", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }

    @Test
    void largeBoxesOfParcelLockerShouldNotBeFull(){

        ParcelLocker parcelLocker = new ParcelLocker();
        parcelLocker.setAmountOfBoxes(30);
        parcelLocker.setAmountOfLargeBoxes(10);

        Set<Parcel> parcels = new HashSet<>();

        for(int i=0; i<9; i++){
            Parcel parcel = new Parcel();
            Box box = new Box();
            box.setSize("large");

            parcel.setBox(box);


            parcels.add(parcel);
        }
        parcelLocker.setParcels(parcels);

        //when
        Mockito.when(parcelLockerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parcelLocker));

        StringResponse response = parcelLockerService.checkBoxes("large", Mockito.anyLong());

        assertEquals("notfull", response.getMessage());
        Mockito.verify(parcelLockerRepository).findById(Mockito.anyLong());

    }
}