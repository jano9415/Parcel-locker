package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.response.FollowParcelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowParcelActivity extends AppCompatActivity {

    private Context context;

    private EditText followUniqueParcelIdEt;

    private TextView notFoundTv, isPlacedTv, isSentTv, isShippedTv, isPickedUpTv;

    private Button followUniqueParcelIdBtn;

    private CardView notFoundCv, isPlacedCv, isSentCv, isShippedCv, isPickedUpCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_parcel);

        context = this;
        followUniqueParcelIdEt = findViewById(R.id.followUniqueParcelIdEt);
        followUniqueParcelIdBtn = findViewById(R.id.followUniqueParcelIdBtn);
        notFoundCv = findViewById(R.id.notFoundCv);
        isPlacedCv = findViewById(R.id.isPlacedCv);
        isSentCv = findViewById(R.id.isSentCv);
        isShippedCv = findViewById(R.id.isShippedCv);
        isPickedUpCv = findViewById(R.id.isPickedUpCv);

        notFoundTv = findViewById(R.id.notFoundTv);
        isPlacedTv = findViewById(R.id.isPlacedTv);
        isSentTv = findViewById(R.id.isSentTv);
        isShippedTv = findViewById(R.id.isShippedTv);
        isPickedUpTv = findViewById(R.id.isPickedUpTv);

        notFoundCv.setVisibility(View.INVISIBLE);
        isPlacedCv.setVisibility(View.INVISIBLE);
        isSentCv.setVisibility(View.INVISIBLE);
        isShippedCv.setVisibility(View.INVISIBLE);
        isPickedUpCv.setVisibility(View.INVISIBLE);



        //Csomag követése
        followUniqueParcelIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notFoundCv.setVisibility(View.INVISIBLE);
                isPlacedCv.setVisibility(View.INVISIBLE);
                isSentCv.setVisibility(View.INVISIBLE);
                isShippedCv.setVisibility(View.INVISIBLE);
                isPickedUpCv.setVisibility(View.INVISIBLE);
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
                                isShippedTv.setText("Leszállítva");

                            }

                            //Csomag átvéve
                            if(response.body().getMessage() == null && response.body().getPickingUpDate() != null){
                                isPickedUpCv.setVisibility(View.VISIBLE);
                                String message = "Csomag átvéve " + response.body().getPickingUpDate()
                                        + " " + response.body().getPickingUpTime() + "-kor" + "\n"
                                        + "Érkezési automata: " + response.body().getShippingToPostCode()
                                        + " " + response.body().getShippingToCity()
                                        + " " + response.body().getShippingToStreet();
                                isPickedUpTv.setText("Átvéve");

                            }

                        }

                        @Override
                        public void onFailure(Call<FollowParcelResponse> call, Throwable t) {

                        }
                    });
                }

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