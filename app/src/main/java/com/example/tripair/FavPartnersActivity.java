package com.example.tripair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.example.recycleViewPack.CustomContactAdapter;
import com.example.recycleViewPack.OnRecyclerClickListener;

import java.util.ArrayList;

public class FavPartnersActivity extends AppCompatActivity {

    private ArrayList<ContactPOJO> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private CustomContactAdapter mAdapter;
    private String m_uid;
    private User m_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_partners);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User)intent.getSerializableExtra("user");

        mRecyclerView1 = findViewById(R.id.recycleView);

        mAdapter = new CustomContactAdapter(mArrayList, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }
            public void onRecyclerViewItemFavClicked(int position, int id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

            }
        });
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        prepareData();

    }
    private void prepareData() {
//        ContactPOJO contact = null;
//        contact = new ContactPOJO("Bar",30,8,1994,true,25);
//        mArrayList.add(contact);
//        contact = new ContactPOJO("Bar",30,8,1994,true,25);
//        mArrayList.add(contact);
//        contact = new ContactPOJO("Bar",30,8,1994,true,25);
//        mArrayList.add(contact);
//        contact = new ContactPOJO("Bar",30,8,1994,true,25);
//        mArrayList.add(contact);

        mAdapter.notifyDataSetChanged();
    }
}



