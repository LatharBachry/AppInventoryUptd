package com.lathar.appinventoryuptd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ViewInventoryActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RecyclerView mRecyclerView;
    DatabaseReference mDatabaseReference;
    private TextView totalNoItem, totalNoSum;
    private int countTotalItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        totalNoItem = findViewById(R.id.tv_totalNoItem);
        totalNoSum = findViewById(R.id.tv_totalSum);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser = users.getEmail();
        String resultEmail = finalUser.replace(".", "");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultEmail).child("Items");
        mRecyclerView = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countTotalItem = (int) dataSnapshot.getChildrenCount();
                    totalNoItem.setText(Integer.toString(countTotalItem));
                } else {
                    totalNoItem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("itemPrice");
                    int pValue = Integer.parseInt(String.valueOf(price));
                    sum = sum + pValue;
                    totalNoSum.setText(String.valueOf(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected  void  onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Items, ScanItemsActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, ScanItemsActivity.UsersViewHolder>
                (  Items.class,
                        R.layout.list_layout,
                        ScanItemsActivity.UsersViewHolder.class,
                        mDatabaseReference )
        {
            @Override
            protected void populateViewHolder(ScanItemsActivity.UsersViewHolder viewHolder, Items model, int position){

                viewHolder.setDetails(getApplicationContext(),model.getItemQrcode(),model.getItemCategory(),model.getItemName(),model.getItemPrice());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}



