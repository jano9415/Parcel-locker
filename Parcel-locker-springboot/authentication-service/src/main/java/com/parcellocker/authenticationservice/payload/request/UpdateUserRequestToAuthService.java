package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class UpdateUserRequestToAuthService {

    private String previousEmailAddress;

    private String newEmailAddress;

    private boolean isTwoFactorAuthentication;
}
