package com.parcellocker.parcelhandlerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "parcelhandleruser")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    /*
    //Még nem tudom, hogy kell-e
    //User_roles kapcsolótábla
    //Ez az osztály a birtokos
    //Kapcsolat a Role és a User között
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ManyToMany
    private Set<Role> roles;
     */

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    /*
    //Kapcsolat a User és az Address között
    //Ez az osztály a birtokos
    @ManyToOne
    private Address address;
     */

    @Column(nullable = false)
    private String phoneNumber;

    //Opcionális adatok
    //Kapcsolat a Parcel és a User között
    //A Parcel osztály a birtokos
    @OneToMany(mappedBy = "user")
    private Set<Parcel> parcels = new HashSet<>();
}
