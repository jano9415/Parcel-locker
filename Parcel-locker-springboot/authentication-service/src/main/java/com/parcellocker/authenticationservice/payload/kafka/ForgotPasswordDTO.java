package com.parcellocker.authenticationservice.payload.kafka;

import lombok.Data;

@Data
public class ForgotPasswordDTO {

    private String emailAddress;

    private String newPassword;
}
