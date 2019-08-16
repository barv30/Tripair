package com.example.recycleViewPack;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tripair.R;

import java.util.*;

public class CustomContactAdapter extends RecyclerView.Adapter<CustomContactAdapter.MyViewHolder> {

    private ArrayList<ContactPOJO> arrayList = new ArrayList<>();
    private OnRecyclerClickListener listener;

    public CustomContactAdapter(ArrayList<ContactPOJO> arrayList, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_list,parent,false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        ContactPOJO contact = arrayList.get(position);
        holder.name.setText(contact.getmName());
        holder.date.setText(contact.getmDateDest());
        holder.smoke.setText(contact.getmSmoking());
        holder.age.setText(contact.getmAge());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(position,view.getId());
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, smoke,age;
        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            name = itemView.findViewById(R.id.nameText);
            date = itemView.findViewById(R.id.txt_dest_insert);
            smoke = itemView.findViewById(R.id.txt_smoke_insert);
            age=itemView.findViewById(R.id.txt_age_insert);

        }


    }
}