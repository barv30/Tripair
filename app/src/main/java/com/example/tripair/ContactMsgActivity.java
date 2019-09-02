package com.example.tripair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.example.recycleViewPack.CustomContactMsgAdapter;
import com.example.recycleViewPack.OnRecyclerClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactMsgActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> m_contactMsgArray = new ArrayList<>();
    String m_contactId;
    private RecyclerView mRecyclerView1;
    private CustomContactMsgAdapter mAdapter;
    private String m_uid;
    private User m_user;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_msg);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User)intent.getSerializableExtra("user");
        mRecyclerView1 = findViewById(R.id.recycleView);
        mAdapter=new CustomContactMsgAdapter(m_contactMsgArray, new OnRecyclerClickListener(){
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                onBtnClicked(position, id);
            }
        });

        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        ValueEventListener UserListener2 = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot ds : children) {
                        m_contactId = ds.getValue(String.class);
                        if (!(m_contactId.equals(m_uid))) {
                            getUserContactAccordingID(m_contactId);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.child("Messages").child(m_uid).addValueEventListener(UserListener2);
    }

    private void onBtnClicked(int position, int id) {
    }


    private void getUserContactAccordingID(String contactId) {
        ValueEventListener UserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userPartner;
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    userPartner = dataSnapshot.getValue(User.class);
                    addUserPartnerToList(userPartner.getFirstName(), userPartner.getLastName(),contactId);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.child("usersProfile").child(m_contactId).addValueEventListener(UserListener);
    }

    private void addUserPartnerToList(String firstName, String lastName, String contactId) {

        ContactPOJO UserPartner = null;
        UserPartner = new ContactPOJO(firstName,lastName,-1,-1,-1,-1,-1,-1,false,-1,contactId);
        m_contactMsgArray.add(UserPartner);
        mAdapter.notifyDataSetChanged();
    }




}
