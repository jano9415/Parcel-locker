package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class EmptyParcelLocker {

    private Long parcelLockerId;

    private String uniqueCourierId;
}
