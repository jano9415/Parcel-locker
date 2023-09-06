package com.parcellocker.parcelhandlerservice.payload;

import com.parcellocker.parcelhandlerservice.model.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ParcelDTO {

    private Long id;

    private String uniqueParcelId;

    private int shippingFromPostCode;

    private String shippingFromCounty;

    private String shippingFromCity;

    private String shippingFromStreet;

    private int shippingToPostCode;

    private String shippingToCounty;

    private String shippingToCity;

    private String shippingToStreet;

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

    //Rekesz adatok
    private int maxBoxWidth;

    private int maxBoxLength;

    private int maxBoxHeight;

    private int maxBoxWeight;

    private String boxSize;

    private int boxNumber;

    private boolean isPlaced;

    private boolean isPaid;

    //Raktár adatok
    private int storePostCode;

    private String storeCounty;

    private String storeCity;

    private String storeStreet;

    private String pickingUpCode;

    private String sendingCode;

    private String pickingUpExpirationDate;

    private String pickingUpExpirationTime;

    private boolean isPickingUpExpired;

    private String sendingExpirationDate;

    private String sendingExpirationTime;

    private boolean isSendingExpired;
}
