package com.parcellocker.authenticationservice.service.serviceimpl;

import com.parcellocker.authenticationservice.model.Role;
import com.parcellocker.authenticationservice.repository.RoleRepository;
import com.parcellocker.authenticationservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    //Keresés id szerint
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }

    //Új role hozzáadása
    @Override
    public void save(Role role) {
        roleRepository.save(role);

    }

    //Keresés szerepkör neve szerint
    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
