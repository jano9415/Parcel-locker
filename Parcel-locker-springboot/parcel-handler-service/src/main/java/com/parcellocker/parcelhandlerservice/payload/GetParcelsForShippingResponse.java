package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

@Data
public class GetParcelsForShippingResponse {

    private String uniqueParcelId;

    private int price;

    private int senderParcelLockerPostCode;

    private String senderParcelLockerCounty;

    private String senderParcelLockerCity;

    private String senderParcelLockerStreet;

    private int receiverParcelLockerPostCode;

    private String receiverParcelLockerCounty;

    private String receiverParcelLockerCity;

    private String receiverParcelLockerStreet;

    private int boxNumber;
}
