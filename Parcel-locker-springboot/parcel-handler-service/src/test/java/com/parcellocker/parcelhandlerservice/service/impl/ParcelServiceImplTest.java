package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.kafka.Producer;
import com.parcellocker.parcelhandlerservice.model.*;
import com.parcellocker.parcelhandlerservice.payload.GetParcelsForShippingResponse;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeRequest;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingWithoutCodeResponse;
import com.parcellocker.parcelhandlerservice.payload.request.EmptyParcelLockerRequest;
import com.parcellocker.parcelhandlerservice.payload.response.EmptyParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.FillParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.GetParcelsForParcelLockerResponse;
import com.parcellocker.parcelhandlerservice.payload.response.PickUpParcelResponse;
import com.parcellocker.parcelhandlerservice.repository.ParcelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private CourierServiceImpl courierService;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    //Feladási automata
    private ParcelLocker parcelLocker1;
    private Address senderParcelLockerAddress;

    //Érkezési automata
    private ParcelLocker parcelLocker2;
    private Address receiverParcelLockerAddress;

    private ParcelLocker parcelLocker3;
    private Address parcelLockerAddress3;

    //Futár
    private Courier courier1;

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

        //Feladási automata
        parcelLocker1 = new ParcelLocker();
        senderParcelLockerAddress = new Address();
        parcelLocker1.setId(1L);
        senderParcelLockerAddress.setPostCode(8100);
        senderParcelLockerAddress.setCounty("Veszprém");
        senderParcelLockerAddress.setCity("Várpalota");
        senderParcelLockerAddress.setStreet("Újlaky út 8");
        parcelLocker1.setLocation(senderParcelLockerAddress);

        //Érkezési automata
        parcelLocker2 = new ParcelLocker();
        receiverParcelLockerAddress = new Address();
        parcelLocker2.setId(2L);
        receiverParcelLockerAddress.setPostCode(8100);
        receiverParcelLockerAddress.setCounty("Somogy");
        receiverParcelLockerAddress.setCity("Marcali");
        receiverParcelLockerAddress.setStreet("Nagypincei út 15");
        parcelLocker2.setLocation(receiverParcelLockerAddress);

        //Automata 3
        parcelLocker3 = new ParcelLocker();
        parcelLockerAddress3 = new Address();
        parcelLocker3.setId(3L);
        parcelLockerAddress3.setPostCode(8000);
        parcelLockerAddress3.setCounty("Fejér");
        parcelLockerAddress3.setCity("Székesfehérvár");
        parcelLockerAddress3.setStreet("Úrhidai út 6");
        parcelLocker3.setLocation(parcelLockerAddress3);

        //Futár
        courier1 = new Courier();
        courier1.setId(1L);
        courier1.setUniqueCourierId("futar001");
        courier1.setLastName("Nagy");
        courier1.setFirstName("Balázs");

    }

    @AfterEach
    void tearDown() {
    }

    //Az automatában van három kicsit csomag. Ha én feladok egy kicsit csomagot, akkor azt a negyedik rekeszbe kell tenni
    @Test
    @Description("sendParcelWithoutCode function")
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

    //Az automatában négy csomag van
    //Kettőt el kell onnan szállítani az érkezési automatába
    //Egy csomag már megérkezett ide, át lehet venni
    //Egy csomag már megérkezett ide, de lejárt az átvételi ideje
    //Tehát a futárnak három darab csomagot kell onnan elszállítania, de ez a függvény még csak lekéri a csomagokat
    //Három darab csomag a visszatérési érték
    @Test
    @Description("getParcelsForShipping function")
    void itShouldGetThreeParcelsThatAreReadyForDelivery(){

        ResponseEntity<List<GetParcelsForShippingResponse>> response;

        //Csomagok, amik az automatában vannak és készen állnak az elszállításra
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("midj34");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker1);
        parcel1.setShippingTo(parcelLocker2);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("lo97gf");
        parcel2.setPrice(16800);
        parcel2.setShippingFrom(parcelLocker1);
        parcel2.setShippingTo(parcelLocker2);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setBox(box2);

        //Csomag aminek lejárt az átvételi ideje
        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("loj73n");
        parcel3.setPrice(4600);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(true);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        LocalDate pickingUpExpirationDate3 = LocalDate.of(2023,10,12);
        LocalTime pickingUpExpirationTime3 = LocalTime.of(15,41);
        parcel3.setPickingUpExpirationDate(pickingUpExpirationDate3);
        parcel3.setPickingUpExpirationTime(pickingUpExpirationTime3);
        parcel3.setBox(box3);

        //Csomag ami már le lett szállítva, de még nem vették át
        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("mj73le");
        parcel4.setPrice(0);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(true);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        LocalDate pickingUpExpirationDate4 = LocalDate.of(2023,10,15);
        LocalTime pickingUpExpirationTime4 = LocalTime.of(13,10);
        parcel4.setPickingUpExpirationDate(pickingUpExpirationDate4);
        parcel4.setPickingUpExpirationTime(pickingUpExpirationTime4);
        parcel4.setBox(box4);

        //Automata csomagjai
        parcelLocker1.getParcels().add(parcel1);
        parcelLocker1.getParcels().add(parcel2);
        parcelLocker1.getParcels().add(parcel3);
        parcelLocker1.getParcels().add(parcel4);

        //when parcelLocker
        Mockito.when(parcelLockerService.findById(1L)).thenReturn(parcelLocker1);

        response = parcelService.getParcelsForShipping(1L);

        boolean containsParcel1 = false;
        boolean containsParcel2 = false;
        boolean containsParcel3 = false;

        for(GetParcelsForShippingResponse parcel : response.getBody()){
            if(parcel.getUniqueParcelId().equals("midj34")){
                containsParcel1 = true;
            }
            if(parcel.getUniqueParcelId().equals("lo97gf")){
                containsParcel2 = true;
            }
            if(parcel.getUniqueParcelId().equals("loj73n")){
                containsParcel3 = true;
            }
        }

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
        assertTrue(containsParcel1);
        assertTrue(containsParcel2);
        assertTrue(containsParcel3);
        Mockito.verify(parcelLockerService).findById(1L);

    }


    //Az automatában négy csomag van
    //Kettőt el kell onnan szállítani az érkezési automatába
    //Egy csomag már megérkezett ide, át lehet venni
    //Egy csomag már megérkezett ide, de lejárt az átvételi ideje
    //Tehát a futárnak három darab csomagot kell onnan elszállítania
    //Ezeket a csomagokat már el is szállítja, nem csak lekéri azokat
    //Az alábbi rekeszek nyílnak ki - 1,2,3
    @Test
    @Description("emptyParcelLocker function")
    void itShouldEmptyTheParcelLocker(){

        ResponseEntity<List<EmptyParcelLockerResponse>> response;

        EmptyParcelLockerRequest request = new EmptyParcelLockerRequest();
        request.setParcelLockerId(1L);
        request.setUniqueCourierId(courier1.getUniqueCourierId());

        //Csomagok, amik az automatában vannak és készen állnak az elszállításra
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("midj34");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker1);
        parcel1.setShippingTo(parcelLocker2);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("lo97gf");
        parcel2.setPrice(16800);
        parcel2.setShippingFrom(parcelLocker1);
        parcel2.setShippingTo(parcelLocker2);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setBox(box2);

        //Csomag aminek lejárt az átvételi ideje
        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("loj73n");
        parcel3.setPrice(4600);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(true);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        LocalDate pickingUpExpirationDate3 = LocalDate.of(2023,10,12);
        LocalTime pickingUpExpirationTime3 = LocalTime.of(15,41);
        parcel3.setPickingUpExpirationDate(pickingUpExpirationDate3);
        parcel3.setPickingUpExpirationTime(pickingUpExpirationTime3);
        parcel3.setBox(box3);

        //Csomag ami már le lett szállítva, de még nem vették át
        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("mj73le");
        parcel4.setPrice(0);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(true);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        LocalDate pickingUpExpirationDate4 = LocalDate.of(2023,10,20);
        LocalTime pickingUpExpirationTime4 = LocalTime.of(13,10);
        parcel4.setPickingUpExpirationDate(pickingUpExpirationDate4);
        parcel4.setPickingUpExpirationTime(pickingUpExpirationTime4);
        parcel4.setBox(box4);

        //Automata csomagjai
        parcelLocker1.getParcels().add(parcel1);
        parcelLocker1.getParcels().add(parcel2);
        parcelLocker1.getParcels().add(parcel3);
        parcelLocker1.getParcels().add(parcel4);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when parcelLocker
        Mockito.when(parcelLockerService.findById(1L)).thenReturn(parcelLocker1);



        response = parcelService.emptyParcelLocker(request);


        boolean containsParcel1 = false;
        boolean containsParcel2 = false;
        boolean containsParcel3 = false;


        //Futárhoz átkerült csomagok
        for(Parcel parcel : courier1.getParcels()){
            if(parcel.getUniqueParcelId().equals("midj34")){
                containsParcel1 = true;
            }
            if(parcel.getUniqueParcelId().equals("lo97gf")){
                containsParcel2 = true;
            }
            if(parcel.getUniqueParcelId().equals("loj73n")){
                containsParcel3 = true;
            }
        }


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
        assertEquals(3, courier1.getParcels().size());
        //Futárnál lévő három csomag
        assertTrue(containsParcel1);
        assertTrue(containsParcel2);
        assertTrue(containsParcel3);
        //Csomagoknak nincs rekesze
        assertEquals(null, parcel1.getBox());
        assertEquals(null, parcel2.getBox());
        assertEquals(null, parcel3.getBox());
        //Csomagoknak nincs csomag automatája
        assertEquals(null, parcel1.getParcelLocker());
        assertEquals(null, parcel2.getParcelLocker());
        assertEquals(null, parcel3.getParcelLocker());
        //Csomagnak van futárja
        assertEquals(courier1, parcel1.getCourier());
        assertEquals(courier1, parcel2.getCourier());
        assertEquals(courier1, parcel3.getCourier());
        //Csomagnak van dátuma, amikor a futár kiveszi az automatából
        assertNotEquals(null, parcel1.getPickingUpDateFromParcelLockerByCourier());
        assertNotEquals(null, parcel1.getPickingUpTimeFromParcelLockerByCourier());
        assertNotEquals(null, parcel2.getPickingUpDateFromParcelLockerByCourier());
        assertNotEquals(null, parcel2.getPickingUpTimeFromParcelLockerByCourier());
        assertNotEquals(null, parcel3.getPickingUpDateFromParcelLockerByCourier());
        assertNotEquals(null, parcel3.getPickingUpTimeFromParcelLockerByCourier());

        Mockito.verify(parcelLockerService).findById(1L);
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());

    }

    //A futárnál van 2 darab csomag az automatához, amit fel szeretne tölteni
    //Mind a kettőnek van szabad hely
    //Ez a függvény még nem helyezi el a csomagokat az automatában, csak lekéri azokat
    @Test
    @Description("getParcelsForParcelLocker function")
    void itShouldGetTwoParcelsForTheParcelLocker(){

        ResponseEntity<List<GetParcelsForParcelLockerResponse>> response;

        //Az automatában három darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);

        //Futárnál van kettő csomag
        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(0);
        parcel4.setShippingFrom(parcelLocker1);
        parcel4.setShippingTo(parcelLocker2);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(0);
        parcel5.setShippingFrom(parcelLocker1);
        parcel5.setShippingTo(parcelLocker2);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");

        courier1.getParcels().add(parcel4);
        courier1.getParcels().add(parcel5);


        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.getParcelsForParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Mind a kettő csomagnak van hely az automatában
        assertEquals(2, response.getBody().size());

        //A futárnál még megmarad a két csomag
        assertEquals(2, courier1.getParcels().size());

        //A négyes és ötös rekeszbe lehet betenni a csomagokat
        assertEquals(4, response.getBody().get(0).getBoxNumber());
        assertEquals(5, response.getBody().get(1).getBoxNumber());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 2 darab kicsi csomag az automatához, amit fel szeretne tölteni
    //A 10 darab kicsit rekesz tele van, tehát mindegyik tele van
    //Ez a függvény még nem helyezi el a csomagokat az automatában, csak lekéri azokat
    @Test
    @Description("getParcelsForParcelLocker function")
    void itShouldGetZeroParcelForTheParcelLocker(){

        ResponseEntity<List<GetParcelsForParcelLockerResponse>> response;

        //Az automatában tíz darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        Parcel parcel10 = new Parcel();
        parcel10.setUniqueParcelId("aaa10");
        parcel10.setPrice(4820);
        parcel10.setShippingFrom(parcelLocker2);
        parcel10.setShippingTo(parcelLocker1);
        parcel10.setShipped(false);
        parcel10.setPlaced(true);
        parcel10.setPickedUp(false);
        parcel10.setSize("small");
        parcel10.setBox(box10);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);
        parcelLocker2.getParcels().add(parcel10);

        //Futárnál van kettő csomag
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        courier1.getParcels().add(parcel11);
        courier1.getParcels().add(parcel12);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.getParcelsForParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Egyik csomagnak sincs hely
        assertEquals(response.getBody().size(), 0);

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 3 darab kicsi csomag, kettő az automatához, amit fel szeretne tölteni
    //9 darab kicsit rekesz tele van, tehát csak egy csomagot tud elhelyezni majd az automatában
    //Ez a függvény még nem helyezi el a csomagokat az automatában, csak lekéri azokat
    @Test
    @Description("getParcelsForParcelLocker function")
    void itShouldGetJustOneParcelForTheParcelLocker(){

        ResponseEntity<List<GetParcelsForParcelLockerResponse>> response;

        //Az automatában kilenc darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);

        //Futárnál van kettő csomag
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        Parcel parcel13 = new Parcel();
        parcel13.setUniqueParcelId("aaa13");
        parcel13.setPrice(0);
        parcel13.setShippingFrom(parcelLocker1);
        parcel13.setShippingTo(parcelLocker3);
        parcel13.setShipped(false);
        parcel13.setPlaced(true);
        parcel13.setPickedUp(false);
        parcel13.setSize("medium");

        courier1.getParcels().add(parcel11);
        courier1.getParcels().add(parcel12);
        courier1.getParcels().add(parcel13);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when small box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.getParcelsForParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Csak egy csomagnak van hely
        assertEquals(response.getBody().size(), 1);
        //Ezt a csomagot a 10. rekeszbe tudja majd betenni
        assertEquals(10, response.getBody().get(0).getBoxNumber());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 2 darab kicsi csomag ehhez az automatához, egy közepes ehhez az automatához, és egy közepes egy másik automatához
    //9 darab kicsit rekesz tele van, tehát csak egy kicsi csomagot tud elhelyezni majd az automatában, és azt az egy közepes csomagot
    //Ez a függvény még nem helyezi el a csomagokat az automatában, csak lekéri azokat
    @Test
    @Description("getParcelsForParcelLocker function")
    void itShouldGetTwoSmallParcelsAndOneMediumForTheParcelLockerToFill(){

        ResponseEntity<List<GetParcelsForParcelLockerResponse>> response;

        //Az automatában kilenc darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);

        //Futárnál van kettő kicsi csomag
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        //Futárnál van kettő darab közepes csomag, egy az automatához
        Parcel parcel13 = new Parcel();
        parcel13.setUniqueParcelId("aaa13");
        parcel13.setPrice(0);
        parcel13.setShippingFrom(parcelLocker1);
        parcel13.setShippingTo(parcelLocker2);
        parcel13.setShipped(false);
        parcel13.setPlaced(true);
        parcel13.setPickedUp(false);
        parcel13.setSize("medium");

        Parcel parcel14 = new Parcel();
        parcel14.setUniqueParcelId("aaa14");
        parcel14.setPrice(0);
        parcel14.setShippingFrom(parcelLocker1);
        parcel14.setShippingTo(parcelLocker3);
        parcel14.setShipped(false);
        parcel14.setPlaced(true);
        parcel14.setPickedUp(false);
        parcel14.setSize("medium");

        courier1.getParcels().add(parcel11);
        courier1.getParcels().add(parcel12);
        courier1.getParcels().add(parcel13);
        courier1.getParcels().add(parcel14);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when small box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        //when medium box
        Mockito.when(boxService.findBySize("medium")).thenReturn(mediumBoxes);

        response = parcelService.getParcelsForParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Egy kicsi és egy közepes csomagnak van hely
        assertEquals(response.getBody().size(), 2);
        //A kicsi csomagot a 10. rekeszbe tudja majd betenni
        assertEquals(10, response.getBody().get(0).getBoxNumber());
        //Ez a 'aaa11' azonosítójú csomag
        assertEquals("aaa11", response.getBody().get(0).getUniqueParcelId());
        //A közepes csomagot a 11. rekeszbe tudja betenni
        assertEquals(11, response.getBody().get(1).getBoxNumber());
        //Ez a 'aaa13' azonosítójú csomag
        assertEquals("aaa13", response.getBody().get(1).getUniqueParcelId());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");
        Mockito.verify(boxService).findBySize("medium");

    }

    //A futárnál van 2 darab csomag az automatához, amit fel szeretne tölteni
    //Mind a kettőnek van szabad hely
    //Csomagok elhelyezése az automatában
    @Test
    @Description("fillParcelLocker function")
    void courierShouldFillTheParcelLockerWithTwoSmallParcels(){

        ResponseEntity<List<FillParcelLockerResponse>> response;

        //Az automatában három darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);

        //Futárnál van kettő csomag
        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(0);
        parcel4.setShippingFrom(parcelLocker1);
        parcel4.setShippingTo(parcelLocker2);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(0);
        parcel5.setShippingFrom(parcelLocker1);
        parcel5.setShippingTo(parcelLocker2);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");

        courier1.getParcels().add(parcel4);
        parcel4.setCourier(courier1);
        courier1.getParcels().add(parcel5);
        parcel5.setCourier(courier1);


        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.fillParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Mind a kettő csomagnak van hely az automatában
        assertEquals(2, response.getBody().size());

        //'aaa004' azonosítójú csomag
        //El van helyezve
        assertTrue(parcel4.isShipped());
        //Van elhelyezési dátum és időpont
        assertNotEquals(null, parcel4.getShippingDate());
        assertNotEquals(null, parcel4.getShippingTime());
        //Van átvételi lejárati dátum és időpont
        assertNotEquals(null, parcel4.getPickingUpExpirationDate());
        assertNotEquals(null, parcel4.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés ellenőrzése
        assertEquals(parcelLocker2, parcel4.getParcelLocker());
        assertTrue(parcelLocker2.getParcels().contains(parcel4));
        //Csomag és futár összerendelés megszüntetése, ellenőrzés
        assertEquals(null, parcel4.getCourier());
        //Csomag és rekesz kapcsolat ellenőrzése
        assertNotEquals(null, parcel4.getBox());

        //'aaa005' azonosítójú csomag
        //El van helyezve
        assertTrue(parcel5.isShipped());
        //Van elhelyezési dátum és időpont
        assertNotEquals(null, parcel5.getShippingDate());
        assertNotEquals(null, parcel5.getShippingTime());
        //Van átvételi lejárati dátum és időpont
        assertNotEquals(null, parcel5.getPickingUpExpirationDate());
        assertNotEquals(null, parcel5.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés ellenőrzése
        assertEquals(parcelLocker2, parcel5.getParcelLocker());
        assertTrue(parcelLocker2.getParcels().contains(parcel5));
        //Csomag és futár összerendelés megszüntetése, ellenőrzés
        assertEquals(null, parcel5.getCourier());
        //Csomag és rekesz kapcsolat ellenőrzése
        assertNotEquals(null, parcel5.getBox());



        //A négyes és ötös rekeszbe lehet betenni a csomagokat
        assertEquals(4, response.getBody().get(0).getBoxNumber());
        assertEquals(5, response.getBody().get(1).getBoxNumber());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 2 darab kicsi csomag az automatához, amit fel szeretne tölteni
    //A 10 darab kicsit rekesz tele van, tehát mindegyik tele van
    //Egy csomagot se tud elhelyezni az automatában
    @Test
    @Description("fillParcelLocker function")
    void courierShouldNotFillTheParcelLockerButHeHasTwoSmallParcels(){

        ResponseEntity<List<FillParcelLockerResponse>> response;

        //Az automatában tíz darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        Parcel parcel10 = new Parcel();
        parcel10.setUniqueParcelId("aaa10");
        parcel10.setPrice(4820);
        parcel10.setShippingFrom(parcelLocker2);
        parcel10.setShippingTo(parcelLocker1);
        parcel10.setShipped(false);
        parcel10.setPlaced(true);
        parcel10.setPickedUp(false);
        parcel10.setSize("small");
        parcel10.setBox(box10);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);
        parcelLocker2.getParcels().add(parcel10);

        //Futárnál van kettő csomag
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        courier1.getParcels().add(parcel11);
        parcel11.setCourier(courier1);
        courier1.getParcels().add(parcel12);
        parcel12.setCourier(courier1);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.fillParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Egyik csomagnak sincs hely
        assertEquals(response.getBody().size(), 0);

        //'aaa11' azonosítójú csomag
        //Nincs elhelyezve
        assertFalse(parcel11.isShipped());
        //Továbbra sincs elhelyezési dátum és időpont
        assertEquals(null, parcel11.getShippingDate());
        assertEquals(null, parcel11.getShippingTime());
        //Továbbra sincs átvételi lejárati dátum és időpont
        assertEquals(null, parcel11.getPickingUpExpirationDate());
        assertEquals(null, parcel11.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés nem jön létre
        assertNotEquals(parcelLocker2, parcel11.getParcelLocker());
        assertFalse(parcelLocker2.getParcels().contains(parcel11));
        //Csomag és futár összerendelés megmarad
        assertNotEquals(null, parcel11.getCourier());
        //Csomag és rekesz kapcsolat nem jön létre
        assertEquals(null, parcel11.getBox());

        //'aaa12' azonosítójú csomag
        //Nincs elhelyezve
        assertFalse(parcel12.isShipped());
        //Továbbra sincs elhelyezési dátum és időpont
        assertEquals(null, parcel12.getShippingDate());
        assertEquals(null, parcel12.getShippingTime());
        //Továbbra sincs átvételi lejárati dátum és időpont
        assertEquals(null, parcel12.getPickingUpExpirationDate());
        assertEquals(null, parcel12.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés nem jön létre
        assertNotEquals(parcelLocker2, parcel12.getParcelLocker());
        assertFalse(parcelLocker2.getParcels().contains(parcel12));
        //Csomag és futár összerendelés megmarad
        assertNotEquals(null, parcel12.getCourier());
        //Csomag és rekesz kapcsolat nem jön létre
        assertEquals(null, parcel12.getBox());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 3 darab kicsi csomag, kettő az automatához, amit fel szeretne tölteni
    //9 darab kicsit rekesz tele van, tehát csak egy csomagot tud elhelyezni az automatában
    @Test
    @Description("fillParcelLocker function")
    void courierShouldFillTheParcelLockerWithOneParcelButHeHasTwo(){

        ResponseEntity<List<FillParcelLockerResponse>> response;

        //Az automatában kilenc darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);

        //Futárnál van kettő csomag ehhez az automatához
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        //Ez egy másik automatához van
        Parcel parcel13 = new Parcel();
        parcel13.setUniqueParcelId("aaa13");
        parcel13.setPrice(0);
        parcel13.setShippingFrom(parcelLocker1);
        parcel13.setShippingTo(parcelLocker3);
        parcel13.setShipped(false);
        parcel13.setPlaced(true);
        parcel13.setPickedUp(false);
        parcel13.setSize("medium");

        courier1.getParcels().add(parcel11);
        parcel11.setCourier(courier1);
        courier1.getParcels().add(parcel12);
        parcel12.setCourier(courier1);
        courier1.getParcels().add(parcel13);
        parcel13.setCourier(courier1);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when small box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        response = parcelService.fillParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Csak egy csomagnak van hely
        assertEquals(response.getBody().size(), 1);
        //Ezt a csomagot a 10. rekeszbe tudja majd betenni
        assertEquals(10, response.getBody().get(0).getBoxNumber());

        //'aaa11' azonosítójú csomag
        //Ha ezt a csomagot helyezi be az automatába
        if(parcel11.getParcelLocker() != null){
            //El van helyezve
            assertTrue(parcel11.isShipped());
            //Van elhelyezési dátum és időpont
            assertNotEquals(null, parcel11.getShippingDate());
            assertNotEquals(null, parcel11.getShippingTime());
            //Van átvételi lejárati dátum és időpont
            assertNotEquals(null, parcel11.getPickingUpExpirationDate());
            assertNotEquals(null, parcel11.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés ellenőrzése
            assertEquals(parcelLocker2, parcel11.getParcelLocker());
            assertTrue(parcelLocker2.getParcels().contains(parcel11));
            //Csomag és futár összerendelés megszüntetése, ellenőrzés
            assertEquals(null, parcel11.getCourier());
            //Csomag és rekesz kapcsolat ellenőrzése
            assertNotEquals(null, parcel11.getBox());
        }
        else{
            //Nincs elhelyezve
            assertFalse(parcel11.isShipped());
            //Továbbra sincs elhelyezési dátum és időpont
            assertEquals(null, parcel11.getShippingDate());
            assertEquals(null, parcel11.getShippingTime());
            //Továbbra sincs átvételi lejárati dátum és időpont
            assertEquals(null, parcel11.getPickingUpExpirationDate());
            assertEquals(null, parcel11.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés nem jön létre
            assertNotEquals(parcelLocker2, parcel11.getParcelLocker());
            assertFalse(parcelLocker2.getParcels().contains(parcel11));
            //Csomag és futár összerendelés megmarad
            assertNotEquals(null, parcel11.getCourier());
            //Csomag és rekesz kapcsolat nem jön létre
            assertEquals(null, parcel11.getBox());
        }


        //'aaa12' azonosítójú csomag
        //Ha ezt a csomagot helyezi be az automatába
        if(parcel12.getParcelLocker() != null){
            //El van helyezve
            assertTrue(parcel12.isShipped());
            //Van elhelyezési dátum és időpont
            assertNotEquals(null, parcel12.getShippingDate());
            assertNotEquals(null, parcel12.getShippingTime());
            //Van átvételi lejárati dátum és időpont
            assertNotEquals(null, parcel12.getPickingUpExpirationDate());
            assertNotEquals(null, parcel12.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés ellenőrzése
            assertEquals(parcelLocker2, parcel12.getParcelLocker());
            assertTrue(parcelLocker2.getParcels().contains(parcel12));
            //Csomag és futár összerendelés megszüntetése, ellenőrzés
            assertEquals(null, parcel12.getCourier());
            //Csomag és rekesz kapcsolat ellenőrzése
            assertNotEquals(null, parcel12.getBox());
        }
        else{
            //Nincs elhelyezve
            assertFalse(parcel12.isShipped());
            //Továbbra sincs elhelyezési dátum és időpont
            assertEquals(null, parcel12.getShippingDate());
            assertEquals(null, parcel12.getShippingTime());
            //Továbbra sincs átvételi lejárati dátum és időpont
            assertEquals(null, parcel12.getPickingUpExpirationDate());
            assertEquals(null, parcel12.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés nem jön létre
            assertNotEquals(parcelLocker2, parcel12.getParcelLocker());
            assertFalse(parcelLocker2.getParcels().contains(parcel12));
            //Csomag és futár összerendelés megmarad
            assertNotEquals(null, parcel12.getCourier());
            //Csomag és rekesz kapcsolat nem jön létre
            assertEquals(null, parcel12.getBox());
        }

        //'aaa13' azonosítójú csomag. Ez nem ehhez az automatához van
        //Nincs elhelyezve
        assertFalse(parcel13.isShipped());
        //Továbbra sincs elhelyezési dátum és időpont
        assertEquals(null, parcel13.getShippingDate());
        assertEquals(null, parcel13.getShippingTime());
        //Továbbra sincs átvételi lejárati dátum és időpont
        assertEquals(null, parcel13.getPickingUpExpirationDate());
        assertEquals(null, parcel13.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés nem jön létre
        assertNotEquals(parcelLocker2, parcel13.getParcelLocker());
        assertFalse(parcelLocker2.getParcels().contains(parcel13));
        //Csomag és futár összerendelés megmarad
        assertNotEquals(null, parcel13.getCourier());
        //Csomag és rekesz kapcsolat nem jön létre
        assertEquals(null, parcel13.getBox());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");

    }

    //A futárnál van 2 darab kicsi csomag ehhez az automatához, egy közepes ehhez az automatához, és egy közepes egy másik automatához
    //9 darab kicsit rekesz tele van, tehát csak egy kicsi csomagot tud elhelyezni majd az automatában, és azt az egy közepes csomagot
    @Test
    @Description("fillParcelLocker function")
    void courierShouldFillTheParcelLockerWithOneSmallAndOneMediumParcels(){

        ResponseEntity<List<FillParcelLockerResponse>> response;

        //Az automatában kilenc darab csomag van
        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setShipped(false);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        Parcel parcel2 = new Parcel();
        parcel2.setUniqueParcelId("aaa2");
        parcel2.setPrice(0);
        parcel2.setShippingFrom(parcelLocker2);
        parcel2.setShippingTo(parcelLocker1);
        parcel2.setShipped(false);
        parcel2.setPlaced(true);
        parcel2.setPickedUp(false);
        parcel2.setSize("small");
        parcel2.setBox(box2);

        Parcel parcel3 = new Parcel();
        parcel3.setUniqueParcelId("aaa3");
        parcel3.setPrice(0);
        parcel3.setShippingFrom(parcelLocker2);
        parcel3.setShippingTo(parcelLocker1);
        parcel3.setShipped(false);
        parcel3.setPlaced(true);
        parcel3.setPickedUp(false);
        parcel3.setSize("small");
        parcel3.setBox(box3);

        Parcel parcel4 = new Parcel();
        parcel4.setUniqueParcelId("aaa4");
        parcel4.setPrice(3600);
        parcel4.setShippingFrom(parcelLocker2);
        parcel4.setShippingTo(parcelLocker1);
        parcel4.setShipped(false);
        parcel4.setPlaced(true);
        parcel4.setPickedUp(false);
        parcel4.setSize("small");
        parcel4.setBox(box4);

        Parcel parcel5 = new Parcel();
        parcel5.setUniqueParcelId("aaa5");
        parcel5.setPrice(15000);
        parcel5.setShippingFrom(parcelLocker2);
        parcel5.setShippingTo(parcelLocker1);
        parcel5.setShipped(false);
        parcel5.setPlaced(true);
        parcel5.setPickedUp(false);
        parcel5.setSize("small");
        parcel5.setBox(box5);

        Parcel parcel6 = new Parcel();
        parcel6.setUniqueParcelId("aaa6");
        parcel6.setPrice(12000);
        parcel6.setShippingFrom(parcelLocker2);
        parcel6.setShippingTo(parcelLocker1);
        parcel6.setShipped(false);
        parcel6.setPlaced(true);
        parcel6.setPickedUp(false);
        parcel6.setSize("small");
        parcel6.setBox(box6);

        Parcel parcel7 = new Parcel();
        parcel7.setUniqueParcelId("aaa7");
        parcel7.setPrice(0);
        parcel7.setShippingFrom(parcelLocker2);
        parcel7.setShippingTo(parcelLocker1);
        parcel7.setShipped(false);
        parcel7.setPlaced(true);
        parcel7.setPickedUp(false);
        parcel7.setSize("small");
        parcel7.setBox(box7);

        Parcel parcel8 = new Parcel();
        parcel8.setUniqueParcelId("aaa8");
        parcel8.setPrice(9800);
        parcel8.setShippingFrom(parcelLocker2);
        parcel8.setShippingTo(parcelLocker1);
        parcel8.setShipped(false);
        parcel8.setPlaced(true);
        parcel8.setPickedUp(false);
        parcel8.setSize("small");
        parcel8.setBox(box8);

        Parcel parcel9 = new Parcel();
        parcel9.setUniqueParcelId("aaa9");
        parcel9.setPrice(0);
        parcel9.setShippingFrom(parcelLocker2);
        parcel9.setShippingTo(parcelLocker1);
        parcel9.setShipped(false);
        parcel9.setPlaced(true);
        parcel9.setPickedUp(false);
        parcel9.setSize("small");
        parcel9.setBox(box9);

        parcelLocker2.getParcels().add(parcel1);
        parcelLocker2.getParcels().add(parcel2);
        parcelLocker2.getParcels().add(parcel3);
        parcelLocker2.getParcels().add(parcel4);
        parcelLocker2.getParcels().add(parcel5);
        parcelLocker2.getParcels().add(parcel6);
        parcelLocker2.getParcels().add(parcel7);
        parcelLocker2.getParcels().add(parcel8);
        parcelLocker2.getParcels().add(parcel9);

        //Futárnál van kettő kicsi csomag
        Parcel parcel11 = new Parcel();
        parcel11.setUniqueParcelId("aaa11");
        parcel11.setPrice(0);
        parcel11.setShippingFrom(parcelLocker1);
        parcel11.setShippingTo(parcelLocker2);
        parcel11.setShipped(false);
        parcel11.setPlaced(true);
        parcel11.setPickedUp(false);
        parcel11.setSize("small");

        Parcel parcel12 = new Parcel();
        parcel12.setUniqueParcelId("aaa12");
        parcel12.setPrice(0);
        parcel12.setShippingFrom(parcelLocker1);
        parcel12.setShippingTo(parcelLocker2);
        parcel12.setShipped(false);
        parcel12.setPlaced(true);
        parcel12.setPickedUp(false);
        parcel12.setSize("small");

        //Futárnál van kettő darab közepes csomag, egy az automatához
        Parcel parcel13 = new Parcel();
        parcel13.setUniqueParcelId("aaa13");
        parcel13.setPrice(0);
        parcel13.setShippingFrom(parcelLocker1);
        parcel13.setShippingTo(parcelLocker2);
        parcel13.setShipped(false);
        parcel13.setPlaced(true);
        parcel13.setPickedUp(false);
        parcel13.setSize("medium");

        Parcel parcel14 = new Parcel();
        parcel14.setUniqueParcelId("aaa14");
        parcel14.setPrice(0);
        parcel14.setShippingFrom(parcelLocker1);
        parcel14.setShippingTo(parcelLocker3);
        parcel14.setShipped(false);
        parcel14.setPlaced(true);
        parcel14.setPickedUp(false);
        parcel14.setSize("medium");

        courier1.getParcels().add(parcel11);
        parcel11.setCourier(courier1);
        courier1.getParcels().add(parcel12);
        parcel12.setCourier(courier1);
        courier1.getParcels().add(parcel13);
        parcel13.setCourier(courier1);
        courier1.getParcels().add(parcel14);
        parcel14.setCourier(courier1);

        //when parcel locker
        Mockito.when(parcelLockerService.findById(Mockito.anyLong())).thenReturn(parcelLocker2);

        //when courier
        Mockito.when(courierService.findByUniqueCourierId(Mockito.anyString())).thenReturn(courier1);

        //when small box
        Mockito.when(boxService.findBySize("small")).thenReturn(smallBoxes);

        //when medium box
        Mockito.when(boxService.findBySize("medium")).thenReturn(mediumBoxes);

        response = parcelService.fillParcelLocker(2L, courier1.getUniqueCourierId());

        assertEquals(200, response.getStatusCodeValue());
        //Kettő csomagnak van hely
        assertEquals(response.getBody().size(), 2);
        //A 10. rekesz megtelik
        assertNotEquals(null, box10.getParcels());
        //A 11. rekesz megtelik
        assertNotEquals(null, box11.getParcels());

        //'aaa11' azonosítójú csomag
        //Ha ezt a csomagot helyezi be az automatába
        if(parcel11.getParcelLocker() != null){
            //El van helyezve
            assertTrue(parcel11.isShipped());
            //Van elhelyezési dátum és időpont
            assertNotEquals(null, parcel11.getShippingDate());
            assertNotEquals(null, parcel11.getShippingTime());
            //Van átvételi lejárati dátum és időpont
            assertNotEquals(null, parcel11.getPickingUpExpirationDate());
            assertNotEquals(null, parcel11.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés ellenőrzése
            assertEquals(parcelLocker2, parcel11.getParcelLocker());
            assertTrue(parcelLocker2.getParcels().contains(parcel11));
            //Csomag és futár összerendelés megszüntetése, ellenőrzés
            assertEquals(null, parcel11.getCourier());
            //Csomag és rekesz kapcsolat ellenőrzése
            assertNotEquals(null, parcel11.getBox());
        }
        else{
            //Nincs elhelyezve
            assertFalse(parcel11.isShipped());
            //Továbbra sincs elhelyezési dátum és időpont
            assertEquals(null, parcel11.getShippingDate());
            assertEquals(null, parcel11.getShippingTime());
            //Továbbra sincs átvételi lejárati dátum és időpont
            assertEquals(null, parcel11.getPickingUpExpirationDate());
            assertEquals(null, parcel11.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés nem jön létre
            assertNotEquals(parcelLocker2, parcel11.getParcelLocker());
            assertFalse(parcelLocker2.getParcels().contains(parcel11));
            //Csomag és futár összerendelés megmarad
            assertNotEquals(null, parcel11.getCourier());
            //Csomag és rekesz kapcsolat nem jön létre
            assertEquals(null, parcel11.getBox());
        }


        //'aaa12' azonosítójú csomag
        //Ha ezt a csomagot helyezi be az automatába
        if(parcel12.getParcelLocker() != null){
            //El van helyezve
            assertTrue(parcel12.isShipped());
            //Van elhelyezési dátum és időpont
            assertNotEquals(null, parcel12.getShippingDate());
            assertNotEquals(null, parcel12.getShippingTime());
            //Van átvételi lejárati dátum és időpont
            assertNotEquals(null, parcel12.getPickingUpExpirationDate());
            assertNotEquals(null, parcel12.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés ellenőrzése
            assertEquals(parcelLocker2, parcel12.getParcelLocker());
            assertTrue(parcelLocker2.getParcels().contains(parcel12));
            //Csomag és futár összerendelés megszüntetése, ellenőrzés
            assertEquals(null, parcel12.getCourier());
            //Csomag és rekesz kapcsolat ellenőrzése
            assertNotEquals(null, parcel12.getBox());
        }
        else{
            //Nincs elhelyezve
            assertFalse(parcel12.isShipped());
            //Továbbra sincs elhelyezési dátum és időpont
            assertEquals(null, parcel12.getShippingDate());
            assertEquals(null, parcel12.getShippingTime());
            //Továbbra sincs átvételi lejárati dátum és időpont
            assertEquals(null, parcel12.getPickingUpExpirationDate());
            assertEquals(null, parcel12.getPickingUpExpirationTime());
            //Csomag éa automata összerendelés nem jön létre
            assertNotEquals(parcelLocker2, parcel12.getParcelLocker());
            assertFalse(parcelLocker2.getParcels().contains(parcel12));
            //Csomag és futár összerendelés megmarad
            assertNotEquals(null, parcel12.getCourier());
            //Csomag és rekesz kapcsolat nem jön létre
            assertEquals(null, parcel12.getBox());
        }

        //'aaa13' azonosítójú közepes csomag. Ez ehhez az automatához van
        //El van helyezve
        assertTrue(parcel13.isShipped());
        //Van elhelyezési dátum és időpont
        assertNotEquals(null, parcel13.getShippingDate());
        assertNotEquals(null, parcel13.getShippingTime());
        //Van átvételi lejárati dátum és időpont
        assertNotEquals(null, parcel13.getPickingUpExpirationDate());
        assertNotEquals(null, parcel13.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés ellenőrzése
        assertEquals(parcelLocker2, parcel13.getParcelLocker());
        assertTrue(parcelLocker2.getParcels().contains(parcel13));
        //Csomag és futár összerendelés megszüntetése, ellenőrzés
        assertEquals(null, parcel13.getCourier());
        //Csomag és rekesz kapcsolat ellenőrzése
        assertNotEquals(null, parcel13.getBox());

        //'aaa14' azonosítójú csomag. Ez nem ehhez az automatához van
        //Nincs elhelyezve
        assertFalse(parcel14.isShipped());
        //Továbbra sincs elhelyezési dátum és időpont
        assertEquals(null, parcel14.getShippingDate());
        assertEquals(null, parcel14.getShippingTime());
        //Továbbra sincs átvételi lejárati dátum és időpont
        assertEquals(null, parcel14.getPickingUpExpirationDate());
        assertEquals(null, parcel14.getPickingUpExpirationTime());
        //Csomag éa automata összerendelés nem jön létre
        assertNotEquals(parcelLocker2, parcel14.getParcelLocker());
        assertFalse(parcelLocker2.getParcels().contains(parcel14));
        //Csomag és futár összerendelés megmarad
        assertNotEquals(null, parcel14.getCourier());
        //Csomag és rekesz kapcsolat nem jön létre
        assertEquals(null, parcel14.getBox());

        Mockito.verify(parcelLockerService).findById(Mockito.anyLong());
        Mockito.verify(courierService).findByUniqueCourierId(Mockito.anyString());
        Mockito.verify(boxService, Mockito.times(2)).findBySize("small");
        Mockito.verify(boxService).findBySize("medium");

    }

    //Ügyfél sikeresen átveszi a csomagját
    //A csomagot már előre kifizette
    @Test
    @Description("pickUpParcel function")
    void customerShouldPickUpItsParcel(){

        ResponseEntity<PickUpParcelResponse> response;

        Parcel parcel1 = new Parcel();
        parcel1.setUniqueParcelId("aaa1");
        parcel1.setPickingUpCode("abc12");
        parcel1.setPrice(0);
        parcel1.setShippingFrom(parcelLocker2);
        parcel1.setShippingTo(parcelLocker1);
        parcel1.setSendingDate(LocalDate.of(2023,10,23));
        parcel1.setSendingTime(LocalTime.of(15,31));
        parcel1.setPickingUpDateFromParcelLockerByCourier(LocalDate.of(2023,10,24));
        parcel1.setPickingUpTimeFromParcelLockerByCourier(LocalTime.of(8,40));
        parcel1.setHandingDateToFirstStoreByCourier(LocalDate.of(2023,10,24));
        parcel1.setHandingTimeToFirstStoreByCourier(LocalTime.of(15,49));
        parcel1.setPickingUpDateFromSecondStoreByCourier(LocalDate.of(2023,10,26));
        parcel1.setPickingUpTimeFromSecondStoreByCourier(LocalTime.of(7,14));
        parcel1.setShippingDate(LocalDate.of(2023,10,26));
        parcel1.setShippingTime(LocalTime.of(8,29));
        parcel1.setPickingUpExpirationDate(LocalDate.of(2023,10,29));
        parcel1.setPickingUpExpirationTime(LocalTime.of(8,29));
        parcel1.setShipped(true);
        parcel1.setPlaced(true);
        parcel1.setPickedUp(false);
        parcel1.setPaid(true);
        parcel1.setSize("small");
        parcel1.setBox(box1);

        //Csomag és automata összerendelése
        parcel1.setParcelLocker(parcelLocker1);
        parcelLocker1.getParcels().add(parcel1);

        //when parcel
        Mockito.when(parcelService.findByPickingUpCode("abc12")).thenReturn(parcel1);

        //when webclient


        response = parcelService.pickUpParcel("abc12", 1L);

        //Az 1-es rekesz nyílik ki
        assertEquals(1, response.getBody().getBoxNumber());
        //Csomag ára szerepel a válaszban
        assertEquals(parcel1.getPrice(), response.getBody().getPrice());
        //Csomagot át lehet venni üzenet
        assertEquals("pickedUp", response.getBody().getMessage());

        Mockito.verify(parcelService).findByPickingUpCode("abc12");



    }

}