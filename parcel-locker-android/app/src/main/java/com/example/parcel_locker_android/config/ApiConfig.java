package com.example.parcel_locker_android.config;

import com.example.parcel_locker_android.service.AuthService;
import com.example.parcel_locker_android.service.ParcelLockerService;
import com.example.parcel_locker_android.service.ParcelService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    private static final String API_GATEWAY_URL = "http://192.168.0.13:8080/";
    private static ApiConfig instance;
    private Retrofit retrofit;

    private ApiConfig(){
        retrofit = new Retrofit.Builder()
                .baseUrl(API_GATEWAY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiConfig getInstance(){
        if(instance == null){
            instance = new ApiConfig();
        }
        return instance;
    }

    public AuthService authService(){
        return retrofit.create(AuthService.class);
    }

    public ParcelService parcelService(){
        return retrofit.create(ParcelService.class);
    }

    public ParcelLockerService parcelLockerService(){
        return retrofit.create(ParcelLockerService.class);
    }
}
