package com.example.ddr.peminjamantanah.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddr.peminjamantanah.Model.Dokumen;
import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PreRegistration extends AppCompatActivity {

    EditText nomorsewa;
    Button cari;
    DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_registration);

        nomorsewa = findViewById(R.id.edtx_nomor);
        cari = findViewById(R.id.btn_cari);

        mDataRef = FirebaseDatabase.getInstance().getReference("Users");

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = nomorsewa.getText().toString().trim();

                View viewProgressBar = getLayoutInflater().inflate(R.layout.progressbar_view,null); //menginisialisasikan view menggunakan getLayoutInflater

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PreRegistration.this, R.style.DialogTheme); //Make alert dialog in this Class -> Layout
                dialogBuilder.setView(viewProgressBar); //Memasukan view progress bar ke alert dialog

                final AlertDialog dialog = dialogBuilder.create(); //dialog builder dimasukan ke sebuah alert dialog agar bisa di show dan di dismiss
                dialog.show(); //show alert dialog

                final Query query = mDataRef.orderByChild("nomor").equalTo(temp); //is like WHERE nomor = 10.30.40.123  query in SQL Database
                query.addListenerForSingleValueEvent(new ValueEventListener() { //function that check the query WHERE above. And we use "addListenerForSingleValue" instead of "addValueListener"
                    //Because we just want to Single check for data : exist or not. When we use valueListener it will always check of changing the data.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dialog.dismiss(); //menghilangkan alert dialog

                        if (dataSnapshot.exists()){ //dataSnapshot return as key of "Users" and Value exist
                            //This means the value exist, you could also dataSnaphot.exist()
                            for (DataSnapshot data : dataSnapshot.getChildren()){ //so we must get children first

                                String key_id = data.getKey(); //Take key inside of Users
                                String id = data.child("id").getValue().toString(); //Take the value inside of key_id
                                String nama = data.child("nama").getValue().toString();
                                String nomor = data.child("nomor").getValue().toString();

//                                String KTP = data.child("dokumen").child("dokKTP").getValue().toString();
//                                String KK = data.child("dokumen").child("dokKK").getValue().toString();
//                                String BPS = data.child("dokumen").child("dokBPS").getValue().toString();
//                                String SBP = data.child("dokumen").child("dokSBP").getValue().toString();

                                String KTP = null;
                                String KK = null;
                                String BPS = null;
                                String SBP = null;

                                User user = new User(key_id,id,nomor,nama, KTP, KK, BPS, SBP); //Make instances

                                if (user.getId().equals("")){
                                    Intent i = new Intent(PreRegistration.this,Registration.class);
                                    i.putExtra("User",user); //gives instances to Registration
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(PreRegistration.this, "You're already have account", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else { //if value doesn't exist
                            Toast.makeText(PreRegistration.this,String.valueOf(dataSnapshot.exists()),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
            }
        });
    }
}
