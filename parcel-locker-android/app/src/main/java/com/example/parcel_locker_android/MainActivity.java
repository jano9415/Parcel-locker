package com.example.parcel_locker_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.parcel_locker_android.activity.FollowParcelActivity;
import com.example.parcel_locker_android.activity.LoginActivity;
import com.example.parcel_locker_android.activity.ParcelLockersActivity;
import com.example.parcel_locker_android.activity.SignUpActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;

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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //Regisztrációs oldal
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));

            }
        });
        //Csomag nyomon követése oldal
        followParcelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FollowParcelActivity.class));

            }
        });
        //Csomagautomaták oldal
        parcelLockersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ParcelLockersActivity.class));

            }
        });


    }



}