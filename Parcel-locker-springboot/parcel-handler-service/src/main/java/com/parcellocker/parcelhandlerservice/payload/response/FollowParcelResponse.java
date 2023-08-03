package com.parcellocker.parcelhandlerservice.payload.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FollowParcelResponse {

    private String message;

    private int shippingFromPostCode;

    private String shippingFromCounty;

    private String shippingFromCity;

    private String shippingFromStreet;

    private int storePostCode;

    private String storeCounty;

    private String storeCity;

    private String storeStreet;

    private int shippingToPostCode;

    private String shippingToCounty;

    private String shippingToCity;

    private String shippingToStreet;

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

    private boolean isSendingExpired;

}
