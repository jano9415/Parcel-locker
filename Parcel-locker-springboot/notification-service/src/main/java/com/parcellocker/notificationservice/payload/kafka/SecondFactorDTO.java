package com.parcellocker.notificationservice.payload.kafka;

import lombok.Data;

@Data
public class SecondFactorDTO {

    private String emailAddress;

    private String secondFactorCode;
}
