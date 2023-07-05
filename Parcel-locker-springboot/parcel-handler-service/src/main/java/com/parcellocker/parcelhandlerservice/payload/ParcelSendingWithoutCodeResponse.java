package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

@Data
public class ParcelSendingWithoutCodeResponse {

    private String message;

    private int boxNumber;
}
