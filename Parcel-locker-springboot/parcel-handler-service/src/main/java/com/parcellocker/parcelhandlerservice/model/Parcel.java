package com.parcellocker.parcelhandlerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Parcel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int uniqueParcelId;

    //Kapcsolat a Parcel és a User között
    //Ez az osztály a birtokos
    @ManyToOne
    private User user;

    //Kapcsolat a Parcel és a Parcel locker között
    //Ez az osztály a birtokos
    @ManyToOne
    private ParcelLocker shippingFrom;

    //Kapcsolat a Parcel és a Parcel locker között
    //Ez az osztály a birtokos
    @ManyToOne
    private ParcelLocker shippingTo;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverEmailAddress;

    @Column(nullable = false)
    private boolean isShipped;

    @Column(nullable = false)
    private boolean isPickedUp;

    private Date sendingDate;

    private Time sendingTime;

    private Date pickingUpDate;

    private Time pickingUpTime;

    private Date shippingDate;

    private Time shippingTime;

    //Kapcsolat a Parcel és a Box között
    //Ez az osztály a birtokos
    @ManyToOne
    private Box box;

    @Column(nullable = false)
    private boolean isPlaced;

    @Column(nullable = false)
    private boolean isPaid;

    //Parcel_in_parcel_locker kapcsolótábla
    //Ez az osztály a birtokos
    //Kapcsolat a Parcel és a Parcel locker között
    @JoinTable(
            name = "parcel_in_parcel_locker",
            joinColumns = {@JoinColumn(name = "parcel_id")},
            inverseJoinColumns = {@JoinColumn(name = "parcellocker_id")})
    @ManyToOne
    private ParcelLocker parcelLocker;

    //Parcel_in_store kapcsolótábla
    //Ez az osztály a birtokos
    //Kapcsolat a Parcel és a Store között
    @JoinTable(
            name = "parcel_in_store",
            joinColumns = {@JoinColumn(name = "parcel_id")},
            inverseJoinColumns = {@JoinColumn(name = "store_id")})
    @ManyToOne
    private Store store;

    //Parcel_during_shipping kapcsolótábla
    //Ez az osztály a birtokos
    //Kapcsolat a Parcel és a Courier között
    @JoinTable(
            name = "parcel_during_shipping",
            joinColumns = {@JoinColumn(name = "parcel_id")},
            inverseJoinColumns = {@JoinColumn(name = "courier_id")})
    @ManyToOne
    private Courier courier;
}
