package com.parcellocker.authenticationservice.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class CreateAdminDTO {

    private String emailAddress;

    private String password;

}
