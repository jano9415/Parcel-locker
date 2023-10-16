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
public class Courier {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String uniqueCourierId;

    @Column(nullable = false)
    private  String firstName;

    @Column(nullable = false)
    private String lastName;

    //Parcel_during_shipping kapcsolótábla
    //A Parcel osztály a birtokos
    //Kapcsolat a Parcel és a Courier között
    @OneToMany(mappedBy = "courier")
    private Set<Parcel> parcels = new HashSet<>();

    //Kapcsolat a Courier és a Store között
    //Ez az osztály a birtokos
    @ManyToOne
    private Store area;
}
