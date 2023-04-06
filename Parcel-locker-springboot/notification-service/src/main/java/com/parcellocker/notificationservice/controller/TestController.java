package com.parcellocker.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/notification/test1")
    public ResponseEntity<String> notificationTest1(){
        return ResponseEntity.ok("Hello from notification service");
    }
}
