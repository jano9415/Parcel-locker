package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ParcelLockerService {

    String API_URL = "/parcelhandler/parcellocker/";

    @GET(API_URL + "getparcellockersforchoice")
    Call<GetParcelLockersResponse> getParcelLockersForChoice();
}
