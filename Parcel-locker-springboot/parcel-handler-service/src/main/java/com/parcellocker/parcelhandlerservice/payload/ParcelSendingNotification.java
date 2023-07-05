package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ParcelSendingNotification {

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

    private String sendingDate;

    private String sendingTime;
}
