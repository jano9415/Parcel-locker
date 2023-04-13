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

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private UserServiceImpl userService;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	public void pelda(){
		//Role role = new Role();
		//role.setRoleName("user");
		//roleService.save(role);

		/*User user = new User();
		user.setFirstName("László3");
		user.setLastName("Kovács");
		user.setPassword("jelszo123");
		user.getRoles().add(role);
		userService.signUp(user);*/

	}

}
