package com.lathar.appinventoryuptd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemsActivity extends AppCompatActivity {
    private EditText itemName, itemCategory, itemPrice;
    private TextView itemQrcode;
    private FirebaseAuth firebaseAuth;
    public static TextView resultTextView;
    Button scanButton, addItemToDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");

        resultTextView = findViewById(R.id.tv_qrcode_number);
        scanButton = findViewById(R.id.btn_add_scan_qrcode);
        addItemToDatabase = findViewById(R.id.btn_add_items);
        itemName = findViewById(R.id.edt_nama_barang);
        itemCategory = findViewById(R.id.edt_kategori);
        itemPrice = findViewById(R.id.edt_harga_barang);
        itemQrcode = findViewById(R.id.tv_qrcode_number);

        scanButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class)));

        addItemToDatabase.setOnClickListener(view -> addItem());
    }

    //menambahkan item ke database
    public void addItem(){
        String itemNameValue = itemName.getText().toString();
        String itemQrcodeValue = itemQrcode.getText().toString();
        String itemCategoryValue = itemCategory.getText().toString();
        String itemPriceValue = itemPrice.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser = users.getEmail();
        String resultEmail = finalUser.replace(".", "");

        if (itemQrcodeValue.isEmpty()){
            itemQrcode.setError("Data ini Kosong!!");
            itemQrcode.requestFocus();
            return;
        }

        if(!TextUtils.isEmpty(itemNameValue)&&!TextUtils.isEmpty(itemCategoryValue)&&!TextUtils.isEmpty(itemPriceValue)){
            Items items= new Items(itemNameValue,itemCategoryValue,itemQrcodeValue,itemPriceValue);
            databaseReference.child(resultEmail).child("Items").child(itemQrcodeValue).setValue(items);
            databaseReferencecat.child(resultEmail).child("ItemByCategory").child(itemCategoryValue).child(itemQrcodeValue).setValue(items);
            itemName.setText("");
            itemQrcode.setText("");
            itemPrice.setText("");
            itemQrcode.setText("");
            Toast.makeText(AddItemsActivity.this,itemNameValue+" Added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(AddItemsActivity.this,"Maaf isi semua form",Toast.LENGTH_SHORT).show();
        }
    }

    //Logout Below
//    private void Logout(){
//        firebaseAuth.signOut();
//        finish();
//        startActivity(new Intent(AddItemsActivity.this, LoginActivity.class));
//        Toast.makeText(AddItemsActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logoutMenu:{
//                Logout();
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}