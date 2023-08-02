package com.parcellocker.parcelhandlerservice.controller;

import com.parcellocker.parcelhandlerservice.payload.response.GetStoresResponse;
import com.parcellocker.parcelhandlerservice.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcelhandler/store")
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

    //Központi raktárak lekérése
    //Jwt token szükséges
    @GetMapping("/getstores")
    public ResponseEntity<List<GetStoresResponse>> getStores(){
        return storeService.getStores();
    }
}
