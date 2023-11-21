package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private Long id;

    private String emailAddress;

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private boolean isTwoFactorAuthentication;
}
