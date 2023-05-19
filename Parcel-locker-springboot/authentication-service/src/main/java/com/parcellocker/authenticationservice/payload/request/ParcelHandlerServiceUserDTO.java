package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

import java.util.Set;

//Ezt az objektumot küldöm a parcel-handler service-nek
@Data
public class ParcelHandlerServiceUserDTO {

    private String emailAddress;

    private Set<String> roles;

    private String firstName;

    private String lastName;

    private int postCode;

    private String county;

    private String city;

    private String street;

    private String phoneNumber;

}
