package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class UpdateUserPasswordRequest {

    private String emailAddress;

    private String password;

    private String newPassword;
}
