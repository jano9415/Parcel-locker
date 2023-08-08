package com.example.parcel_locker_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.listadapter.ParcelLockersRvAdapter;
import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;

import java.util.ArrayList;
import java.util.List;

public class ParcelLockersActivity extends AppCompatActivity {

    private Context context;

    private RecyclerView parcelLockersRv;

    private ParcelLockersRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_lockers);

        context = this;
        parcelLockersRv = findViewById(R.id.parcelLockersRv);

        parcelLockersRv.setLayoutManager(new LinearLayoutManager(this));

        GetParcelLockersResponse pelda1 = new GetParcelLockersResponse();
        pelda1.setPostCode(8100);
        pelda1.setCity("Várpalota");
        pelda1.setStreet("Körmöcbánya utca 9");

        List<GetParcelLockersResponse> parcelLockers = new ArrayList<>();
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);
        parcelLockers.add(pelda1);

        adapter = new ParcelLockersRvAdapter(parcelLockers);
        parcelLockersRv.setAdapter(adapter);

    }
}