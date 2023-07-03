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
public class ParcelLocker {

    @Id
    @GeneratedValue
    private Long id;

    //Kapcsolat a Parcel locker és az Address között
    //Ez az osztály a birtokos
    @OneToOne
    private Address location;

    @Column(nullable = false)
    private int amountOfBoxes;

    @Column(nullable = false)
    private int amountOfSmallBoxes;

    @Column(nullable = false)
    private int amountOfMediumBoxes;

    @Column(nullable = false)
    private int amountOfLargeBoxes;

    //Kapcsolat a Parcel locker és a Store között
    //Ez az osztály a birtokos
    @ManyToOne
    private Store store;

    //Parcel_in_parcel_locker kapcsolótábla
    //A Parcel osztály a birtokos
    //Kapcsolat a Parcel és a Parcel locker között
    @OneToMany(mappedBy = "parcelLocker")
    private Set<Parcel> parcels = new HashSet<>();

    //Opcionális adatok.
    //Kapcsolat a Parcel és a Parcel locker között
    //A Parcel osztály a birtokos
    @OneToMany(mappedBy = "shippingFrom")
    private Set<Parcel> parcelsFrom = new HashSet<>();

    //Opcionális adatok
    //Kapcsolat a Parcel és a Parcel locker között
    //A Parcel osztály a birtokos
    @OneToMany(mappedBy = "shippingTo")
    private Set<Parcel> parcelsTo = new HashSet<>();


}
