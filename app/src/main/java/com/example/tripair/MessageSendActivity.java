package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dataUser.Message;
import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MessageSendActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> m_favoritePartners = new ArrayList<>();
    private String m_uid;
    private User m_user;
    private Message m_message;
    private String reciverId;
    private String senderName;
    private String senderId;
    private String content;
    private String senderFirstName;
    private String senderLastName;
    private String m_dateAndTime;
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
        m_favPartnerPosition = intent.getIntExtra("favoritePartnerPosition",-1);
        m_favoritePartners = (ArrayList<ContactPOJO>) intent.getSerializableExtra("favoritePartners");
        TextView lineText = findViewById(R.id.messageTo);
        lineText.setText("Your optional partners to - "+ m_favoritePartners.get(m_favPartnerPosition).getmName());
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
            reciverId = m_favoritePartners.get(m_favPartnerPosition).getId();
            DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
            m_dateAndTime = df.format(Calendar.getInstance().getTime());
            m_message= new Message(senderName,senderId,content,m_dateAndTime, time);
            mRef.child("Messages").child(reciverId).child(senderId).child(time).setValue(m_message);
            moveToFavPartners();
        }


    }

    private void moveToFavPartners() {
        // if everything is ok, return toFavPartners page
        Intent intent =new Intent(this,FavPartnersActivity.class);
        intent.putExtra("favoritePartners", m_favoritePartners);
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
