package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.kafka.Producer;
import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeResponse;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.xml.stream.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private ParcelLockerServiceImpl parcelLockerService;

    @Mock
    private BoxServiceImpl boxService;

    @Mock
    private Producer producer;

    @InjectMocks
    private ParcelServiceImpl parcelService;

        //200x70x50
        Box box1 = new Box();
        Box box2 = new Box();
        Box box3 = new Box();
        Box box4 = new Box();
        Box box5 = new Box();
        Box box6 = new Box();
        Box box7 = new Box();
        Box box8 = new Box();
        Box box9 = new Box();
        Box box10 = new Box();
        //200x120x100
        Box box11 = new Box();
        Box box12 = new Box();
        Box box13 = new Box();
        Box box14 = new Box();
        Box box15 = new Box();
        Box box16 = new Box();
        Box box17 = new Box();
        Box box18 = new Box();
        Box box19 = new Box();
        Box box20 = new Box();
        //200x170x150
        Box box21 = new Box();
        Box box22 = new Box();
        Box box23 = new Box();
        Box box24 = new Box();
        Box box25 = new Box();
        Box box26 = new Box();
        Box box27 = new Box();
        Box box28 = new Box();
        Box box29 = new Box();
        Box box30 = new Box();

        List<Box> smallBoxes = new ArrayList<>();
        List<Box> mediumBoxes = new ArrayList<>();
        List<Box> largeBoxes = new ArrayList<>();


    @BeforeEach
    void setUp() {

        //200x70x50
        box1.setBoxNumber(1);
        box1.setMaxHeight(50);
        box1.setMaxLength(200);
        box1.setMaxWeight(5);
        box1.setMaxWidth(70);
        box1.setSize("small");

        box2.setBoxNumber(2);
        box2.setMaxHeight(50);
        box2.setMaxLength(200);
        box2.setMaxWeight(5);
        box2.setMaxWidth(70);
        box2.setSize("small");

        box3.setBoxNumber(3);
        box3.setMaxHeight(50);
        box3.setMaxLength(200);
        box3.setMaxWeight(5);
        box3.setMaxWidth(70);
        box3.setSize("small");

        box4.setBoxNumber(4);
        box4.setMaxHeight(50);
        box4.setMaxLength(200);
        box4.setMaxWeight(5);
        box4.setMaxWidth(70);
        box4.setSize("small");

        box5.setBoxNumber(5);
        box5.setMaxHeight(50);
        box5.setMaxLength(200);
        box5.setMaxWeight(5);
        box5.setMaxWidth(70);
        box5.setSize("small");

        box6.setBoxNumber(6);
        box6.setMaxHeight(50);
        box6.setMaxLength(200);
        box6.setMaxWeight(5);
        box6.setMaxWidth(70);
        box6.setSize("small");

        box7.setBoxNumber(7);
        box7.setMaxHeight(50);
        box7.setMaxLength(200);
        box7.setMaxWeight(5);
        box7.setMaxWidth(70);
        box7.setSize("small");

        box8.setBoxNumber(8);
        box8.setMaxHeight(50);
        box8.setMaxLength(200);
        box8.setMaxWeight(5);
        box8.setMaxWidth(70);
        box8.setSize("small");

        box9.setBoxNumber(9);
        box9.setMaxHeight(50);
        box9.setMaxLength(200);
        box9.setMaxWeight(5);
        box9.setMaxWidth(70);
        box9.setSize("small");

        box10.setBoxNumber(10);
        box10.setMaxHeight(50);
        box10.setMaxLength(200);
        box10.setMaxWeight(5);
        box10.setMaxWidth(70);
        box10.setSize("small");

        smallBoxes.add(box1);
        smallBoxes.add(box2);
        smallBoxes.add(box3);
        smallBoxes.add(box4);
        smallBoxes.add(box5);
        smallBoxes.add(box6);
        smallBoxes.add(box7);
        smallBoxes.add(box8);
        smallBoxes.add(box9);
        smallBoxes.add(box10);

        //200x120x100
        box11.setBoxNumber(11);
        box11.setMaxHeight(100);
        box11.setMaxLength(200);
        box11.setMaxWeight(5);
        box11.setMaxWidth(120);
        box11.setSize("medium");

        box12.setBoxNumber(12);
        box12.setMaxHeight(100);
        box12.setMaxLength(200);
        box12.setMaxWeight(5);
        box12.setMaxWidth(120);
        box12.setSize("medium");

        box13.setBoxNumber(13);
        box13.setMaxHeight(100);
        box13.setMaxLength(200);
        box13.setMaxWeight(5);
        box13.setMaxWidth(120);
        box13.setSize("medium");

        box14.setBoxNumber(14);
        box14.setMaxHeight(100);
        box14.setMaxLength(200);
        box14.setMaxWeight(5);
        box14.setMaxWidth(120);
        box14.setSize("medium");

        box15.setBoxNumber(15);
        box15.setMaxHeight(100);
        box15.setMaxLength(200);
        box15.setMaxWeight(5);
        box15.setMaxWidth(120);
        box15.setSize("medium");

        box16.setBoxNumber(16);
        box16.setMaxHeight(100);
        box16.setMaxLength(200);
        box16.setMaxWeight(5);
        box16.setMaxWidth(120);
        box16.setSize("medium");

        box17.setBoxNumber(17);
        box17.setMaxHeight(100);
        box17.setMaxLength(200);
        box17.setMaxWeight(5);
        box17.setMaxWidth(120);
        box17.setSize("medium");

        box18.setBoxNumber(18);
        box18.setMaxHeight(100);
        box18.setMaxLength(200);
        box18.setMaxWeight(5);
        box18.setMaxWidth(120);
        box18.setSize("medium");

        box19.setBoxNumber(19);
        box19.setMaxHeight(100);
        box19.setMaxLength(200);
        box19.setMaxWeight(5);
        box19.setMaxWidth(120);
        box19.setSize("medium");

        box20.setBoxNumber(20);
        box20.setMaxHeight(100);
        box20.setMaxLength(200);
        box20.setMaxWeight(5);
        box20.setMaxWidth(120);
        box20.setSize("medium");

        mediumBoxes.add(box11);
        mediumBoxes.add(box12);
        mediumBoxes.add(box13);
        mediumBoxes.add(box14);
        mediumBoxes.add(box15);
        mediumBoxes.add(box16);
        mediumBoxes.add(box17);
        mediumBoxes.add(box18);
        mediumBoxes.add(box19);
        mediumBoxes.add(box20);

        //200x170x150
        box21.setBoxNumber(21);
        box21.setMaxHeight(150);
        box21.setMaxLength(200);
        box21.setMaxWeight(5);
        box21.setMaxWidth(170);
        box21.setSize("large");

        box22.setBoxNumber(22);
        box22.setMaxHeight(150);
        box22.setMaxLength(200);
        box22.setMaxWeight(5);
        box22.setMaxWidth(170);
        box22.setSize("large");

        box23.setBoxNumber(23);
        box23.setMaxHeight(150);
        box23.setMaxLength(200);
        box23.setMaxWeight(5);
        box23.setMaxWidth(170);
        box23.setSize("large");

        box24.setBoxNumber(24);
        box24.setMaxHeight(150);
        box24.setMaxLength(200);
        box24.setMaxWeight(5);
        box24.setMaxWidth(170);
        box24.setSize("large");

        box25.setBoxNumber(25);
        box25.setMaxHeight(150);
        box25.setMaxLength(200);
        box25.setMaxWeight(5);
        box25.setMaxWidth(170);
        box25.setSize("large");

        box26.setBoxNumber(26);
        box26.setMaxHeight(150);
        box26.setMaxLength(200);
        box26.setMaxWeight(5);
        box26.setMaxWidth(170);
        box26.setSize("large");

        box27.setBoxNumber(27);
        box27.setMaxHeight(150);
        box27.setMaxLength(200);
        box27.setMaxWeight(5);
        box27.setMaxWidth(170);
        box27.setSize("large");

        box28.setBoxNumber(28);
        box28.setMaxHeight(150);
        box28.setMaxLength(200);
        box28.setMaxWeight(5);
        box28.setMaxWidth(170);
        box28.setSize("large");

        box29.setBoxNumber(29);
        box29.setMaxHeight(150);
        box29.setMaxLength(200);
        box29.setMaxWeight(5);
        box29.setMaxWidth(170);
        box29.setSize("large");

        box30.setBoxNumber(30);
        box30.setMaxHeight(150);
        box30.setMaxLength(200);
        box30.setMaxWeight(5);
        box30.setMaxWidth(170);
        box30.setSize("large");

        largeBoxes.add(box21);
        largeBoxes.add(box22);
        largeBoxes.add(box23);
        largeBoxes.add(box24);
        largeBoxes.add(box25);
        largeBoxes.add(box26);
        largeBoxes.add(box27);
        largeBoxes.add(box28);
        largeBoxes.add(box29);
        largeBoxes.add(box30);

    }

    @AfterEach
    void tearDown() {
    }

    //Az automatában van három kicsit csomag. Ha én feladok egy kicsit csomagot, akkor azt a negyedik rekeszbe kell tenni
    @Test
    void itShouldSendAParcelWithoutSendingCode() {

        ParcelSendingWithoutCodeRequest request = new ParcelSendingWithoutCodeRequest();


        request.setParcelSize("small");
        request.setPrice(0);
        request.setReceiverName("Nagy Kamilla");
        request.setSenderName("Tóth János");
        request.setSenderEmailAddress("jano9415@gmail.com");
        request.setReceiverEmailAddress("jano9415@gmail.com");
        request.setReceiverPhoneNumber("06309672281");
        request.setSelectedParcelLockerId(2L);

        //Feladási automata
        ParcelLocker senderParcelLocker = new ParcelLocker();
        senderParcelLocker.setId(1L);
        Address senderParcelLockerAddress = new Address();
        senderParcelLockerAddress.setPostCode(8100);
        senderParcelLockerAddress.setCounty("Veszprém");
        senderParcelLockerAddress.setCity("Várpalota");
        senderParcelLockerAddress.setStreet("Újlaky út 8");
        senderParcelLocker.setLocation(senderParcelLockerAddress);

        //Feladási automata csomagjai
        Parcel parcel1 = new Parcel();
        parcel1.setBox(box1);
        senderParcelLocker.getParcels().add(parcel1);

        Parcel parcel2 = new Parcel();
        parcel2.setBox(box2);
        senderParcelLocker.getParcels().add(parcel2);

        Parcel parcel3 = new Parcel();
        parcel3.setBox(box3);
        senderParcelLocker.getParcels().add(parcel3);


        //when
        Mockito.when(parcelLockerService.findById(1L)).thenReturn(senderParcelLocker);

        //when box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);
        //Mockito.when(boxService.findBySize("medium")).thenReturn(mediumBoxes);
        //Mockito.when(boxService.findBySize("large")).thenReturn(largeBoxes);

        //Érkezési automata
        ParcelLocker receiverParcelLocker = new ParcelLocker();
        receiverParcelLocker.setId(2L);
        Address receiverParcelLockerAddress = new Address();
        receiverParcelLockerAddress.setPostCode(8100);
        receiverParcelLockerAddress.setCounty("Somoly");
        receiverParcelLockerAddress.setCity("Marcali");
        receiverParcelLockerAddress.setStreet("Nagypincei út 15");
        receiverParcelLocker.setLocation(receiverParcelLockerAddress);

        //when
        Mockito.when(parcelLockerService.findById(2L)).thenReturn(receiverParcelLocker);

        ResponseEntity<ParcelSendingWithoutCodeResponse> response = (ResponseEntity<ParcelSendingWithoutCodeResponse>) parcelService.sendParcelWithoutCode(request, 1L);

        assertEquals("successSending", response.getBody().getMessage());
        assertEquals(4, response.getBody().getBoxNumber());
        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(parcelLockerService).findById(1L);
        Mockito.verify(parcelLockerService).findById(2L);
        Mockito.verify(boxService).findBySize("small");


    }
}