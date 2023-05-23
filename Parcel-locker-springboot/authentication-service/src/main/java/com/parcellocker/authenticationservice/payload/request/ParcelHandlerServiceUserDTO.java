package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

import java.util.Set;

//Ezt az objektumot küldöm a parcel-handler service-nek
@Data
public class ParcelHandlerServiceUserDTO {

    private String emailAddress;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
