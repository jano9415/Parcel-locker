package com.parcellocker.parcelhandlerservice.service.impl;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.payload.ParcelLockerDTO;
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

    @Test
    void itShouldGiveParcelLockers(){

        // A végpont URL-je
        String url = "http://localhost:" + port + "/parcelhandler/parcellocker/getparcellockersforchoice";

        // HTTP GET kérés a végpontra
        //ResponseEntity<List<ParcelLockerDTO>> responseEntity = restTemplate.getForEntity(url, List.class);

        ResponseEntity<List<ParcelLockerDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ParcelLockerDTO>>() {}
        );

        // Ellenőrizd a válasz kódját
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Ellenőrizd a válasz tartalmát
        List<ParcelLockerDTO> parcelLockers = responseEntity.getBody();
        assertNotNull(parcelLockers);
        assertTrue(parcelLockers.size() > 0);
        System.out.println(parcelLockers.get(0).getCity());

        // Egyéb ellenőrzések a válasz tartalmára vonatkozóan
        // ...

    }




}
