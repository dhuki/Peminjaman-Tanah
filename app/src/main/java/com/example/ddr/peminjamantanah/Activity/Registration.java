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

import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    TextView nomor_sewa;
    TextView nama_pemakai;
    EditText new_email;
    EditText new_password;
    Button sign_up;

    FirebaseAuth mAuth;
    DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nomor_sewa = findViewById(R.id.tf_nomor);
        nama_pemakai = findViewById(R.id.tf_nama);
        new_email = findViewById(R.id.edtx_new_email);
        new_password = findViewById(R.id.edtx_new_password);
        sign_up = findViewById(R.id.btn_sign_up);

        Bundle data = getIntent().getExtras();
        final User user = data.getParcelable("User"); //Take the user instances from PreRegistration

        mAuth = FirebaseAuth.getInstance(); //Inisialisasi Auh
        mDataRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getKey_id()); //Go to Reference ex: User -> 01

        nomor_sewa.setText(user.getNomor_sewa());
        nama_pemakai.setText(user.getNama_pemakai());

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = new_email.getText().toString().trim();
                final String password = new_password.getText().toString().trim();

                View viewProgressBar = getLayoutInflater().inflate(R.layout.progressbar_view,null); //menginisialisasikan view menggunakan getLayoutInflater

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Registration.this,R.style.DialogTheme); //Make alert dialog in this Class -> Layout
                dialogBuilder.setView(viewProgressBar); //Memasukan view progress bar ke alert dialog

                final AlertDialog dialog = dialogBuilder.create(); //dialog builder dimasukan ke sebuah alert dialog agar bisa di show dan di dismiss
                dialog.show(); //show alert dialog

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //create new account
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        dialog.dismiss();

                        if (task.isSuccessful()){

                            String user_id = mAuth.getCurrentUser().getUid(); //Take the user_Id in Auth

                            user.setEmail(email);
                            user.setPassword(password);

                            mDataRef.child("id").setValue(user_id); //Change Value of id in database to the user_Id in Auth

                            Intent i = new Intent(Registration.this, MenuUtamaUserActivity.class);
                            i.putExtra("User", user);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Make task become single task and clear all activity that open before
                            startActivity(i);
                        }
                    }
                });
            }
        });

    }
}
