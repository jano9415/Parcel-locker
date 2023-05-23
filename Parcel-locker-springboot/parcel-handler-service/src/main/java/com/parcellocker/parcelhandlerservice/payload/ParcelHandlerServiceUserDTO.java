package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

import java.util.Set;

@Data
public class ParcelHandlerServiceUserDTO {

    private String emailAddress;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
