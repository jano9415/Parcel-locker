package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.parcel_locker_android.MainActivity;
import com.example.parcel_locker_android.R;

public class UserHomeActivity extends AppCompatActivity {

    private Button userLogOutBtn;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        context = this;
        userLogOutBtn = findViewById(R.id.userLogOutBtn);

        //Kijelentkezés
        userLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //User törlése a shared preferences-ben
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("currentUser");
                editor.apply();

                //Átirányítás a főoldalra
                startActivity(new Intent(UserHomeActivity.this, MainActivity.class));
            }
        });
    }
}