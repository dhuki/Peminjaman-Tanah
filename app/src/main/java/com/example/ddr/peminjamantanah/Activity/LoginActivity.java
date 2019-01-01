package com.example.ddr.peminjamantanah.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddr.peminjamantanah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText nomorsewa;
    EditText password;
    TextView forgot;
    TextView account;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        nomorsewa = findViewById(R.id.edtx_username);
        password = findViewById(R.id.edtx_password);
        btn_login = findViewById(R.id.btn_login);
        forgot = findViewById(R.id.tf_forgot);
        account = findViewById(R.id.tf_account);

        firebaseAuth = FirebaseAuth.getInstance(); //Inisialisasi Auth

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View viewProgressDialog = getLayoutInflater().inflate(R.layout.progressbar_view,null);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this,R.style.DialogTheme); //didalam setonClickListener context harus LoginActivity.this tidak bisa this saja
                dialogBuilder.setView(viewProgressDialog);

                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();


                firebaseAuth.signInWithEmailAndPassword(nomorsewa.getText().toString().trim(),password.getText().toString().trim())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() { //This is the function that helps to deal with login
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){

                                dialog.dismiss();

                                if (!task.isSuccessful()){ //email & password is not correct
                                    Toast.makeText(LoginActivity.this,nomorsewa.getText(),Toast.LENGTH_LONG).show();
                                } else { //email & password is correct
                                    startActivity(new Intent(LoginActivity.this,MenuUtamaUserActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }
                            }
                        });
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PreRegistration.class));
            }
        });
    }
}
