package com.parcellocker.authenticationservice.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    private String token;

    private String tokenType = "Bearer";

    private String userId;

    private String emailAddress;

    private String firstName;

    private String lastName;

    private List<String> roles;
}
