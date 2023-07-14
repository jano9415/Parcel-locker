package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class EmptyParcelLockerRequest {

    private Long parcelLockerId;

    private String uniqueCourierId;
}
