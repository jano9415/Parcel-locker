package com.parcellocker.authenticationservice.config;

import com.parcellocker.authenticationservice.model.Role;
import com.parcellocker.authenticationservice.model.User;
import com.parcellocker.authenticationservice.repository.UserRepository;
import com.parcellocker.authenticationservice.service.serviceimpl.RoleServiceImpl;
import com.parcellocker.authenticationservice.service.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InitDatabase {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    //Szerepkörök létrehozása
    /*
    @Bean
    public void createRoles(){
        Role role = new Role();
        //role.setRoleName("courier");
        //roleService.save(role);

        //role.setRoleName("admin");
        //roleService.save(role);

        role.setRoleName("user");
        roleService.save(role);
    }
     */

    //Felhasználók létrehozása
    /*
    @Bean
    public void createUsers(){
        User user = new User();
        user.setEmailAddress("admin@gmail.com");
        user.setPassword("Password.!1");
        user.setEnable(true);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName("admin").get());
        user.setRoles(roles);
        userRepository.save(user);

    }
     */

}
