package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    private String emailAddress;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
