package com.example.parcel_locker_android.service;

import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;
import com.example.parcel_locker_android.payload.response.GetSaturationDatasResponse;
import com.example.parcel_locker_android.payload.response.StringResponse;

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

    //Automata tele van?
    //Nem szükséges jwt token
    @GET(API_URL + "isparcellockerfull/{senderParcelLockerId}")
    Call<StringResponse> isParcelLockerFull(@Path("senderParcelLockerId") Long senderParcelLockerId);

    //Rekeszek tele vannak? Kicsi, közepes, nagy rekeszek ellenőrzése.
    //Nem szükséges jwt token
    @GET(API_URL + "areboxesfull/{senderParcelLockerId}")
    Call<List<StringResponse>> areBoxesFull(@Path("senderParcelLockerId") Long senderParcelLockerId);



}
