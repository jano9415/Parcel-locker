package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcel_locker_android.MainActivity;
import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.request.SignUpRequest;
import com.example.parcel_locker_android.payload.response.StringResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Context context;

    private EditText signUpEmailAddressEt, signUpPasswordEt, signUpPasswordAgainEt,
            signUpLastNameEt, singUpFirstNameEt, signUpPhoneNumberEt;

    private Button signUpBtn;

    private TextView backBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = this;
        signUpEmailAddressEt = findViewById(R.id.signUpEmailAddressEt);
        signUpPasswordEt = findViewById(R.id.signUpPasswordEt);
        signUpPasswordAgainEt = findViewById(R.id.signUpPasswordAgainEt);
        signUpLastNameEt = findViewById(R.id.signUpLastNameEt);
        singUpFirstNameEt = findViewById(R.id.singUpFirstNameEt);
        signUpPhoneNumberEt = findViewById(R.id.signUpPhoneNumberEt);

        signUpBtn = findViewById(R.id.signUpBtn);
        backBtn2 = findViewById(R.id.backBtn2);

        //Regisztráció
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = signUpEmailAddressEt.getText().toString().trim();
                String password = signUpPasswordEt.getText().toString().trim();
                String passwordAgain = signUpPasswordAgainEt.getText().toString().trim();
                String lastName = signUpLastNameEt.getText().toString().trim();
                String firstName = singUpFirstNameEt.getText().toString().trim();
                String phoneNumber = signUpPhoneNumberEt.getText().toString().trim();

                if(validateDatas(emailAddress,password,passwordAgain,lastName,
                        firstName,phoneNumber)){

                    SignUpRequest request = new SignUpRequest(emailAddress,password, firstName,
                            lastName, phoneNumber);

                    Call<StringResponse> call = ApiConfig.getInstance().authService()
                            .signUp(request);

                    call.enqueue(new Callback<StringResponse>() {
                        @Override
                        public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {

                            if (response.body().getMessage().equals("emailExists")) {
                                Toast.makeText(SignUpActivity.this ,
                                        "Ez az email cím már regisztrálva van. Adj meg egy másikat",
                                        Toast.LENGTH_LONG).show();
                            }
                            if(response.body().getMessage().equals("successRegistration")) {
                                Toast.makeText(SignUpActivity.this ,
                                        "Sikeres regisztráció",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });
    }

    //Adatok validációja
    private boolean validateDatas(String emailAddress, String password, String passwordAgain,
                                  String lastName, String firstName, String phoneNumber){

        if(emailAddress.isEmpty()){
            signUpEmailAddressEt.setError("Add meg az email címed");
            signUpEmailAddressEt.requestFocus();
            return false;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(emailAddress);

        if(!matcher.matches()){
            signUpEmailAddressEt.setError("Helytelen email formátum");
            signUpEmailAddressEt.requestFocus();
            return false;
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        if(password.isEmpty()){
            signUpPasswordEt.setError("Add meg a jelszót");
            signUpPasswordEt.requestFocus();
            return false;
        }
        if(!password.matches(passwordPattern)){
            signUpPasswordEt.setError("Túl gyenge jelszó. Minimum 8 karakter, kisbetű, nagybetű és speciális karakter szükséges");
            signUpPasswordEt.requestFocus();
            return false;
        }
        if(passwordAgain.isEmpty()){
            signUpPasswordAgainEt.setError("Add meg a jelszót újra");
            signUpPasswordAgainEt.requestFocus();
            return false;
        }
        if(!passwordAgain.equals(password)){
            signUpPasswordAgainEt.setError("A két jelszó nem egyezik meg");
            signUpPasswordAgainEt.requestFocus();
            return false;
        }
        if(lastName.isEmpty()){
            signUpLastNameEt.setError("Add meg a vezetékneved");
            signUpLastNameEt.requestFocus();
            return false;
        }
        if(firstName.isEmpty()){
            singUpFirstNameEt.setError("Add meg a keresztneved");
            singUpFirstNameEt.requestFocus();
            return false;
        }
        if(phoneNumber.isEmpty()){
            signUpPhoneNumberEt.setError("Add meg a telefonszámod");
            signUpPhoneNumberEt.requestFocus();
            return false;
        }


        return true;
    }
}