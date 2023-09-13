package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class UpdateCourierRequestToAuthService {

    private String newUniqueCourierId;

    private String previousUniqueCourierId;

    //Ez egyben az rfid azonosító is
    private String password;
}
