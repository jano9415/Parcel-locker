package com.parcellocker.statisticsservice.payload.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ParcelToStaticticsServiceRequest {

    private String uniqueParcelId;

    //Feladási automata
    private int senderParcelLockerPostCode;

    private String senderParcelLockerCounty;

    private String senderParcelLockerCity;

    private String senderParcelLockerStreet;

    //Érkezési automata
    private int receiverParcelLockerPostCode;

    private String receiverParcelLockerCounty;

    private String receiverParcelLockerCity;

    private String receiverParcelLockerStreet;

    private String size;

    private int price;

    private String receiverName;

    private String receiverEmailAddress;

    private String senderName;

    private String senderEmailAddress;

    private boolean isShipped;

    private boolean isPickedUp;

    private String sendingDate;

    private String sendingTime;

    private String pickingUpDate;

    private String pickingUpTime;

    private String shippingDate;

    private String shippingTime;

    private boolean isPlaced;

    private boolean isPaid;

    private String pickingUpExpirationDate;

    private String pickingUpExpirationTime;

    private boolean isPickingUpExpired;

    private String sendingExpirationDate;

    private String sendingExpirationTime;

    private String pickingUpDateFromParcelLockerByCourier;

    private String pickingUpTimeFromParcelLockerByCourier;

    private String handingDateToFirstStoreByCourier;

    private String handingTimeToFirstStoreByCourier;

    private String pickingUpDateFromSecondStoreByCourier;

    private String pickingUpTimeFromSecondStoreByCourier;

}
