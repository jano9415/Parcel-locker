package com.parcellocker.parcelhandlerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private String uniqueParcelId;

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
    private String size;

    /*
    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int weight;
     */

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverEmailAddress;

    private String senderName;

    private String senderEmailAddress;

    @Column(nullable = false)
    private boolean isShipped;

    @Column(nullable = false)
    private boolean isPickedUp;

    private LocalDate sendingDate;

    private LocalTime sendingTime;

    private LocalDate pickingUpDate;

    private LocalTime pickingUpTime;

    private LocalDate shippingDate;

    private LocalTime shippingTime;

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

    @Column(nullable = false)
    private String pickingUpCode;

    private String sendingCode;

    private LocalDate pickingUpExpirationDate;

    private LocalTime pickingUpExpirationTime;

    @Column(nullable = false)
    private boolean isPickingUpExpired;

    private LocalDate sendingExpirationDate;

    private LocalTime sendingExpirationTime;

    private boolean isSendingExpired;

    private LocalDate pickingUpDateFromParcelLockerByCourier;

    private LocalTime pickingUpTimeFromParcelLockerByCourier;

    private LocalDate handingDateToFirstStoreByCourier;

    private LocalTime handingTimeToFirstStoreByCourier;

    private LocalDate pickingUpDateFromSecondStoreByCourier;

    private LocalTime pickingUpTimeFromSecondStoreByCourier;
}
