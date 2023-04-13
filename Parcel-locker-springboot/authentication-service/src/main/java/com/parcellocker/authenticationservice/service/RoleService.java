package com.parcellocker.authenticationservice.service;

import com.parcellocker.authenticationservice.model.Role;

import java.util.Optional;

public interface RoleService {

    //Keresés id szerint
    Role findById(Long id);

    //Új role hozzáadása
    void save(Role role);

    //Keresés szerepkör neve szerint
    Optional<Role> findByRoleName(String roleName);
}
