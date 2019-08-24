package com.example.recycleViewPack;

        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import com.example.tripair.R;

        import java.util.*;

public class CustomTripAdpater extends RecyclerView.Adapter<CustomTripAdpater.MyViewHolder> {

    private ArrayList<TripPOJO> arrayList = new ArrayList<>();
    private OnRecyclerClickListener listener;

    public CustomTripAdpater(ArrayList<TripPOJO> arrayList, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list,parent,false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        TripPOJO trip = arrayList.get(position);
        holder.where.setText(trip.getmCountry()+","+trip.getmCity());
        holder.date.setText(trip.getmFullDate());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(position,view.getId());
                Log.i("Info",holder.where.toString());
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView where,date;
        ImageButton button;
        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            where = itemView.findViewById(R.id.txt_trip_insert);
            date = itemView.findViewById(R.id.txt_date);
            button=itemView.findViewById(R.id.btn_open);

        }


    }
}