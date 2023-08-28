package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.response.StringResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpParcelFromStoreActivity extends AppCompatActivity {

    private Context context;

    private EditText pickUpUniqueParcelIdEt;

    private TextView backBtn7;

    private Button pickUpParcelFromStoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_parcel_from_store);

        context = this;
        pickUpUniqueParcelIdEt = findViewById(R.id.pickUpUniqueParcelIdEt);
        pickUpParcelFromStoreBtn = findViewById(R.id.pickUpParcelFromStoreBtn);
        backBtn7 = findViewById(R.id.backBtn7);

        //Csomag felvétele a raktárból
        pickUpParcelFromStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueParcelId = pickUpUniqueParcelIdEt.getText().toString().trim();

                if(validateData(uniqueParcelId)){
                    Call<StringResponse> call = ApiConfig.getInstance().parcelService()
                            .pickUpParcelFromStore(
                                    CurrentUser.getCurrentUser(context).getEmailAddress(),
                                    uniqueParcelId,
                                    CurrentUser.getCurrentUser(context).getToken()
                            );

                    call.enqueue(new Callback<StringResponse>() {
                        @Override
                        public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {

                            if(response.body().getMessage().equals("notFound")){
                                Toast.makeText(PickUpParcelFromStoreActivity.this ,
                                        "Csomag nem található",
                                        Toast.LENGTH_LONG).show();
                            }
                            if(response.body().getMessage().equals("successPickUp")){
                                Toast.makeText(PickUpParcelFromStoreActivity.this ,
                                        "Sikeresen felvetted a csomagot",
                                        Toast.LENGTH_LONG).show();
                                pickUpUniqueParcelIdEt.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<StringResponse> call, Throwable t) {

                        }
                    });

                }

            }
        });

        //Vissza
        backBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickUpParcelFromStoreActivity.this, CourierHomeActivity.class));
            }
        });

    }

    //Adatok validációja
    private boolean validateData(String uniqueParcelId){

        if(uniqueParcelId.isEmpty()){
            pickUpUniqueParcelIdEt.setError("Add meg a csomagazonosítót");
            pickUpUniqueParcelIdEt.requestFocus();
            return false;
        }

        return true;

    }
}

