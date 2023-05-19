package com.parcellocker.parcelhandlerservice.model;

import jakarta.persistence.Entity;
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

    private Long id;

    private int uniqueParcelId;

    private User user;

    private ParcelLocker shippingFrom;

    private ParcelLocker shippingTo;

    private int width;

    private int length;

    private int height;

    private int weight;

    private int price;

    private String receiverName;

    private String receiverEmailAddress;

    private boolean isShipped;

    private boolean isPickedUp;

    private Date sendingDate;

    private Time sendingTime;

    private Date pickingUpDate;

    private Time pickingUpTime;

    private Date shippingDate;

    private Time shippingTime;

    private Box box;

    private boolean isPlaced;

    private boolean isPaid;

    private ParcelLocker parcelLocker;

    private Store store;

    private Courier courier;
}
