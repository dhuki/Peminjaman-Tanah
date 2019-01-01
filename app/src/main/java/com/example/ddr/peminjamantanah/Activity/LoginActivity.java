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

import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText nomorsewa;
    EditText password;
    TextView forgot;
    TextView account;
    DatabaseReference firebaseDatabase;
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
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");

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

                                    String user_id = firebaseAuth.getCurrentUser().getUid();

                                    firebaseDatabase.orderByChild("id").equalTo(user_id)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot data : dataSnapshot.getChildren()){
                                                String key_id = data.getKey();
                                                String id = data.child("id").getValue().toString();
                                                String nama = data.child("nama").getValue().toString();
                                                String nomor = data.child("nomor").getValue().toString();

                                                String KTP = data.child("dokumen").child("dokKTP").getValue().toString();
                                                String KK = data.child("dokumen").child("dokKK").getValue().toString();
                                                String BPS = data.child("dokumen").child("dokBPS").getValue().toString();
                                                String SBP = data.child("dokumen").child("dokSBP").getValue().toString();

                                                User user = new User(key_id,id,nomor,nama, KTP, KK, BPS, SBP); //Make instances

                                                startActivity(new Intent(LoginActivity.this,MenuUtamaUserActivity.class)
                                                        .putExtra("User", user)
                                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(LoginActivity.this,"Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
