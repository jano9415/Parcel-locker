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
public class Store {

    @Id
    @GeneratedValue
    private Long id;

    //Kapcsolat a Store és az Address között
    //Ez az osztály a birtokos
    @OneToOne
    private Address address;

    //Parcel_in_store kapcsolótábla
    //A Parcel osztály a birtokos
    //Kapcsolat a Parcel és a Store között
    @OneToMany(mappedBy = "store")
    private Set<Parcel> parcels = new HashSet<>();

    /*Opcionális adatok
    //Kapcsolat a Parcel locker és a Store között
    //A Parcel locker osztály a birtokos
    @OneToOne(mappedBy = "store")
    private Set<ParcelLocker> parcelLockers = new HashSet<>();
     */
}
