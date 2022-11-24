package com.lathar.appinventoryuptd;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteItemsActivity extends AppCompatActivity {

    public static TextView resultDeleteView;
    private FirebaseAuth firebaseAuth;
    Button scanToDelete, btnDelete;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        resultDeleteView = findViewById(R.id.tv_qrcode_delete);
        scanToDelete = findViewById(R.id.btn_scan_delete);
        btnDelete = findViewById(R.id.btn_delete_item_todatabase);

        scanToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeDeleteActivity.class));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDatabase();
            }
        });
    }

    public void deleteFromDatabase(){
        String deleteQrCodeValue = resultDeleteView.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finalUser = users.getEmail();
        String resultEmail = finalUser.replace(".","");
        if (!TextUtils.isEmpty(deleteQrCodeValue)){
            databaseReference.child(resultEmail).child("Items").child(deleteQrCodeValue).removeValue();
            Toast.makeText(DeleteItemsActivity.this, "Barang Telah Di Hapus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DeleteItemsActivity.this, "Tolong Scan QrCode", Toast.LENGTH_SHORT).show();
        }
    }
}