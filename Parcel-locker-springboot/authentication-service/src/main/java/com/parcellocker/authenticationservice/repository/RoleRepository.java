package com.parcellocker.authenticationservice.repository;

import com.parcellocker.authenticationservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RoleRepository extends MongoRepository<Role, Long> {

    //Keresés szerepkör neve szerint
    Optional<Role> findByRoleName(String roleName);

}
