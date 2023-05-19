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
public class Box {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int maxWidth;

    @Column(nullable = false)
    private int maxLength;

    @Column(nullable = false)
    private int maxHeight;

    @Column(nullable = false)
    private int maxWeight;

    @Column(nullable = false)
    private int boxNumber;

    /*Opcionális adatok
    //Kapcsolat a Parcel és a Box között
    //Ez az osztály a birtokos
    @OneToMany(mappedBy = "box")
    private Set<Parcel> parcels = new HashSet<>();
     */

}
