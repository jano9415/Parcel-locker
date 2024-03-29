package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class UpdateCourierRequest {

    private String newUniqueCourierId;

    private String previousUniqueCourierId;

    //Ez egyben az rfid azonosító is
    private String password;
}
