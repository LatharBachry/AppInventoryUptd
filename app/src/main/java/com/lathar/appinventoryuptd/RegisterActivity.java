package com.lathar.appinventoryuptd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail,edtPassword, edtConfirmPassword;
    public Button btnUserRegister;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edt_name_reg);
        edtEmail = findViewById(R.id.edt_email_reg);
        edtPassword = findViewById(R.id.edt_password_reg);
        edtConfirmPassword = findViewById(R.id.edt_confpassword_reg);
        btnUserRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.prb_register);
        progressBar.setVisibility(View.GONE);

        mAuth =FirebaseAuth.getInstance();

        btnUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            //handle already login user
        }
    }

    private void registerUser() {
        final String name = edtName.getText().toString().trim();
        final String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString().trim();
        String cpassword = edtConfirmPassword.getText().toString().trim();

        if (name.isEmpty()){
            edtName.setError("Nama Harap di isi");
            edtName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edtEmail.setError("Email Harap di isi");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email tidak sesuai");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword.setError("Password Harap di isi");
            edtPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            edtPassword.setError("password terlalu pendek");
            edtPassword.requestFocus();
            return;
        }

        if (!password.equals(cpassword)){
            edtConfirmPassword.setError("Password tidak sesuai");
            edtConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

//                            addStudent();

                        final User user = new User(
                                name,
                                email

                        );
                        //important to retrive data and send data based on user email
                        FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
                        String UserID=usernameinfirebase.getEmail();
                        // String result = UserID.substring(0, UserID.indexOf("@"));
                        String resultemail = UserID.replace(".","");

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(resultemail).child("UserDetails")
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    progressBar.setVisibility(View.GONE);
                                    if (task1.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this,DashboardActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Tidak dapat mengirim", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

}