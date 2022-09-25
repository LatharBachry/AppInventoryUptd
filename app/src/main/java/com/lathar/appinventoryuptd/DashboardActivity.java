package com.lathar.appinventoryuptd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private CardView addItems, deleteItems, scanItems, viewInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addItems = (CardView)findViewById(R.id.addItems);
        deleteItems =(CardView) findViewById(R.id.deleteItems);
        scanItems = (CardView) findViewById(R.id.viewProduct);
        viewInventory = (CardView) findViewById(R.id.viewInventory);

        addItems.setOnClickListener(this);
        deleteItems.setOnClickListener(this);
        scanItems.setOnClickListener(this);
        viewInventory.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent dsb;

        switch (view.getId()){
            case R.id.addItems: dsb = new Intent(this, AddItemsActivity.class); startActivity(dsb); break;
        }
    }

    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
        Toast.makeText(DashboardActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}