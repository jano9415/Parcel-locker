package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class LogInRequest {

    private String emailAddress;

    private String password;
}
