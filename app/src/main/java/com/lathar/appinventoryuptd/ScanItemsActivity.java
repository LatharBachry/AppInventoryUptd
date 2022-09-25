package com.lathar.appinventoryuptd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ScanItemsActivity extends AppCompatActivity {
    public static EditText resultSearchView;
    private FirebaseAuth firebaseAuth;
    ImageButton scanToSearch;
    Button btnSearch;
    Adapter adapter;
    RecyclerView mRecyclerView;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser = users.getEmail();
        String resultEmail = finalUser.replace(".","");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultEmail).child("Items");
        resultSearchView = findViewById(R.id.edt_search_field);
        scanToSearch = findViewById(R.id.btn_img_btnsearch);
        btnSearch = findViewById(R.id.btn_search);

        mRecyclerView = findViewById(R.id.rv_items);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        scanToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeSearchActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = resultSearchView.getText().toString();
                firebasesearch(searchText);
            }
        });

    }

    public void firebasesearch(String searchText) {
        Query firebaseSearchQuery = mDatabaseReference.orderByChild("itembarcode").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<Items, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, UsersViewHolder>(
                Items.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery)
        {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, Items items, int i) {

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UsersViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
    }
}