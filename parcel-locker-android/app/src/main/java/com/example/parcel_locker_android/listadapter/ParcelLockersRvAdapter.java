package com.example.parcel_locker_android.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parcel_locker_android.R;
import com.example.parcel_locker_android.payload.response.GetParcelLockersResponse;

import java.util.List;

public class ParcelLockersRvAdapter extends RecyclerView.Adapter<ParcelLockersRvAdapter.ViewHolder> {

    private List<GetParcelLockersResponse> parcelLockerList;

    public ParcelLockersRvAdapter(List<GetParcelLockersResponse> parcelLockerList) {
        this.parcelLockerList = parcelLockerList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView postCodeTextView;

        public TextView cityTextView;
        public TextView streetTextView;


        public ViewHolder(View view) {
            super(view);
            postCodeTextView = view.findViewById(R.id.item_post_code);
            cityTextView = view.findViewById(R.id.item_city);
            streetTextView = view.findViewById(R.id.item_street);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcel_locker_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetParcelLockersResponse parcelLocker = parcelLockerList.get(position);

        holder.postCodeTextView.setText(parcelLocker.getPostCode() + "");
        holder.cityTextView.setText(parcelLocker.getCity());
        holder.streetTextView.setText(parcelLocker.getStreet());
    }

    @Override
    public int getItemCount() {
        return parcelLockerList.size();
    }
}

