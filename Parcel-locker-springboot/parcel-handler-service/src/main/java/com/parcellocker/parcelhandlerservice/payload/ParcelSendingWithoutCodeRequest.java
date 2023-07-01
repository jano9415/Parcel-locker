package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

@Data
public class ParcelSendingWithoutCodeRequest {

    private String receiverName;

    private String receiverEmailAddress;

    private String receiverPhoneNumber;

    private String parcelSize;

    private Long selectedParcelLockerId;

    private String senderName;

    private String senderEmailAddress;

    private int price;


}
