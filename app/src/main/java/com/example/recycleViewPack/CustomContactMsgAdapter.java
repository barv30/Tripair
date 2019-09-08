package com.example.recycleViewPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripair.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CustomContactMsgAdapter extends RecyclerView.Adapter<CustomContactMsgAdapter.MyViewHolder> {
    private ArrayList<ContactPOJO> arrayList = new ArrayList<>();
    private OnRecyclerClickListener listener;

    public CustomContactMsgAdapter(ArrayList<ContactPOJO> arrayList, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_msg_list,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        ContactPOJO contact = arrayList.get(position);
        holder.name.setText(contact.getmName());
        Picasso.get().load(contact.getmImage()).into(holder.btnImg);
        holder.btntoMsg.setOnClickListener(new View.OnClickListener(){

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
        TextView name;
        ImageButton btntoMsg;
        ImageView btnImg;
        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            name = itemView.findViewById(R.id.username);
            btntoMsg = itemView.findViewById(R.id.imageButton);
            btnImg =  itemView.findViewById(R.id.profile_image);

        }
    }
}
