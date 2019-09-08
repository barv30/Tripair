package com.example.tripair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.dataUser.Message;
import com.example.dataUser.User;
import com.example.recycleViewPack.CustomMessagesAdapter;
import com.example.recycleViewPack.OnRecyclerClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageContentPerSenderActivity extends AppCompatActivity {
    private ArrayList<Message> m_MsgArray = new ArrayList<>();
    private String  m_contactId;
    private Message m_message;
    private RecyclerView mRecyclerView1;
    private CustomMessagesAdapter mAdapter;
    private String m_uid;
    private String m_contactName;
    private User m_user;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content_per_sender);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User) intent.getSerializableExtra("user");
        mRecyclerView1 = findViewById(R.id.recycleView);
        m_contactId = intent.getStringExtra("ContactId");
        m_contactName=intent.getStringExtra("ContactName");
        TextView lineText = findViewById(R.id.txt_line1);
        lineText.setText("Your Messages from " + m_contactName);
        mAdapter = new CustomMessagesAdapter(m_MsgArray, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
            }

            @Override
            public void onRecycleViewItemDeleteClicked(int position, int id) {
            }
        });
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        ValueEventListener UserListener2 = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    m_MsgArray.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot ds : children) {
                        m_message = ds.getValue(Message.class);
                        addMsgToList(m_message.getSenderName(),m_message.getSenderId(),m_message.getContent(),m_message.getDate(),m_message.getMsgNum());
                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        };
        mRef.child("Messages").child(m_uid).child(m_contactId).addValueEventListener(UserListener2);
    }


    private void addMsgToList(String name, String SenderId, String ContentMsg, String date, String time) {
        Message newMessage = null;
        newMessage = new Message(name,SenderId,ContentMsg,date, time);
        m_MsgArray.add(newMessage);
        mAdapter.notifyDataSetChanged();
    }

}
