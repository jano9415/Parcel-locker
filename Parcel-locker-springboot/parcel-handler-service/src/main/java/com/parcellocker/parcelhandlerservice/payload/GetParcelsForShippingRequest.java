package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

@Data
public class GetParcelsForShippingRequest {

    private Long parcelLockerId;

    private String uniqueCourierId;
}
