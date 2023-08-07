package com.example.parcel_locker_android.payload.response;

import java.util.List;


public class LoginResponse {

    private String message;

    private String token;

    private String tokenType = "Bearer";

    private String userId;

    private String emailAddress;

    private List<String> roles;

    public LoginResponse(String message, String token, String tokenType, String userId, String emailAddress, List<String> roles) {
        this.message = message;
        this.token = token;
        this.tokenType = tokenType;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
