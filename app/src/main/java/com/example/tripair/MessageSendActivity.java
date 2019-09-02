package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dataUser.Message;
import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageSendActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> m_favoritePartners = new ArrayList<>();
    private String m_uid;
    private User m_user;
    private int m_tripPosition;
    private Trip m_trip;
    private Message m_message;
    private String reciverId;
    private String senderName;
    private String senderId;
    private String content;
    private String senderFirstName;
    private String senderLastName;
    private int m_favPartnerPosition;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User)intent.getSerializableExtra("user");
        m_tripPosition =  intent.getIntExtra("tripPosition",-1);
        m_trip = (Trip)intent.getSerializableExtra("trip");
        m_favPartnerPosition = intent.getIntExtra("favoritePartnerPosition",-1);
        m_favoritePartners = (ArrayList<ContactPOJO>) intent.getSerializableExtra("favoritePartners");
    }

    public void SendButtonClicked (View v){
        String isValid = checkIfInputFromUserIsValid();
        String time= Long.toString(System.currentTimeMillis()/1000);
        if(isValid != null)
        {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = isValid;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        else
        {
            senderFirstName=m_user.getFirstName();
            senderLastName=m_user.getLastName();
            senderName = senderFirstName +" "+ senderLastName;
            senderId=  m_uid;
            reciverId = m_favoritePartners.get(m_favPartnerPosition).getmContactId();
            m_message= new Message(senderName,senderId,content);
            mRef.child("Messages").child(reciverId).child(senderId).child(time).setValue(m_message);
            moveToFavPartners();
        }


    }

    private void moveToFavPartners() {
        // if everything is ok, return toFavPartners page
        Intent intent =new Intent(this,FavPartnersActivity.class);
        intent.putExtra("favoritePartners", m_favoritePartners);
        intent.putExtra("trip",m_trip);
        intent.putExtra("tripPosition", m_tripPosition);
        intent.putExtra("userUid", m_uid);
        intent.putExtra("user", m_user);
        startActivity(intent);
    }

    private String checkIfInputFromUserIsValid(){
        String empty="";
        EditText userMessageContent = (EditText) findViewById(R.id.msgContentInput);
        String userMessageContentText = userMessageContent.getText().toString();

        if (userMessageContentText.equals("")) {
            return "Can't send empty message!";
        } else
        {
            content=userMessageContentText;
        }
        return null;
    }
}
