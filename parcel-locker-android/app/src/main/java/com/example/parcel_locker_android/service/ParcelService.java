package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.response.StringResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ParcelService {

    String API_URL = "parcelhandler/parcel/";

    @GET(API_URL + "handparceltostore/{uniqueCourierId}/{uniqueParcelId}")
    Call<StringResponse> handParcelToStore(@Path("uniqueCourierId") String uniqueCourierId,
                                           @Path("uniqueParcelId") String uniqueParcelId,
                                           @Header("Authorization") String jwtToken);
}
