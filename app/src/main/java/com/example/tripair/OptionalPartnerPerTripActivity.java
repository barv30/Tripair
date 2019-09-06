package com.example.tripair;

        import android.app.Activity;
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
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.*;

        import com.example.dataUser.Trip;
        import com.example.dataUser.User;
        import com.example.recycleViewPack.ContactPOJO;
        import com.example.recycleViewPack.CustomContactAdapter;
        import com.example.recycleViewPack.OnRecyclerClickListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class OptionalPartnerPerTripActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> mOptionalPartnersArray = new ArrayList<>();
    Trip m_tripPerOptionalPartner;
    String m_partnerID;
    private RecyclerView mRecyclerView1;
    private CustomContactAdapter mAdapter;
    private String m_uid;
    private User m_user;
    private String m_tripCountry;
    private String m_tripCity;
    private int m_tripPosition;
    private Trip m_tripUserObj;
    ValueEventListener UserListener2;
    ValueEventListener UserListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_partner_per_trip);
        Intent intent = getIntent();
        m_user = (User)intent.getSerializableExtra("user");
        m_uid = m_user.getId();
        m_tripCountry = intent.getStringExtra("tripCountry");
        m_tripCity = intent.getStringExtra("tripCity");
        m_tripPosition = intent.getIntExtra("tripPosition",-1);
        m_tripUserObj = m_user.getAllTrips().getTripByPosition(m_tripPosition);
        TextView lineText = findViewById(R.id.txt_line);
        lineText.setText("Your optional partners to - "+m_tripCountry+","+m_tripCity);

      //  mOptionalPartnersArray = (ArrayList<ContactPOJO>) intent.getSerializableExtra("arrayPartner");
        mRecyclerView1 = findViewById(R.id.recycleView);
        mAdapter = new CustomContactAdapter(mOptionalPartnersArray, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
               // Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                addPartnerToFavorites(position);
             //   mOptionalPartnersArray.remove(mOptionalPartnersArray.get(position));
               // mAdapter.notifyDataSetChanged();

            }
    });


        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

         UserListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot ds : children) {
                        m_tripPerOptionalPartner = ds.getValue(Trip.class);
                        m_partnerID = m_tripPerOptionalPartner.getM_ownerID();
                        if (! (m_partnerID.equals(m_uid))) {
                            getUserPartnerAccordingID(m_partnerID);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.child("Countries").child(m_tripCountry).child(m_tripCity).addValueEventListener(UserListener2);
    }


    private void getUserPartnerAccordingID(String partnerID) {
         UserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User userPartner;
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    userPartner = dataSnapshot.getValue(User.class);
                    Trip tripOfPartner = userPartner.getAllTrips().findInTripList(m_tripCountry, m_tripCity);
                    String partnerID = m_partnerID;
                    if (tripOfPartner != null) {
                        if (filterPartner(userPartner, tripOfPartner) && isInFavAlready(userPartner)) {
                            addUserPartnerToList(userPartner.getId(),userPartner.getFirstName(), userPartner.getLastName(), tripOfPartner.getArriveDay(),
                                    tripOfPartner.getArriveMonth(), tripOfPartner.getArriveYear(), tripOfPartner.getLeftDay(), tripOfPartner.getLeftMonth(), tripOfPartner.getLeftYear(), userPartner.isSmoking(), userPartner.getAge(),userPartner.getImgURL());
                        }

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.child("usersProfile").child(m_partnerID).addValueEventListener(UserListener);

        if (mOptionalPartnersArray.size() == 0)
        {

        }
    }

    private boolean isInFavAlready(User userPartner)
    {
        if (m_tripUserObj.getFavPartner().size() != 0) {
            for (ContactPOJO usr : m_tripUserObj.getFavPartner()) {
                if (usr.getId().equals(userPartner.getId())) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean matchStyle(Trip tripOfPartner)
    {
        for (String styleUser : m_tripUserObj.getStyleTrip())
        {
            for (String stylePartner : tripOfPartner.getStyleTrip())
            {
                if (styleUser.equals(stylePartner)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isDateMatch (User userPartner, Trip tripOfPartner)
    {
        return true;
    }

    private boolean isLanguageMatch (User userPartner)
    {
        for (String lagUser : m_user.getLanguages())
        {
            for (String lagPartner : userPartner.getLanguages())
            {
                if ( lagUser.equals(lagPartner)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean filterPartner(User userPartner, Trip tripOfPartner)
    {
        if (
                (isDateMatch(userPartner, tripOfPartner)) &&
                ((m_tripUserObj.getPartner().isSmoking()) || (!(m_tripUserObj.getPartner().isSmoking()) && (!userPartner.isSmoking()))) &&
                ((tripOfPartner.getPartner().isSmoking()) || (!(tripOfPartner.getPartner().isSmoking()) && (!m_user.isSmoking()))) &&
                (((m_tripUserObj.getPartner().getGender()).equals("No matter")) || (((!(m_tripUserObj.getPartner().getGender()).equals("No matter"))) && ((m_tripUserObj.getPartner().getGender()).equals(userPartner.getGender())))) &&
                (((tripOfPartner.getPartner().getGender()).equals("No matter")) || (((!(tripOfPartner.getPartner().getGender()).equals("No matter"))) && ((tripOfPartner.getPartner().getGender()).equals(m_user.getGender())))) &&
                 (userPartner.getAge() >= m_tripUserObj.getPartner().getMinAge())&&
                 (userPartner.getAge() <= m_tripUserObj.getPartner().getMaxAge()) &&
                 (m_user.getAge() >= tripOfPartner.getPartner().getMinAge() &&
                 (m_user.getAge() <= tripOfPartner.getPartner().getMaxAge()) &&
                 (matchStyle(tripOfPartner))) &&
                 (isLanguageMatch(userPartner))
        )

        {
            return true;
        }

        return false;
    }

        private void addUserPartnerToList(String id, String firstName, String lastName, int arriveDay, int arriveMonth, int arriveYear, int leftDay, int leftMonth, int leftYear, boolean smoking, int age, String image)
        {

        // get the array of all trips and make array of tripPOJO
        ContactPOJO UserPartner = null;
        UserPartner = new ContactPOJO(id ,firstName,lastName, arriveDay, arriveMonth, arriveYear, leftDay, leftMonth, leftYear, smoking, age, image);
        mOptionalPartnersArray.add(UserPartner);
        mAdapter.notifyDataSetChanged();
        }

    private void addPartnerToFavorites(int position) {
        TextView name, date, smoke,age,leftDate;
        RecyclerView.ViewHolder child =mRecyclerView1.findViewHolderForLayoutPosition(position);
            name=child.itemView.findViewById(R.id.txt_name);
            Log.v("ViewHolder","in View Holder");
            date = child.itemView.findViewById(R.id.txt_dest_insert);
            leftDate = child.itemView.findViewById(R.id.txt_dateL);
            smoke = child.itemView.findViewById(R.id.txt_smoke_insert);
            age=child.itemView.findViewById(R.id.txt_age_insert);

        ContactPOJO contact = new ContactPOJO();
        Integer id  = position;
        contact.setId(mOptionalPartnersArray.get(position).getId());
        contact.setmName(name.getText().toString());
        contact.setmAge(Integer.parseInt(age.getText().toString()));
        contact.setmDateDest(date.getText().toString());
        contact.setmDateLeft(leftDate.getText().toString());
        contact.setmSmoking(smoke.getText().toString());
        m_tripUserObj.updateFavPartner(contact);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_partner_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.editTrip)
            {
                mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripPosition)).setValue(m_tripUserObj);
                Intent intent = new Intent(this, EditTripSettingsActivity.class);
                intent.putExtra("userUid", m_uid);
                intent.putExtra("user", m_user);
                intent.putExtra("tripPosition",m_tripPosition);
                intent.putExtra("trip", m_tripUserObj);
                startActivity(intent);
                this.finish();
            }
        else if (idItem == R.id.editPartner)
         {
             mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripPosition)).setValue(m_tripUserObj);
                Intent intent = new Intent(this, PartnerSettingsActivity.class);
                intent.putExtra("user", m_user);
                intent.putExtra("trip",m_tripUserObj);
                intent.putExtra("isEditMode", "edit");
                intent.putExtra("tripPosition",m_tripPosition);
                intent.putExtra("tripCountryKey", m_tripCountry);
                intent.putExtra("tripCityKey", m_tripCity);
                startActivity(intent);
                this.finish();

         }
        else if (idItem == R.id.favPartners)
        {
            Intent intent = new Intent(this, FavPartnersActivity.class);
                intent.putExtra("favoritePartners", m_tripUserObj.getFavPartner());
                intent.putExtra("trip",m_tripUserObj);
                intent.putExtra("tripPosition", m_tripPosition);
                intent.putExtra("user", m_user);
                startActivity(intent);
                this.finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripPosition)).setValue(m_tripUserObj);
        Intent intent = new Intent(this, AllTripsActivity.class);
        intent.putExtra("user", m_user);
        startActivity(intent);
        this.finish();
    }

}
