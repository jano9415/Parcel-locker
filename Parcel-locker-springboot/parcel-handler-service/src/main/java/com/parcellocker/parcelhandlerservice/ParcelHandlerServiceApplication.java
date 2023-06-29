package com.parcellocker.parcelhandlerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication
public class ParcelHandlerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelHandlerServiceApplication.class, args);
	}


}
