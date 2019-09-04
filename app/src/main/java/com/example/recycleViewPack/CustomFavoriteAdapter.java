package com.example.recycleViewPack;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripair.R;

import java.util.ArrayList;

public class CustomFavoriteAdapter extends RecyclerView.Adapter<CustomFavoriteAdapter.MyViewHolder>{
    private ArrayList<ContactPOJO> arrayList = new ArrayList<>();
    private OnRecyclerClickListener listener;

    public CustomFavoriteAdapter(ArrayList<ContactPOJO> arrayList, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public CustomFavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list, parent, false);

        return new CustomFavoriteAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(CustomFavoriteAdapter.MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        ContactPOJO contact = arrayList.get(position);
        holder.name.setText(contact.getmName());
        holder.date.setText(contact.getmDateDest());
        holder.smoke.setText(contact.getmSmoking());
        holder.leftDate.setText(contact.getmDateLeft());
        holder.age.setText(String.valueOf(contact.getmAge()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(position, view.getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, smoke, age, leftDate;
        ImageButton btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder", "in View Holder");
            name = itemView.findViewById(R.id.txt_name);
            date = itemView.findViewById(R.id.txt_dest_insert);
            leftDate = itemView.findViewById(R.id.txt_dateL);
            smoke = itemView.findViewById(R.id.txt_smoke_insert);
            age = itemView.findViewById(R.id.txt_age_insert);
            btnDelete = itemView.findViewById(R.id.btn_deleteFav);

        }
    }
}
