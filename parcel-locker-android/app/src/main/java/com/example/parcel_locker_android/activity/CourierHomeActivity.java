package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parcel_locker_android.R;

public class CourierHomeActivity extends AppCompatActivity {

    private Button handParcelToStoreActivityBtn, pickUpParcelFromStoreActivityBtn;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_home);
        context = this;

        handParcelToStoreActivityBtn = findViewById(R.id.handParcelToStoreActivityBtn);
        pickUpParcelFromStoreActivityBtn = findViewById(R.id.pickUpParcelFromStoreActivityBtn);

        //Csomagleadás raktárban
        handParcelToStoreActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourierHomeActivity.this, HandParcelToStoreActivity.class));

            }
        });

        //Csomagfelvétel raktárból
        pickUpParcelFromStoreActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourierHomeActivity.this, PickUpParcelFromStoreActivity.class));

            }
        });
    }
}