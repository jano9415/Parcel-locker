package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    private String emailAddress;

    private Set<String> roles;

    private String password;

    private String firstName;

    private String lastName;

    private int postCode;

    private String county;

    private String city;

    private String street;

    private String phoneNumber;
}
