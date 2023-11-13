package com.parcellocker.authenticationservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;

    private String emailAddress;

    private String password;

    private String activationCode;

    private boolean enable;

    private boolean isTwoFactorAuthentication;

    private String secondFactorCode;

    private Set<Role> roles = new HashSet<Role>();

}
