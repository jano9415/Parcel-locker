package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.listadapter.MyParcelsRvAdapter;
import com.example.parcel_locker_android.payload.CurrentUser;
import com.example.parcel_locker_android.payload.ParcelDTO;
import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyParcelsActivity extends AppCompatActivity {

    private Spinner requestTypeSpinner;

    private Context context;

    private String[] requestTypes = {"reserved", "notPickedUp", "pickedUp"};
    private String[] requestTexts = {"Online feladott, még nem elhelyezett csomagok",
            "Szállítás alatti csomagok", "Lezárt, átvett csomagok"};

    private String selectedRequest;

    private RecyclerView myParcelsRv;

    private MyParcelsRvAdapter myParcelsRvAdapter;

    private List<ParcelDTO> parcels = new ArrayList<>();

    private TextView backBtn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_parcels);
        context = this;

        requestTypeSpinner = findViewById(R.id.requestTypeSpinner);
        myParcelsRv = findViewById(R.id.myParcelsRv);
        backBtn9 = findViewById(R.id.backBtn9);

        myParcelsRv.setLayoutManager(new LinearLayoutManager(this));

        //Adapter a spinner-hez
        final ArrayAdapter<String>[] adapter = new ArrayAdapter[]{new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, requestTexts)};
        adapter[0].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestTypeSpinner.setAdapter(adapter[0]);

        //Elem kiválasztása a spinner-ből
        requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRequest = requestTypes[i];

                //Kérés küldése
                Call<List<ParcelDTO>> call = ApiConfig.getInstance().parcelService()
                        .getParcelsOfUser(CurrentUser.getCurrentUser(context).getEmailAddress(), selectedRequest,
                                CurrentUser.getCurrentUser(context).getToken());

                call.enqueue(new Callback<List<ParcelDTO>>() {
                    @Override
                    public void onResponse(Call<List<ParcelDTO>> call, Response<List<ParcelDTO>> response) {
                        parcels = response.body();
                        myParcelsRvAdapter = new MyParcelsRvAdapter(parcels);
                        myParcelsRv.setAdapter(myParcelsRvAdapter);

                        //Kattintási esemény a lista egyik elemére
                        myParcelsRvAdapter.setOnItemClickListener(new MyParcelsRvAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(ParcelDTO parcel) {

                                //Csomagadatok modal
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setTitle("Hol tart a csomagom?")
                                        .setMessage(
                                                "A csomagot a webes vagy mobilos alkalmazásból már feladtad, " +
                                                        "de az automatában még nem helyezted el.\n" +
                                                "Itt tudod feladni: " + parcel.getShippingFromPostCode() + " " +
                                                        parcel.getShippingFromCity() + " " + parcel.getShippingFromStreet() + "\n"

                                        );

                                        if(selectedRequest.equals("reserved")){
                                            builder.setPositiveButton("Törlés", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    startActivity(new Intent(context, MyParcelsActivity.class));

                                                }
                                            });
                                        }

                                        builder.show();
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<ParcelDTO>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Vissza gomb
        backBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UserHomeActivity.class));
            }
        });
    }
}