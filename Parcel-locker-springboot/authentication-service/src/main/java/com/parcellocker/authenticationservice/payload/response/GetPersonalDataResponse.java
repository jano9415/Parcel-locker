package com.parcellocker.authenticationservice.payload.response;

import com.parcellocker.authenticationservice.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetPersonalDataResponse {

    private boolean enable;

    private boolean isTwoFactorAuthentication;

}
