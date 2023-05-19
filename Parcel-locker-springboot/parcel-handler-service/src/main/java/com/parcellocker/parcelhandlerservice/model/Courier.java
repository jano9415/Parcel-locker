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
public class Courier {

    private Long id;

    private String uniqueCourierId;

    //Még nem tudom, hogy kell-e
    //private Set<Role> roles;

    private Set<Parcel> parcels;
}
