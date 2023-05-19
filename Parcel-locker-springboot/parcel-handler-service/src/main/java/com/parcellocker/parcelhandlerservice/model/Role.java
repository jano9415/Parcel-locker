package com.parcellocker.parcelhandlerservice.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Entity
//Még nem tudom, kell-e
public class Role {

    private Long id;

    private String roleName;

    private Set<User> users;

    private Set<Courier> couriers;
}
