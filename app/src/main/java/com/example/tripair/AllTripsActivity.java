package com.example.tripair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.*;

import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.example.recycleViewPack.CustomContactAdapter;
import com.example.recycleViewPack.CustomTripAdpater;
import com.example.recycleViewPack.OnRecyclerClickListener;
import com.example.recycleViewPack.TripPOJO;

public class AllTripsActivity extends AppCompatActivity {
    private ArrayList<TripPOJO> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private ArrayList<ContactPOJO> mArrayDemoPartners;

    private CustomTripAdpater mAdapter = new CustomTripAdpater(mArrayList, new OnRecyclerClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, int id) {
            Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            onButtonClicked();
        }
        public void onRecyclerViewItemFavClicked(int position, int id) {

        }

    });
    private String m_uid;
    private User m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trips);
        Intent intent = getIntent();
        m_uid = (String) intent.getStringExtra("userUid");
        m_user = (User) intent.getSerializableExtra("user");
        mRecyclerView1 = findViewById(R.id.recycleView);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        prepareData();

    }


    private void prepareData() {

        // get the array of all trips and make array of tripPOJO

        TripPOJO trip = null;
        trip = new TripPOJO("Israel", "Haifa", 30, 8, 1994);
        mArrayList.add(trip);
        trip = new TripPOJO("Israel", "Haifa", 30, 8, 1994);
        mArrayList.add(trip);
        trip = new TripPOJO("Israel", "Haifa", 30, 8, 1994);
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
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClicked() {
        fillArrayDemoOfPartners();
        Log.i("info", "bla");
        Intent intent = new Intent(this,OptionalPartnerPerTripActivity.class);
        intent.putExtra("arrayPartner", mArrayDemoPartners);
        startActivity(intent);
    }

    private void fillArrayDemoOfPartners()
    {
        mArrayDemoPartners = new ArrayList<>();
        ContactPOJO contact = null;
        contact = new ContactPOJO("Bar",30,8,1994,0,0,0,true,25);
        mArrayDemoPartners.add(contact);
        contact = new ContactPOJO("Bar",30,8,1994,0,0,0,true,25);
        mArrayDemoPartners.add(contact);
        contact = new ContactPOJO("Bar",30,8,1994,1,1,1995,true,25);
        mArrayDemoPartners.add(contact);
        contact = new ContactPOJO("Bar",30,8,1994,0,0,0,true,25);
        mArrayDemoPartners.add(contact);

    }
}
