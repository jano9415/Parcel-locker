package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class UpdateCourierRequest {

    private String uniqueCourierId;

    //Ez egyben az rfid azonosító is
    private String password;
}
