package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.example.parcel_locker_android.payload.response.FollowParcelResponse;
import com.example.parcel_locker_android.payload.response.StringResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ParcelService {

    String API_URL = "parcelhandler/parcel/";

    //Csomag leadása a központi raktárba
    //Jwt token szükséges
    @GET(API_URL + "handparceltostore/{uniqueCourierId}/{uniqueParcelId}")
    Call<StringResponse> handParcelToStore(@Path("uniqueCourierId") String uniqueCourierId,
                                           @Path("uniqueParcelId") String uniqueParcelId,
                                           @Header("Authorization") String jwtToken);

    //Csomag felvétele a központi raktárból
    //Jwt token szükséges
    @GET(API_URL + "pickupparcelfromstore/{uniqueCourierId}/{uniqueParcelId}")
    Call<StringResponse> pickUpParcelFromStore(@Path("uniqueCourierId") String uniqueCourierId,
                                           @Path("uniqueParcelId") String uniqueParcelId,
                                           @Header("Authorization") String jwtToken);

    //Csomag nyomonkövetése
    //Jwt token nem szükséges
    @GET(API_URL + "followparcel/{uniqueParcelId}")
    Call<FollowParcelResponse> followParcel(@Path("uniqueParcelId") String uniqueParcelId);

    //Csomag küldése a weblapról feladási kóddal
    //Ez még csak egy előzetes csomagfeladás. A felhasználó megkapja email-ben a csomagfeladási kódot
    //A végleges csomagfeladás az automatánál történik
    //Jwt token szükséges
    @POST(API_URL + "sendparcelwithcodefromwebpage")
    Call<StringResponse> sendParcelWithCodeFromWebpage(@Body SendParcelWithCodeFromWebpageRequest request,
                                                       @Header("Authorization") String jwtToken);

}
