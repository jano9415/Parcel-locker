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

    //Csomag leadása a központi raktárba
    @GET(API_URL + "handparceltostore/{uniqueCourierId}/{uniqueParcelId}")
    Call<StringResponse> handParcelToStore(@Path("uniqueCourierId") String uniqueCourierId,
                                           @Path("uniqueParcelId") String uniqueParcelId,
                                           @Header("Authorization") String jwtToken);

    //Csomag felvétele a központi raktárból
    @GET(API_URL + "pickupparcelfromstore/{uniqueCourierId}/{uniqueParcelId}")
    Call<StringResponse> pickUpParcelFromStore(@Path("uniqueCourierId") String uniqueCourierId,
                                           @Path("uniqueParcelId") String uniqueParcelId,
                                           @Header("Authorization") String jwtToken);
}
