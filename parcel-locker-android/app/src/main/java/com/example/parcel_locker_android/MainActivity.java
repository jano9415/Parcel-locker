package com.example.parcel_locker_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginActivity, signUpActivity, followParcelActivity, parcelLockersActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginActivity = findViewById(R.id.loginActivity);
        signUpActivity = findViewById(R.id.signUpActivity);
        followParcelActivity = findViewById(R.id.followParcelActivity);
        parcelLockersActivity = findViewById(R.id.parcelLockersActivity);

        //Bejelentkezési oldal
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("valami", "valamiszöveg");
            }
        });
        //Regisztrációs oldal
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //Csomag nyomon követése oldal
        followParcelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //Csomagautomaták oldal
        parcelLockersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}