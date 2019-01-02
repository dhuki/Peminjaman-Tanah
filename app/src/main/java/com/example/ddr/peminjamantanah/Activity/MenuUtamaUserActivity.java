package com.example.ddr.peminjamantanah.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ddr.peminjamantanah.Adapter.*;
import com.example.ddr.peminjamantanah.Model.MenuUtama;
import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MenuUtamaUserActivity extends AppCompatActivity {

    RecyclerView muRecycleview;
    RecyclerView.LayoutManager muLayoutManager;
    RecyclerView.Adapter muAdapter;
    User user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama_user_activity);

        ArrayList<MenuUtama> listMenuUtama = new ArrayList<>();

        Bundle data = getIntent().getExtras();
        user = data.getParcelable("User");

        listMenuUtama.add(new MenuUtama(R.drawable.ic_add_icon,"Perpanjangan Sewa Tanah", "Menu ini digunakan untuk memperpanjang sewa tanah atau bangunan"));
        listMenuUtama.add(new MenuUtama(R.drawable.ic_switch_icon,"Balik Nama Sewa Tanah", "Menu ini digunakan untuk melakukan pergantian nama kepemilikan"));
        listMenuUtama.add(new MenuUtama(R.drawable.ic_monitor_icon,"Monitoring Berkas", "Menu ini digunakan untuk memonitoring berkas - berkas yang diunggah"));
        listMenuUtama.add(new MenuUtama(R.drawable.ic_informasi_icon,"Informasi Sewa", "Menu ini digunakan untuk mengetahui detail informasi tanah yang disewa"));

        muRecycleview = findViewById(R.id.recycleview_menu_user);

        muLayoutManager = new LinearLayoutManager(this); // Linear tipe RecylceViewnya
        muRecycleview.setLayoutManager(muLayoutManager); //set LayoutManager to recycleView

        muAdapter = new MainAdapter(listMenuUtama,this, user);
        muRecycleview.setAdapter(muAdapter); //set adapter to recycleView
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        Gson gson = new Gson();
//        SharedPreferences sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
//
//        Log.d("sssM", sharedPreferences.getString(user.getKey_id(), "unggah"));
//
//        String retrieveData = sharedPreferences.getString(user.getKey_id(), "unggah"); //"unggah is default value if there is no value in TEXT Pref then put value by unggah"
//        user = gson.fromJson(retrieveData, User.class);
//    }
}
