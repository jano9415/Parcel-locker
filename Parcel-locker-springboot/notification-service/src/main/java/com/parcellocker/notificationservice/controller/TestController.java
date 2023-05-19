package com.parcellocker.notificationservice.controller;

import com.parcellocker.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/notification/test1")
    public ResponseEntity<String> notificationTest1(){
        return ResponseEntity.ok("Hello from notification service test1");
    }

    @GetMapping("/notification/test2")
    public ResponseEntity<String> notificationTest2(){
        return ResponseEntity.ok("Hello from notification service test2");
    }
}
