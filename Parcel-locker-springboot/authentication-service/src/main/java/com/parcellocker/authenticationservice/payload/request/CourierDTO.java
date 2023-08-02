package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

@Data
public class CourierDTO {

    private String uniqueCourierId;

    private String firstName;

    private String lastName;

    private Long storeId;
}
