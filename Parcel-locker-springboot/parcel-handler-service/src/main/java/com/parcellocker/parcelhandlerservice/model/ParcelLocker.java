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
public class ParcelLocker {

    private Long id;

    private Address location;

    private int amountOfBoxes;

    private Store store;

    private Set<Parcel> parcels;
}
