package com.example.parcel_locker_android.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.payload.ParcelDTO;


import java.util.List;

public class MyParcelsRvAdapter extends RecyclerView.Adapter<MyParcelsRvAdapter.ViewHolder> {

    private List<ParcelDTO> parcels;

    public MyParcelsRvAdapter(List<ParcelDTO> parcels) {
        this.parcels = parcels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView myParcelUniqueParcelId;

        //Szövegmezők, amik a megjelenítőn vannak elhelyezve
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myParcelUniqueParcelId = itemView.findViewById(R.id.myParcelUniqueParcelId);
        }
    }


    //Adapter és megjelenítő összekapcsolása
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_parcel_rv_item, parent, false);
        return new ViewHolder(view);
    }

    //Kattintási eseményhez változók
    public interface OnItemClickListener {
        void onItemClick(ParcelDTO parcel);
    }

    private MyParcelsRvAdapter.OnItemClickListener itemClickListener;

    public void setOnItemClickListener(MyParcelsRvAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    //Megjelenítőn található szövegmezők feltöltése az objektum lista változóival
    //Kattintási esemény az adott objektumra
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ParcelDTO parcel = parcels.get(position);

        holder.myParcelUniqueParcelId.setText(parcel.getUniqueParcelId());

        //Kattintási esemény
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(parcel);
                }

            }
        });

    }



    //Elemek megszámlálása
    @Override
    public int getItemCount() {
        return parcels.size();
    }
}


