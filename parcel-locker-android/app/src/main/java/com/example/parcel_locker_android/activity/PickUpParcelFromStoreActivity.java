package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.parcel_locker_android.R;

public class PickUpParcelFromStoreActivity extends AppCompatActivity {

    private Context context;

    private EditText pickUpUniqueParcelIdEt;

    private Button pickUpParcelFromStoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_parcel_from_store);

        context = this;
        pickUpUniqueParcelIdEt = findViewById(R.id.pickUpUniqueParcelIdEt);
        pickUpParcelFromStoreBtn = findViewById(R.id.pickUpParcelFromStoreBtn);

        //Csomag felvétele a raktárból
        pickUpParcelFromStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueParcelId = pickUpUniqueParcelIdEt.getText().toString().trim();

                if(validateData(uniqueParcelId)){
                    Log.d("valami", uniqueParcelId);

                }

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

