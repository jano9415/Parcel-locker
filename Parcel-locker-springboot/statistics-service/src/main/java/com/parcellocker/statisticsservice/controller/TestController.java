package com.parcellocker.statisticsservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/statistics/test1")
    public ResponseEntity<String> statisticsTest1(){
        return ResponseEntity.ok("Hello from statistics service test1");
    }

    @GetMapping("/statistics/test2")
    public ResponseEntity<String> statisticsTest2(){
        return ResponseEntity.ok("Hello from statistics service test2");
    }
}
