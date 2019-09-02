package com.example.tripair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.example.recycleViewPack.CustomTripAdpater;
import com.example.recycleViewPack.OnRecyclerClickListener;
import com.example.recycleViewPack.TripPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllTripsActivity extends AppCompatActivity {

    private ArrayList<TripPOJO> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private ArrayList<ContactPOJO> myListPartnersPerTrip;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    private CustomTripAdpater mAdapter = new CustomTripAdpater(mArrayList, new OnRecyclerClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, int id) {
            Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            onButtonClicked(position);
        }

        public void onRecyclerViewItemFavClicked(int position, int id) {

        }

    });
    private String m_uid;
    private User m_user;
    Trip m_trip;
    ValueEventListener UserListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trips);
        Intent intent = getIntent();
        m_user = (User) intent.getSerializableExtra("user");
        m_uid = m_user.getId();
        mRecyclerView1 = findViewById(R.id.recycleView);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);
        TextView lineText = findViewById(R.id.lineText);
        lineText.setText("Welcome "+ m_user.getFirstName()+" !");
         UserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        m_trip = ds.getValue(Trip.class);
                        addTripToList(m_trip.getCountry(), m_trip.getCity(), m_trip.getArriveDay(), m_trip.getArriveMonth(), m_trip.getArriveYear(), m_trip.getLeftDay(),m_trip.getLeftMonth(),m_trip.getLeftYear());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").addValueEventListener(UserListener);

    }


    private void addTripToList(String country, String city, int day, int month, int year,int leftDay,int leftMonth,int leftYear) {

        // get the array of all trips and make array of tripPOJO
        TripPOJO trip = null;
        trip = new TripPOJO(country, city, day, month, year,leftDay,leftMonth,leftYear);
        mArrayList.add(trip);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTrip: {
                Intent intent = new Intent(this, TripSettingsActivity.class);
                intent.putExtra("userUid", m_uid);
                intent.putExtra("user", (User) m_user);
                startActivity(intent);
                this.finish();
                break;

            }
            case R.id.editMyProfile:{
                Intent intent = new Intent(this, SettingProfileActivity.class);
                intent.putExtra("userUid", m_uid);
                intent.putExtra("user", (User) m_user);
                startActivity(intent);
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClicked(int position) {

        Intent intent = new Intent(this, OptionalPartnerPerTripActivity.class);
        TripPOJO tripPojo = mArrayList.get(position);
        intent.putExtra("tripPosition", position);
        intent.putExtra("userUid", m_uid);
        intent.putExtra("user", (User) m_user);
        intent.putExtra("tripCountry", tripPojo.getmCountry());
        intent.putExtra("tripCity", tripPojo.getmCity());
        startActivity(intent);
        this.finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").removeEventListener(UserListener);
    }


}