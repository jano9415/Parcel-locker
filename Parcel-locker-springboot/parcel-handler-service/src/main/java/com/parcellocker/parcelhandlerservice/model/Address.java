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
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int postCode;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    //Opcionális adatok
    //Kapcsolat a Parcel locker és az Address között
    //A Parcel locker osztály a birtokos
    @OneToOne(mappedBy = "location")
    private ParcelLocker parcelLocker;

    //Opcionális adatok
    //Kapcsolat a Store és az Address között
    //Az Address osztály a birtokos
    @OneToOne(mappedBy = "address")
    private Store store;

    /*Opcionális adatok
    //Kapcsolat a User és az Address között
    //A User osztály a birtokos
    @OneToMany(mappedBy = "address")
    private Set<User> users = new HashSet<>();
    */


}
