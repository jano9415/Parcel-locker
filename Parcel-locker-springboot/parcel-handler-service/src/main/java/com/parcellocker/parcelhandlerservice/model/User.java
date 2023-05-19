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
@Entity
public class User {

    private Long id;

    private String emailAddress;

    //Még nem tudom, hogy kell-e
    //private Set<Role> roles;

    private String firstName;

    private String lastName;

    private Address address;

    private String phoneNumber;
}
