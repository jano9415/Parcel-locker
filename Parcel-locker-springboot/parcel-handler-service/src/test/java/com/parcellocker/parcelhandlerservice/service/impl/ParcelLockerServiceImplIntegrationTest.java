package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
import com.parcellocker.parcelhandlerservice.payload.StringResponse;
import com.parcellocker.parcelhandlerservice.repository.ParcelLockerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParcelLockerServiceImplIntegrationTest {


    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //Összes csomag automata lekérése
    //Nem szükséges jwt token
    @Test
    @Description("getParcelLockersForChoice function")
    void itShouldGiveParcelLockers(){

        // A végpont URL-je
        String url = "http://localhost:" + port + "/parcelhandler/parcellocker/getparcellockersforchoice";

        // HTTP GET kérés a végpontra
        //ResponseEntity<List<ParcelLockerDTO>> responseEntity = restTemplate.getForEntity(url, List.class);

        long startTime = System.currentTimeMillis();
        //Kérés összeállítása
        ResponseEntity<List<ParcelLockerDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ParcelLockerDTO>>() {}
        );
        long endTime = System.currentTimeMillis();

        //A válasz időtartama
        long responseTime = endTime - startTime;
        System.out.println("A válasz időtartama: " + responseTime + " miliszekundum");

        //Válasz kódja
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //A válasz json formátum
        boolean isJsonType = false;
        String responseBodyType = responseEntity.getHeaders().getFirst("Content-Type");
        if(responseBodyType != null && responseBodyType.contains("application/json")){
            isJsonType = true;
        }
        assertTrue(isJsonType);

        //Csomagautomaták
        List<ParcelLockerDTO> parcelLockers = responseEntity.getBody();
        //A válasz tartalmaz csomagautomatákat
        assertNotNull(parcelLockers);
        //Az adatbázis 20 db automatát tartalmaz
        assertEquals(20, parcelLockers.size());

        System.out.println(parcelLockers.get(0));

    }

    //Automata tele van vagy nincs?
    //Nem szükséges jwt token
    @Test
    @Description("isParcelLockerFull function")
    void itShouldCheckTheParcelLockerFullOrNot(){

        //Veszprém pápai út 32 automata
        Long parcelLockerId = 6L;

        // A végpont URL-je
        String url = "http://localhost:" + port + "/parcelhandler/parcellocker/isparcellockerfull/" + parcelLockerId;

        long startTime = System.currentTimeMillis();
        //Kérés összeállítása
        ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StringResponse>() {}
        );
        long endTime = System.currentTimeMillis();

        //A válasz időtartama
        long responseTime = endTime - startTime;
        System.out.println("A válasz időtartama: " + responseTime + " miliszekundum");

        //Válasz kódja
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //A válasz json formátum
        boolean isJsonType = false;
        String responseBodyType = responseEntity.getHeaders().getFirst("Content-Type");
        if(responseBodyType != null && responseBodyType.contains("application/json")){
            isJsonType = true;
        }
        assertTrue(isJsonType);

        //Az automata nincs tele
        assertEquals("notfull", responseEntity.getBody().getMessage());




    }




}
