package com.parcellocker.authenticationservice.exception;

import javax.naming.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {

    public JwtTokenMissingException(String message){
        super(message);
    }
}
