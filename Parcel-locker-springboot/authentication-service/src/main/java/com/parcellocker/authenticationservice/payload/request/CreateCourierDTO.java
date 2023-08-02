package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;


@Data
public class CreateCourierDTO {

    private String uniqueCourierId;

    private String password;

    private String firstName;

    private String lastName;

    private Long storeId;
}
