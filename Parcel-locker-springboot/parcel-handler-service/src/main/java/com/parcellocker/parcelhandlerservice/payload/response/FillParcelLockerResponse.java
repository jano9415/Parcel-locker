package com.parcellocker.parcelhandlerservice.payload.response;

import lombok.Data;

@Data
public class FillParcelLockerResponse {

    private String message;

    private String uniqueParcelId;

    private int boxNumber;
}
