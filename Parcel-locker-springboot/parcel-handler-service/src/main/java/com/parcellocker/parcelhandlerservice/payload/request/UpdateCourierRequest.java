package com.parcellocker.parcelhandlerservice.payload.request;

import lombok.Data;

@Data
public class UpdateCourierRequest {

    private Long id;

    private String uniqueCourierId;

    //Ez egyben az rfid azonosító is
    private String password;

    private  String firstName;

    private String lastName;

    private Long storeId;
}
