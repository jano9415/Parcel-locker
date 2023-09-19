package com.example.parcel_locker_android.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.parcel_locker_android.qrreading.Capture;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandParcelToStoreActivity extends AppCompatActivity {

    private Context context;

    private EditText handUniqueParcelIdEt;

    private TextView backBtn6;

    private Button handParcelToStoreBtn, readQRBtnHPActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_parcel_to_store);

        context = this;
        handParcelToStoreBtn = findViewById(R.id.handParcelToStoreBtn);
        handUniqueParcelIdEt = findViewById(R.id.handUniqueParcelIdEt);
        readQRBtnHPActivity = findViewById(R.id.readQRBtnHPActivity);
        backBtn6 = findViewById(R.id.backBtn6);

        //Barkód olvasás
        readQRBtnHPActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setPrompt("Barkód olvasás");
                scanOptions.setBeepEnabled(true);
                scanOptions.setOrientationLocked(true);
                scanOptions.setCaptureActivity(Capture.class);
                barCodeLauncher.launch(scanOptions);

            }
        });

        //Csomag leadása a raktárba
        handParcelToStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueParcelId = handUniqueParcelIdEt.getText().toString().trim();

                if(validateData(uniqueParcelId)){

                    Call<StringResponse> call = ApiConfig.getInstance().parcelService()
                            .handParcelToStore(
                                    CurrentUser.getCurrentUser(context).getEmailAddress(),
                                    uniqueParcelId,
                                    CurrentUser.getCurrentUser(context).getToken());

                    call.enqueue(new Callback<StringResponse>() {
                        @Override
                        public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {

                            if(response.body().getMessage().equals("notFound")){
                                Toast.makeText(HandParcelToStoreActivity.this ,
                                        "Csomag nem található",
                                        Toast.LENGTH_LONG).show();
                            }
                            if(response.body().getMessage().equals("successHand")){
                                Toast.makeText(HandParcelToStoreActivity.this ,
                                        "Sikeresen leadtad a csomagot",
                                        Toast.LENGTH_LONG).show();
                                handUniqueParcelIdEt.setText("");
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
        backBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HandParcelToStoreActivity.this, CourierHomeActivity.class));
            }
        });
    }

    //Adatok validációja
    private boolean validateData(String uniqueParcelId){

        if(uniqueParcelId.isEmpty()){
            handUniqueParcelIdEt.setError("Add meg a csomagazonosítót");
            handUniqueParcelIdEt.requestFocus();
            return false;
        }

        return true;

    }

    ActivityResultLauncher<ScanOptions> barCodeLauncher = registerForActivityResult(new ScanContract(),
            result ->{

                try {
                    if(result.getContents() == null) {
                        Toast.makeText(context, "Nem támogatott típusú kód", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Kód beolvasva: " + result.getContents(), Toast.LENGTH_LONG).show();
                        handUniqueParcelIdEt.setText(result.getContents());
                    }
                }catch (Exception exception){

                }


            });
}