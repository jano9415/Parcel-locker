package com.parcellocker.parcelhandlerservice.payload.response;

import lombok.Data;

@Data
public class GetParcelForSendingWithCodeResponse {

    private String message;

    private int boxNumber;
}
