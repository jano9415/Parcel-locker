package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.request.LoginRequest;
import com.example.parcel_locker_android.payload.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    String API_URL = "auth/";

    //Bejelentkezés
    @POST(API_URL + "login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
