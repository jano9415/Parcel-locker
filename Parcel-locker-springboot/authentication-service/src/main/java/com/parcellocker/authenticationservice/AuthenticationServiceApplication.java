package com.parcellocker.authenticationservice;

import com.parcellocker.authenticationservice.model.Role;
import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.service.serviceimpl.RoleServiceImpl;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}




}
