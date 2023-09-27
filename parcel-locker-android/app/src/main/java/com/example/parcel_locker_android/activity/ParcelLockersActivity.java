package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.parcel_locker_android.MainActivity;
import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.config.ApiConfig;
import com.example.parcel_locker_android.listadapter.ParcelLockersRvAdapter;
import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;
import com.example.parcel_locker_android.payload.response.GetSaturationDatasResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParcelLockersActivity extends AppCompatActivity {

    private Context context;

    private RecyclerView parcelLockersRv;

    private ParcelLockersRvAdapter adapter;

    private TextView backBtn4;

    private List<GetParcelLockersResponse> parcelLockers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_lockers);

        context = this;
        parcelLockersRv = findViewById(R.id.parcelLockersRv);
        backBtn4 = findViewById(R.id.backBtn4);

        parcelLockersRv.setLayoutManager(new LinearLayoutManager(this));

        //Csomag automaták lekérése
        Call<List<GetParcelLockersResponse>> call = ApiConfig.getInstance().parcelLockerService()
                .getParcelLockersForChoice();

        call.enqueue(new Callback<List<GetParcelLockersResponse>>() {
            @Override
            public void onResponse(Call<List<GetParcelLockersResponse>> call, Response<List<GetParcelLockersResponse>> response) {
                parcelLockers = response.body();
                adapter = new ParcelLockersRvAdapter(parcelLockers);
                parcelLockersRv.setAdapter(adapter);

                //Kattintási esemény a lista egyik elemére
                adapter.setOnItemClickListener(new ParcelLockersRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(GetParcelLockersResponse parcelLocker) {

                        //Kiválasztott automata telítettségi adatok lekérdezése
                        Call<GetSaturationDatasResponse> call2 = ApiConfig.getInstance().parcelLockerService()
                                .getSaturationDatas(parcelLocker.getId());

                        call2.enqueue(new Callback<GetSaturationDatasResponse>() {
                            @Override
                            public void onResponse(Call<GetSaturationDatasResponse> call, Response<GetSaturationDatasResponse> response) {

                                //Telítettségi adatok modal
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Telítettségi adatok")
                                        .setMessage(
                                                "Szabad kicsi rekeszek száma: " +
                                                        (response.body().getAmountOfSmallBoxes() - response.body().getAmountOfFullSmallBoxes()) +
                                                        "\n" + "Szabad közepes rekeszek száma: " +
                                                        (response.body().getAmountOfMediumBoxes() - response.body().getAmountOfFullMediumBoxes()) +
                                                        "\n" + "Szabad nagy rekeszek száma: " +
                                                        (response.body().getAmountOfLargeBoxes() - response.body().getAmountOfFullLargeBoxes())
                                        )
                                        .show();
                            }

                            @Override
                            public void onFailure(Call<GetSaturationDatasResponse> call, Throwable t) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<GetParcelLockersResponse>> call, Throwable t) {
            }
        });

        //Vissza
        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParcelLockersActivity.this, MainActivity.class));
            }
        });



    }
}