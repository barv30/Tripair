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
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.*;

        import com.example.dataUser.User;
        import com.example.recycleViewPack.ContactPOJO;
        import com.example.recycleViewPack.CustomContactAdapter;
        import com.example.recycleViewPack.OnRecyclerClickListener;

public class OptionalPartnerPerTripActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private CustomContactAdapter mAdapter;
    private String m_uid;
    private User m_user;
    private String m_tripCountry;
    private String m_tripCity;
    private ArrayList<ContactPOJO> mArrayDemoFav = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_partner_per_trip);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User)intent.getSerializableExtra("user");
        m_tripCountry = intent.getStringExtra("tripCountry");
        m_tripCity = intent.getStringExtra("tripCity");
        TextView lineText = findViewById(R.id.txt_line);
        lineText.setText("Your optional partners to - "+m_tripCountry+","+m_tripCity);
      //  mArrayList = (ArrayList<ContactPOJO>) intent.getSerializableExtra("arrayPartner");
        mRecyclerView1 = findViewById(R.id.recycleView);
        mAdapter = new CustomContactAdapter(mArrayList, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }
            public void onRecyclerViewItemFavClicked(int position, int id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                addPartnerToFavorites(position);

            }
        });
        prepareData();

        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //prepareData();


    }

    private void addPartnerToFavorites(int position) {
        TextView name, date, smoke,age,leftDate;
        ImageButton btnFav,btnDelete;

            Log.v("ViewHolder","in View Holder");
            name = findViewById(R.id.txt_line);
            date = findViewById(R.id.txt_dest_insert);
            leftDate = findViewById(R.id.txt_dateL);
            smoke = findViewById(R.id.txt_smoke_insert);
            age=findViewById(R.id.txt_age_insert);

        ContactPOJO contact = new ContactPOJO();
        contact.setmName(name.getText().toString());
        contact.setmAge(Integer.parseInt(age.getText().toString()));
        contact.setmDateDest(date.getText().toString());
        contact.setmDateLeft(leftDate.getText().toString());
        contact.setmSmoking(smoke.getText().toString());
        Integer id  = position;
        mArrayDemoFav.add(contact);
    }


    private void prepareData() {
        ContactPOJO contact = null;
       contact = new ContactPOJO("Bar","Vaida",30,8,2019,30,8,2020,true,25);
        mArrayList.add(contact);
        contact = new ContactPOJO("Bar","Vaida",30,8,2019,30,8,2020,true,25);
        mArrayList.add(contact);
        contact = new ContactPOJO("Bar","Vaida",30,8,2019,30,8,2020,true,25);
        mArrayList.add(contact);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_partner_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.editTrip:
            {
                Intent intent = new Intent(this, TripSettingsActivity.class);
                intent.putExtra("userUid", m_uid);
                intent.putExtra("user", m_user);
                startActivity(intent);
            }

            case R.id.editPartner:
            {

            }

            case R.id.favPartners:
            {
                Intent intent = new Intent(this, FavPartnersActivity.class);
                intent.putExtra("favArr", mArrayDemoFav);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
