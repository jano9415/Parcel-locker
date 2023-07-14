package com.parcellocker.parcelhandlerservice.payload.kafka;

import lombok.Data;

@Data
public class ParcelPickingUpNotification {

    private String receiverName;

    private String senderName;

    private String senderEmailAddress;

    private String receiverEmailAddress;

    private String uniqueParcelId;

    private int receiverParcelLockerPostCode;

    private String receiverParcelLockerCity;

    private String receiverParcelLockerStreet;

    private String pickingUpDate;

    private String pickingUpTime;

}
