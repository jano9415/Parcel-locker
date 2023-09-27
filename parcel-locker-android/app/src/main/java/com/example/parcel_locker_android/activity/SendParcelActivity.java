package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.request.SendParcelWithCodeFromWebpageRequest;
import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;
import com.example.parcel_locker_android.payload.response.StringResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendParcelActivity extends AppCompatActivity {

    private Context context;

    private Spinner parcelLockerFromSpinner, parcelLockerToSpinner;

    private RadioGroup parcelSizeRg;

    private RadioButton smallSizeRb, mediumSizeRb, largeSizeRb;

    private EditText priceEt, receiverNameEt, receiverEmailAddressEt, receiverPhoneNumberEt;

    private TextView backBtn5;

    private Button sendParcelBtn;

    private List<GetParcelLockersResponse> parcelLockers;

    private Long parcelLockerFromId, parcelLockerToId;

    private String parcelSize = "";

    private boolean senderParcelLockerFull, smallBoxesFull, mediumBoxesFull, largeBoxesFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_parcel);

        context = this;

        parcelLockerFromSpinner = findViewById(R.id.parcelLockerFromSpinner);
        parcelLockerToSpinner = findViewById(R.id.parcelLockerToSpinner);

        parcelSizeRg = findViewById(R.id.parcelSizeRg);
        smallSizeRb = findViewById(R.id.smallSizeRb);
        mediumSizeRb = findViewById(R.id.mediumSizeRb);
        largeSizeRb = findViewById(R.id.largeSizeRb);
        priceEt = findViewById(R.id.priceEt);
        backBtn5 = findViewById(R.id.backBtn5);

        //Az ár alapértéke 0
        priceEt.setText("0");
        receiverNameEt = findViewById(R.id.receiverNameEt);
        receiverEmailAddressEt = findViewById(R.id.receiverEmailAddressEt);
        receiverPhoneNumberEt = findViewById(R.id.receiverPhoneNumberEt);
        sendParcelBtn = findViewById(R.id.sendParcelBtn);

        senderParcelLockerFull = false;
        smallBoxesFull = false;
        mediumBoxesFull = false;
        largeBoxesFull = false;

        //Automaták lekérése a select tag számára
        Call<List<GetParcelLockersResponse>> call = ApiConfig.getInstance().parcelLockerService()
                .getParcelLockersForChoice();

        call.enqueue(new Callback<List<GetParcelLockersResponse>>() {
            @Override
            public void onResponse(Call<List<GetParcelLockersResponse>> call, Response<List<GetParcelLockersResponse>> response) {

                parcelLockers = response.body();

                //Adapter létrehozása a kettő spinner-hez
                ArrayAdapter<GetParcelLockersResponse> adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_item,parcelLockers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                parcelLockerFromSpinner.setAdapter(adapter);
                parcelLockerToSpinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<GetParcelLockersResponse>> call, Throwable t) {

            }
        });

        //Elem kiválasztása a parcelLockerFromSpinner-ből
        parcelLockerFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GetParcelLockersResponse parcelLocker = (GetParcelLockersResponse) adapterView.getItemAtPosition(i);
                parcelLockerFromId = parcelLocker.getId();

                //Ellenőrzöm, hogy a feladási automata tele van-e
                Call<StringResponse> call1 = ApiConfig.getInstance().parcelLockerService()
                        .isParcelLockerFull(parcelLockerFromId);

                call1.enqueue(new Callback<StringResponse>() {
                    @Override
                    public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {
                        //Ha tele van, nem látszik a küldés gomb
                        if (response.body().getMessage().equals("full")) {
                            senderParcelLockerFull = true;
                            sendParcelBtn.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "Ez a feladási automata jelenleg tele van. Nem tudsz csomagot feladni",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (response.body().getMessage().equals("notfull")) {
                            senderParcelLockerFull = false;
                            sendParcelBtn.setVisibility(View.VISIBLE);

                        }

                        //Ha az automata nincs tele, akkor ellenőrzöm a kis, közepes és nagy rekeszek telítettségét.
                        if(senderParcelLockerFull == false){

                            Call<List<StringResponse>> call2 = ApiConfig.getInstance().parcelLockerService()
                                    .areBoxesFull(parcelLockerFromId);

                            call2.enqueue(new Callback<List<StringResponse>>() {
                                @Override
                                public void onResponse(Call<List<StringResponse>> call, Response<List<StringResponse>> response) {
                                    //Kicsi rekeszek
                                    if (response.body().get(0).getMessage().equals("full")) {
                                        smallBoxesFull = true;
                                        smallSizeRb.setEnabled(false);
                                    }
                                    if (response.body().get(0).getMessage().equals("notfull"))  {
                                        smallBoxesFull = false;
                                        smallSizeRb.setEnabled(true);
                                    }
                                    //Közepes rekeszek
                                    if (response.body().get(1).getMessage().equals("full")) {
                                        mediumBoxesFull = true;
                                        mediumSizeRb.setEnabled(false);
                                    }
                                    if (response.body().get(1).getMessage().equals("notfull"))  {
                                        mediumBoxesFull = false;
                                        mediumSizeRb.setEnabled(true);
                                    }
                                    //Nagy rekeszek
                                    if (response.body().get(2).getMessage().equals("full")) {
                                        largeBoxesFull = true;
                                        largeSizeRb.setEnabled(false);
                                    }
                                    if (response.body().get(2).getMessage().equals("notfull"))  {
                                        largeBoxesFull = false;
                                        largeSizeRb.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<StringResponse>> call, Throwable t) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<StringResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Elem kiválasztása a parcelLockerToSpinner-ből
        parcelLockerToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GetParcelLockersResponse parcelLocker = (GetParcelLockersResponse) adapterView.getItemAtPosition(i);
                parcelLockerToId = parcelLocker.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Radio gombok kezelése
        //Méret kiválasztása
        parcelSizeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                if(radioButton != null){
                    if(radioButton.getText().toString().equals("Kicsi")){
                        parcelSize = "small";
                    }
                    if(radioButton.getText().toString().equals("Közepes")){
                        parcelSize = "medium";
                    }
                    if(radioButton.getText().toString().equals("Nagy")){
                        parcelSize = "large";
                    }
                }
            }
        });

        //Csomag küldése
        sendParcelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String price = priceEt.getText().toString().trim();
                String receiverName = receiverNameEt.getText().toString().trim();
                String receiverEmailAddress = receiverEmailAddressEt.getText().toString().trim();
                String receiverPhoneNumber = receiverPhoneNumberEt.getText().toString().trim();

                if(validateDatas(parcelLockerFromId, parcelLockerToId, parcelSize,
                        price, receiverName, receiverEmailAddress, receiverPhoneNumber)){

                    //Kérés összeállítása
                    SendParcelWithCodeFromWebpageRequest request = new SendParcelWithCodeFromWebpageRequest();
                    request.setParcelLockerFromId(parcelLockerFromId);
                    request.setParcelLockerToId(parcelLockerToId);
                    request.setSize(parcelSize);
                    request.setPrice(Integer.parseInt(price));
                    request.setReceiverName(receiverName);
                    request.setReceiverEmailAddress(receiverEmailAddress);
                    request.setReceiverPhoneNumber(receiverPhoneNumber);
                    request.setSenderEmailAddress(CurrentUser.getCurrentUser(context).getEmailAddress());

                    //Kérés küldése
                    Call<StringResponse> call1 = ApiConfig.getInstance().parcelService()
                            .sendParcelWithCodeFromWebpage(request,CurrentUser.getCurrentUser(context).getToken());

                    call1.enqueue(new Callback<StringResponse>() {
                        @Override
                        public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {
                            if(response.body().getMessage().equals("successSending")){
                                startActivity(new Intent(SendParcelActivity.this, SendParcelActivity.class));
                                Toast.makeText(context, "Sikeres előzetes csomagfeladás. A feladási kódodat megtalálod az email értesítőben" +
                                        " és a csomagjaim meüpontban.", Toast.LENGTH_LONG).show();
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
        backBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendParcelActivity.this, UserHomeActivity.class));
            }
        });

    }

    //Adatok validációja
    private boolean validateDatas(Long parcelLockerFromId, Long parcelLockerToId, String parcelSize,
                                  String price, String receiverName, String receiverEmailAddress, String receiverPhoneNumber){

        if(parcelLockerFromId == parcelLockerToId){
            Toast.makeText(context, "A feladási és érkezési automata nem lehet azonos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(parcelSize.isEmpty()){
            Toast.makeText(context, "Válassz csomagméretet", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(receiverName.isEmpty()){
            receiverNameEt.setError("Add meg az átvevő nevét");
            receiverNameEt.requestFocus();
            return false;
        }
        if(receiverEmailAddress.isEmpty()){
            receiverEmailAddressEt.setError("Add meg az átvevő email címét");
            receiverEmailAddressEt.requestFocus();
            return false;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(receiverEmailAddress);

        if(!matcher.matches()){
            receiverEmailAddressEt.setError("Helytelen email formátum");
            receiverEmailAddressEt.requestFocus();
            return false;
        }

        if(receiverPhoneNumber.isEmpty()){
            receiverPhoneNumberEt.setError("Add meg az átvevő telefonszámát");
            receiverPhoneNumberEt.requestFocus();
            return false;
        }
        return true;

    }
}