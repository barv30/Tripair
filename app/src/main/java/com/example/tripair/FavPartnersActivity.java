package com.example.tripair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.example.recycleViewPack.CustomContactAdapter;
import com.example.recycleViewPack.CustomFavoriteAdapter;
import com.example.recycleViewPack.OnRecyclerClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavPartnersActivity extends AppCompatActivity {

    private ArrayList<ContactPOJO> m_favoritePartners = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private CustomFavoriteAdapter mAdapter;
    private String m_uid;
    private User m_user;
    private int m_tripPosition;
    private Trip m_trip;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_partners);
        Intent intent = getIntent();
        m_user = (User)intent.getSerializableExtra("user");
        m_uid = m_user.getId();
        m_tripPosition =  intent.getIntExtra("tripPosition",-1);
        m_trip = (Trip)intent.getSerializableExtra("trip");
        m_favoritePartners = (ArrayList<ContactPOJO>) intent.getSerializableExtra("favoritePartners");


        mRecyclerView1 = findViewById(R.id.recycleView);

        mAdapter = new CustomFavoriteAdapter(m_favoritePartners, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        ValueEventListener UserListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    ContactPOJO contact = null;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot ds : children) {
                        contact = ds.getValue(ContactPOJO.class);
                        m_favoritePartners.add(contact);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.child("userProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripPosition)).child("favPartner").addValueEventListener(UserListener2);
    }


    @Override
    public void onBackPressed() {
        
        m_user.getAllTrips().updateFavPartnersInSpecificTrip(m_tripPosition,m_favoritePartners);
        mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripPosition)).setValue(m_trip);
        Intent intent = new Intent(this, AllTripsActivity.class);
        intent.putExtra("user", m_user);
        startActivity(intent);
        this.finish();
    }

    public void onDeleteButtonFavClicked(View v)
    {

    }

}





