package com.parcellocker.parcelhandlerservice.payload.response;

import lombok.Data;

@Data
public class PickUpParcelResponse {

    private int boxNumber;

    private String message;

    private int price;
}
