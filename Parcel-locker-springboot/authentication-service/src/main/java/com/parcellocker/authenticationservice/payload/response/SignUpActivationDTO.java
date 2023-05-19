package com.parcellocker.authenticationservice.payload.response;


import lombok.Data;

//Ezt az objektumot küldöm a kafka "signup_email_topic" topicnak
@Data
public class SignUpActivationDTO {

    private String emailAddress;

    private String activationCode;

    private String firstName;

    private String lastName;

}
