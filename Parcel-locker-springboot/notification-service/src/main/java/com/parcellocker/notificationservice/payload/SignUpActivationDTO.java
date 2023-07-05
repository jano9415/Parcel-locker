package com.parcellocker.notificationservice.payload;

import lombok.Data;

@Data
public class SignUpActivationDTO {

    private String emailAddress;

    private String activationCode;

    private String firstName;

    private String lastName;
}
