package com.parcellocker.parcelhandlerservice.payload.kafka;

import lombok.Data;

@Data
public class ParcelShippingNotification {

    private String receiverName;

    private String senderName;

    private String senderEmailAddress;

    private String receiverEmailAddress;

    private int price;

    private String uniqueParcelId;

    private int senderParcelLockerPostCode;

    private String senderParcelLockerCity;

    private String senderParcelLockerStreet;

    private int receiverParcelLockerPostCode;

    private String receiverParcelLockerCity;

    private String receiverParcelLockerStreet;

    private String shippingDate;

    private String shippingTime;

    private String pickingUpCode;
}
