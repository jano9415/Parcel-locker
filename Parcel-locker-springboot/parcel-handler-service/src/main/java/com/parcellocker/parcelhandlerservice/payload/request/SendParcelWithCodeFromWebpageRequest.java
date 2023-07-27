package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class SendParcelWithCodeFromWebpageRequest {

    private int price;

    private String size;

    private Long parcelLockerFromId;

    private Long parcelLockerToId;

    private String receiverName;

    private String receiverEmailAddress;

    private String receiverPhoneNumber;

    private String senderEmailAddress;
}
