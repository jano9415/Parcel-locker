package com.example.parcel_locker_android.activity;

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

import com.example.parcel_locker_android.MainActivity;
import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.request.LoginRequest;
import com.example.parcel_locker_android.payload.response.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailAddressEt, loginPasswordEt;
    private Button loginButton, futar001Btn, futar002Btn, jano9415Btn;

    private TextView backButton1;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        loginEmailAddressEt = findViewById(R.id.loginEmailAddressEt);
        loginPasswordEt = findViewById(R.id.loginPasswordEt);
        loginButton = findViewById(R.id.loginButton);
        backButton1 = findViewById(R.id.backButton1);

        futar001Btn = findViewById(R.id.futar001Btn);
        futar002Btn = findViewById(R.id.futar002Btn);
        jano9415Btn = findViewById(R.id.jano9415Btn);

        LoginRequest loginRequest = new LoginRequest();


        //Bejelentkezés
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = loginEmailAddressEt.getText().toString();
                String password = loginPasswordEt.getText().toString();

                if(validateDatas(emailAddress, password)){

                    loginRequest.setEmailAddress(emailAddress);
                    loginRequest.setPassword(password);

                    Call<LoginResponse> call = ApiConfig.getInstance().authService()
                            .login(loginRequest);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if (response.body().getMessage() != null && response.body().getMessage().equals("emailError")) {
                                Toast.makeText(LoginActivity.this ,
                                        "Hibás email cím",
                                        Toast.LENGTH_LONG).show();

                            }
                            if (response.body().getMessage() != null && response.body().getMessage().equals("passwordError")) {
                                Toast.makeText(LoginActivity.this ,
                                        "Hibás jelszó",
                                        Toast.LENGTH_LONG).show();

                            }
                            if (response.body().getMessage() != null && response.body().getMessage().equals("notActivated")) {
                                Toast.makeText(LoginActivity.this ,
                                        "Még nem aktiváltad a felhasználói fiókodat",
                                        Toast.LENGTH_LONG).show();

                            }
                            //Ha a válasz egy login response jwt tokennel
                            //Rfid uid: 07 6205 26
                            if (response.body().getEmailAddress() != null) {
                                //Ha user
                                if(response.body().getRoles().contains("user")){
                                    saveObjectInSharedPreferences(response.body());
                                    startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));

                                }
                                //Ha futár
                                if(response.body().getRoles().contains("courier")){
                                    saveObjectInSharedPreferences(response.body());
                                    startActivity(new Intent(LoginActivity.this, CourierHomeActivity.class));
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });

                }

            }
        });

        //Vissza
        backButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        futar001Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmailAddressEt.setText("futar001");
                loginPasswordEt.setText("07 6205 26");

            }
        });

        futar002Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmailAddressEt.setText("futar002");
                loginPasswordEt.setText(" A6 40 65 ");

            }
        });

        jano9415Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmailAddressEt.setText("jano9415@gmail.com");
                loginPasswordEt.setText("Password1!");

            }
        });




    }

    //Adatok validációja
    private boolean validateDatas(String emailAddress, String password){

        if(emailAddress.isEmpty()){
            loginEmailAddressEt.setError("Add meg az email címed");
            loginEmailAddressEt.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            loginPasswordEt.setError("Add meg a jelszót");
            loginPasswordEt.requestFocus();
            return false;
        }

        return true;
    }

    //Objektum konvertálás JSON formátumba
    private String objectToJson(LoginResponse loginResponse){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", loginResponse.getToken());
            jsonObject.put("tokenType", loginResponse.getTokenType());
            jsonObject.put("userId", loginResponse.getUserId());
            jsonObject.put("emailAddress", loginResponse.getEmailAddress());
            jsonObject.put("roles", loginResponse.getRoles());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Objektum mentése shared preferences-be
    private void saveObjectInSharedPreferences(LoginResponse loginResponse){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser", objectToJson(loginResponse));
        editor.apply();
    }
}
