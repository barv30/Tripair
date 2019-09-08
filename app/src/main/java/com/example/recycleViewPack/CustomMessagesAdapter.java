package com.example.recycleViewPack;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.dataUser.Message;
import com.example.tripair.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomMessagesAdapter extends RecyclerView.Adapter<CustomMessagesAdapter.MyViewHolder> {

    private ArrayList<Message> arrayList = new ArrayList<>();
    private OnRecyclerClickListener listener;

    public CustomMessagesAdapter(ArrayList<Message> arrayList, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public CustomMessagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        Message message = arrayList.get(position);
        holder.date.setText(message.getDate());
        holder.contactMsg.setText(message.getContent());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecycleViewItemDeleteClicked(position, view.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, contactMsg;
        ImageButton btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            date=itemView.findViewById(R.id.recivedDate);
            contactMsg=itemView.findViewById(R.id.messageContent);
            btnDelete = itemView.findViewById(R.id.deleteBtn);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.removeValue();
                }
            });

        }
    }
}
