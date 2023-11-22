package com.parcellocker.parcelhandlerservice.payload.response;

import lombok.Data;

@Data
public class GetPersonalDataResponse {

    private Long id;

    private String lastName;

    private String firstName;

    private String phoneNumber;
}
