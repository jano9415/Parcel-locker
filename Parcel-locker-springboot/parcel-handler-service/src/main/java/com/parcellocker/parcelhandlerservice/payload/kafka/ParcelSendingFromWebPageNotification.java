package com.parcellocker.parcelhandlerservice.payload.kafka;

import lombok.Data;

@Data
public class ParcelSendingFromWebPageNotification {


    private String senderName;

    private String senderEmailAddress;

    private int price;

    private String uniqueParcelId;

    private int senderParcelLockerPostCode;

    private String senderParcelLockerCity;

    private String senderParcelLockerStreet;

    private int receiverParcelLockerPostCode;

    private String receiverParcelLockerCity;

    private String receiverParcelLockerStreet;

    private String sendingDate;

    private String sendingTime;

    private String sendingCode;
}
