package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parcel_locker_android.MainActivity;
import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.response.FollowParcelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowParcelActivity extends AppCompatActivity {

    private Context context;

    private EditText followUniqueParcelIdEt;

    private TextView notFoundTv, isPlacedTv, isSentTv, isShippedTv, isPickedUpTv, backBtn3,
                    senderTv, arrivedInFirstStoreTv, startedFromSecondStoreTv, isExpiredTv;

    private Button followUniqueParcelIdBtn;

    private CardView notFoundCv, isPlacedCv, isSentCv, isShippedCv, isPickedUpCv, senderCv,
            arrivedInFirstStoreCv, startedFromSecondStoreCv, isExpiredCv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_parcel);

        context = this;
        followUniqueParcelIdEt = findViewById(R.id.followUniqueParcelIdEt);
        followUniqueParcelIdBtn = findViewById(R.id.followUniqueParcelIdBtn);
        backBtn3 = findViewById(R.id.backBtn3);
        notFoundCv = findViewById(R.id.notFoundCv);
        isPlacedCv = findViewById(R.id.isPlacedCv);
        isSentCv = findViewById(R.id.isSentCv);
        isShippedCv = findViewById(R.id.isShippedCv);
        isPickedUpCv = findViewById(R.id.isPickedUpCv);
        senderCv = findViewById(R.id.senderCv);
        arrivedInFirstStoreCv = findViewById(R.id.arrivedInFirstStoreCv);
        startedFromSecondStoreCv = findViewById(R.id.startedFromSecondStoreCv);
        isExpiredCv = findViewById(R.id.isExpiredCv);

        notFoundTv = findViewById(R.id.notFoundTv);
        isPlacedTv = findViewById(R.id.isPlacedTv);
        isSentTv = findViewById(R.id.isSentTv);
        isShippedTv = findViewById(R.id.isShippedTv);
        isPickedUpTv = findViewById(R.id.isPickedUpTv);
        senderTv = findViewById(R.id.senderTv);
        arrivedInFirstStoreTv = findViewById(R.id.arrivedInFirstStoreTv);
        startedFromSecondStoreTv = findViewById(R.id.startedFromSecondStoreTv);
        isExpiredTv = findViewById(R.id.isExpiredTv);

        notFoundCv.setVisibility(View.INVISIBLE);
        senderCv.setVisibility(View.INVISIBLE);
        isPlacedCv.setVisibility(View.INVISIBLE);
        isSentCv.setVisibility(View.INVISIBLE);
        isShippedCv.setVisibility(View.INVISIBLE);
        isPickedUpCv.setVisibility(View.INVISIBLE);
        arrivedInFirstStoreCv.setVisibility(View.INVISIBLE);
        startedFromSecondStoreCv.setVisibility(View.INVISIBLE);
        isExpiredCv.setVisibility(View.INVISIBLE);



        //Csomag követése
        followUniqueParcelIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notFoundCv.setVisibility(View.INVISIBLE);
                senderCv.setVisibility(View.INVISIBLE);
                isPlacedCv.setVisibility(View.INVISIBLE);
                isSentCv.setVisibility(View.INVISIBLE);
                isShippedCv.setVisibility(View.INVISIBLE);
                isPickedUpCv.setVisibility(View.INVISIBLE);
                arrivedInFirstStoreCv.setVisibility(View.INVISIBLE);
                startedFromSecondStoreCv.setVisibility(View.INVISIBLE);
                isExpiredCv.setVisibility(View.INVISIBLE);

                String uniqueParcelId = followUniqueParcelIdEt.getText().toString().trim();

                if(validateData(uniqueParcelId)){

                    Call<FollowParcelResponse> call = ApiConfig.getInstance().parcelService()
                            .followParcel(uniqueParcelId);

                    call.enqueue(new Callback<FollowParcelResponse>() {
                        @Override
                        public void onResponse(Call<FollowParcelResponse> call, Response<FollowParcelResponse> response) {

                            //Csomag nem található
                            if(response.body().getMessage() != null && response.body().getMessage().equals("notFound")){
                                notFoundCv.setVisibility(View.VISIBLE);
                                notFoundTv.setText("A megadott azonosítóval nem található csomag");

                            }

                            //Feladó adatai
                            if(response.body().getMessage() == null){
                                senderCv.setVisibility(View.VISIBLE);
                                String message = "Feladó neve: " + response.body().getSenderName() + "\n" +
                                        "Feladó email címe: " + response.body().getSenderEmailAddress() + "\n";
                                senderTv.setText(message);
                            }

                            //Csomag előzetesen feladva
                            if(response.body().getMessage() == null && response.body().getSendingExpirationDate() != null){
                                isPlacedCv.setVisibility(View.VISIBLE);
                                String message = "Csomag még nincs elhelyezve, csak előzetesen feladva" + "\n"
                                        + "Itt tudod feladni: " + response.body().getShippingFromPostCode()
                                        + " " + response.body().getShippingFromCity()
                                        + " " + response.body().getShippingFromStreet() + "\n"
                                        + "Eddig tudod feladni: " + response.body().getSendingExpirationDate()
                                        + " " + response.body().getSendingExpirationTime();
                                isPlacedTv.setText(message);

                            }

                            //Csomag feladva
                            if(response.body().getMessage() == null && response.body().getSendingDate() != null){
                                isSentCv.setVisibility(View.VISIBLE);
                                String message = "Csomag feladva " + response.body().getSendingDate()
                                        + " " + response.body().getSendingTime() + "-kor" + "\n"
                                        + "Feladási automata: " + response.body().getShippingFromPostCode()
                                        + " " + response.body().getShippingFromCity()
                                        + " " + response.body().getShippingFromStreet() + "\n";
                                isSentTv.setText(message);

                            }

                            //Csomag megérkezett a feladási megyei raktárba
                            if(response.body().getMessage() == null &&
                                    response.body().getHandingDateToFirstStoreByCourier() != null){
                                arrivedInFirstStoreCv.setVisibility(View.VISIBLE);
                                String message = "Csomag leszállítva " + response.body().getHandingDateToFirstStoreByCourier() +
                                        " " + response.body().getHandingTimeToFirstStoreByCourier() + "-kor a feladási " +
                                        "megyei raktárba";
                                arrivedInFirstStoreTv.setText(message);

                            }
                            //Csomag elindult az átvételi feladási raktárból
                            if(response.body().getMessage() == null &&
                                    response.body().getPickingUpDateFromSecondStoreByCourier() != null){
                                startedFromSecondStoreCv.setVisibility(View.VISIBLE);
                                String message = "Csomag elindult " + response.body().getPickingUpDateFromSecondStoreByCourier() +
                                        " " + response.body().getPickingUpTimeFromSecondStoreByCourier() + "-kor " +
                                        "az érkezési megyei raktárból";
                                startedFromSecondStoreTv.setText(message);

                            }

                            //Csomag leszállítva
                            if(response.body().getMessage() == null && response.body().getShippingDate() != null){
                                isShippedCv.setVisibility(View.VISIBLE);
                                String message = "Csomag leszállítva " + response.body().getShippingDate()
                                        + " " + response.body().getShippingTime() + "-kor" + "\n"
                                        + "Érkezési automata: " + response.body().getShippingToPostCode()
                                        + " " + response.body().getShippingToCity()
                                        + " " + response.body().getShippingToStreet() + "\n"
                                        + "Eddig tudod átvenni: " + response.body().getPickingUpExpirationDate()
                                        + " " + response.body().getPickingUpExpirationTime();
                                isShippedTv.setText(message);

                            }

                            //Csomag átvételi ideje lejárt
                            if(response.body().getMessage() == null && response.body().isPickingUpExpired()){
                                isExpiredCv.setVisibility(View.VISIBLE);
                                String message = "A csomagot a futár visszaszállította az átvételi megyei raktárba, " +
                                        "mert lejárt az átvételi ideje. Ha szeretnéd újraindítani a csomagot az " +
                                        "átvételi automatához, akkor hívd fel az ügyfélszolgálatot.";
                                isExpiredTv.setText(message);
                            }

                            //Csomag átvéve
                            if(response.body().getMessage() == null && response.body().getPickingUpDate() != null){
                                isPickedUpCv.setVisibility(View.VISIBLE);
                                String message = "Csomag átvéve " + response.body().getPickingUpDate()
                                        + " " + response.body().getPickingUpTime() + "-kor" + "\n"
                                        + "Érkezési automata: " + response.body().getShippingToPostCode()
                                        + " " + response.body().getShippingToCity()
                                        + " " + response.body().getShippingToStreet();
                                isPickedUpTv.setText(message);

                            }

                        }

                        @Override
                        public void onFailure(Call<FollowParcelResponse> call, Throwable t) {

                        }
                    });
                }

            }
        });

        //Vissza
        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FollowParcelActivity.this, MainActivity.class));
            }
        });

    }

    //Adatok validációja
    private boolean validateData(String uniqueParcelId){

        if(uniqueParcelId.isEmpty()){
            followUniqueParcelIdEt.setError("Add meg a csomagazonosítót");
            followUniqueParcelIdEt.requestFocus();
            return false;
        }

        return true;

    }


}