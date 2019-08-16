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
        import android.widget.Toast;

        import java.util.*;

        import com.example.dataUser.User;
        import com.example.recycleViewPack.ContactPOJO;
        import com.example.recycleViewPack.CustomContactAdapter;
        import com.example.recycleViewPack.OnRecyclerClickListener;

public class MainPageActivity extends AppCompatActivity {
    private ArrayList<ContactPOJO> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private CustomContactAdapter mAdapter;
    private String m_uid;
    private User m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        m_uid = getIntent().getStringExtra("userUid");
        m_user = (User) getIntent().getSerializableExtra("user");

        mRecyclerView1 = findViewById(R.id.recycleView);
        //mRecyclerView2 = findViewById(R.id.recyclerView2);

        mAdapter = new CustomContactAdapter(mArrayList, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);

        prepareData();

        // mRecyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        // mRecyclerView2.setItemAnimator( new DefaultItemAnimator());
        // mRecyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // mRecyclerView2.setAdapter(mAdapter);

    }


    private void prepareData() {
        ContactPOJO contact = null;
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);

        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);
        contact = new ContactPOJO("Dhruvam","9467884671","22/12/1995");
        mArrayList.add(contact);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.addTrip:
            {
                Intent intent = new Intent(this, TripSettingsActivity.class);
                intent.putExtra("userUid", m_uid);
                intent.putExtra("user", m_user);
                startActivity(intent);
            }

            case R.id.editPartner:
            {

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
