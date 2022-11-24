package com.lathar.appinventoryuptd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvPasswordReset, tvRegisterAccount;
    private ProgressBar progressBar;
    private EditText edtPassResetEmail;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get XML in login
        edtEmail = (EditText) findViewById(R.id.edt_email_login);
        edtPassword = (EditText) findViewById(R.id.edt_password_login);
        btnLogin = (Button) findViewById(R.id.btn_login);

        tvRegisterAccount = (TextView) findViewById(R.id.tv_register_account);
        tvPasswordReset = (TextView) findViewById(R.id.tv_reset_password);
        edtPassResetEmail = findViewById(R.id.edt_email_login);
        progressBar = (ProgressBar) findViewById(R.id.prb_login);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //Cek Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });

        tvPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        tvRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(backRegister);
            }
        });

    }

    public void resetPassword() {
        final String resetEmail = edtPassResetEmail.getText().toString();

        if (resetEmail.isEmpty()){
            edtPassResetEmail.setError("ini Kosong");
            edtPassResetEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(resetEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Kami mengirimkan petunjuk untuk reset Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal untuk mengirimkan permintaan Reset Email", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void validate(String userEmail, String userPassword) {

        progressDialog.setMessage(".....Silahkan Menunggu.....");
        progressDialog.show();

        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

}