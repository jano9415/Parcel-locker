package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;
import com.example.parcel_locker_android.payload.response.GetSaturationDatasResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParcelLockerService {

    String API_URL = "/parcelhandler/parcellocker/";

    //Automaták lekérése
    //Nem szükséges jwt token
    @GET(API_URL + "getparcellockersforchoice")
    Call<List<GetParcelLockersResponse>> getParcelLockersForChoice();

    //Automata telítettségi adatok lekérése
    //Nem szükséges jwt token
    @GET(API_URL + "getsaturationdatas/{parcelLockerId}")
    Call<GetSaturationDatasResponse> getSaturationDatas(@Path("parcelLockerId") Long parcelLockerId);
}
