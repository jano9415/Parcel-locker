package com.parcellocker.statisticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Parcel {

    @Id
    private String id;

    private String uniqueParcelId;

    private ParcelLocker shippingFrom;

    private ParcelLocker shippingTo;

    private String size;

    private int price;

    private String receiverName;

    private String receiverEmailAddress;

    private String senderName;

    private String senderEmailAddress;

    private boolean isShipped;

    private boolean isPickedUp;

    private LocalDate sendingDate;

    private LocalTime sendingTime;

    private LocalDate pickingUpDate;

    private LocalTime pickingUpTime;

    private LocalDate shippingDate;

    private LocalTime shippingTime;

    private boolean isPlaced;

    private boolean isPaid;

    private String pickingUpCode;

    private String sendingCode;

    private LocalDate pickingUpExpirationDate;

    private LocalTime pickingUpExpirationTime;

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
