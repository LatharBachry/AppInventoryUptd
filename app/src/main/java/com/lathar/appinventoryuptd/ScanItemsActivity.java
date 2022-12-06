package com.lathar.appinventoryuptd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ScanItemsActivity extends AppCompatActivity {
    public static EditText resultSearchView;
    private FirebaseAuth firebaseAuth;
    ImageButton scanToSearch;
    Button btnSearch, btnSearchName;
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
        btnSearchName=findViewById(R.id.btn_search_name);

        mRecyclerView = findViewById(R.id.rv_items);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        scanToSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ScanCodeSearchActivity.class)));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = resultSearchView.getText().toString();
                firebasesearch(searchText);
            }
        });

        btnSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = resultSearchView.getText().toString();
                firebasesearchname(searchText);
            }
        });

    }
    

    public void firebasesearch(String searchText) {
        Query firebaseSearchQuery = mDatabaseReference.orderByChild("itemQrcode").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<Items, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, UsersViewHolder>(
                Items.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery)
        {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, Items model, int position) {
                usersViewHolder.setDetails(getApplicationContext(),model.getItemQrcode(), model.getItemCategory(),model.getItemName(),model.getItemPrice() );
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public void firebasesearchname(String searchText) {
        Query firebaseSearchQuery = mDatabaseReference.orderByChild("itemName").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<Items, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, UsersViewHolder>(
                Items.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery)
        {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, Items model, int position) {
                usersViewHolder.setDetails(getApplicationContext(),model.getItemQrcode(), model.getItemCategory(),model.getItemName(),model.getItemPrice() );
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

        public void setDetails(Context ctx, String itemQrcode, String itemCategory, String itemName, String itemPrice) {
            TextView item_qrcode= (TextView) mView.findViewById(R.id.tv_viewqrcode);
            TextView item_name = (TextView) mView.findViewById(R.id.tv_viewitemname);
            TextView item_price = (TextView) mView.findViewById(R.id.tv_viewitemprice);
            TextView item_category = (TextView) mView.findViewById(R.id.tv_viewitemcategory);

            item_qrcode.setText(itemQrcode);
            item_name.setText(itemName);
            item_category.setText(itemCategory);
            item_price.setText(itemPrice);
        }

    }
}