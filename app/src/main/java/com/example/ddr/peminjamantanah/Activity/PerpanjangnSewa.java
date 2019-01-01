package com.example.ddr.peminjamantanah.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ddr.peminjamantanah.Adapter.PerpanjanganAdapter;
import com.example.ddr.peminjamantanah.Model.Dokumen;
import com.example.ddr.peminjamantanah.Model.MenuUtama;
import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class PerpanjangnSewa extends AppCompatActivity{

    RecyclerView mRecycleView_perpanjangan;
    RecyclerView.LayoutManager mLayoutMananger;
    RecyclerView.Adapter mAdaper;
    final int REQ_PERMISION = 100;
    Uri pdfUri; //URLs that meant for local storage
    StorageReference mStorage;
    DatabaseReference mDatabase;
    String URL;
    int position;
    ArrayList<MenuUtama> list_perpanjangan_sewa = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perpanjangn_sewa);

        Bundle data = getIntent().getExtras();
        user = data.getParcelable("User");

        Log.d("sss",user.getDokumen().getUrlKK());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(); //root file path

        list_perpanjangan_sewa.add(new MenuUtama(R.id.btn_tambah,"Foto copy Kartu Tanda Penduduk (KTP)",user.getDokumen().getUrlKTP()));
        list_perpanjangan_sewa.add(new MenuUtama(R.id.btn_tambah,"Foto copy Kartu Keluarga (KK)",user.getDokumen().getUrlKTP()));
        list_perpanjangan_sewa.add(new MenuUtama(R.id.btn_tambah,"Foto copy Bukti Pembayaran Sewa",user.getDokumen().getUrlKTP()));
        list_perpanjangan_sewa.add(new MenuUtama(R.id.btn_tambah,"Foto copy Surat Bukti Pembayaran",user.getDokumen().getUrlKTP()));

        mRecycleView_perpanjangan = findViewById(R.id.recycleview_menu_perpanjangan);

        mLayoutMananger = new LinearLayoutManager(this);
        mRecycleView_perpanjangan.setLayoutManager(mLayoutMananger);

        mAdaper = new PerpanjanganAdapter(list_perpanjangan_sewa, this, PerpanjangnSewa.this);
        //"this" digunakan untuk memberikan instances perpanjangan sewa karena digunakan untuk menampilkan sesuatu di layout parent

        mRecycleView_perpanjangan.setAdapter(mAdaper);

        //https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, new IntentFilter("position"));
    }


    public BroadcastReceiver mMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            position = intent.getIntExtra("position", 100);
        }
    };

    //NOTE PERMISSION IS ONLY WORKS IN ACTIVITY SO WE CREATE IN IT : https://www.youtube.com/watch?v=XOf_v2f85RU

    public void reqPermission(){
        int req = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE); //output = -1 it's means permission is still denied

        int permissioin = PackageManager.PERMISSION_GRANTED; //output = 0

        Log.d("xxx",req+" "+permissioin);
        //in android 5 (lollipop) or lower value 0 0 no need permission
        //in android 6 (marshmallow) or up value -1 0 need permission

        if (req != permissioin) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISION);
            //fungsi akan menyuruh user untuk memberikan granted access to storage it will give REQ_PERMISSION to callback if user allow the application to access storage
            //and give grantResult[0] value Permission_Granted if user allow
        } else {
            selectPDF();
        }

    }

    @Override //invoke dari ActivityCompat.requestPermissions berguna untuk mengambil value/acknowledge dari fungsi granted yang dilakukan user
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_PERMISION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPDF();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectPDF(){
        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);//TO FETCH FILES
        startActivityForResult(i,86);
    }

    @Override //invoke dari startActivityForResult(i,)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode == 86 means check that this method is invoked by intent ?
        //resultCode == RESULT_OK means check whether user has complete operation or not (ex : user open file manager and close successfully)
        //data != null means data was selected and not null
        if (requestCode == 86 && resultCode == RESULT_OK && data != null){
            pdfUri = data.getData(); //return local uri of selected file

//            list_perpanjangan_sewa.get(position).setSubKeterangan(pdfUri.toString());
//            mAdaper.notifyItemChanged(position);

//            if (position == 0){
//                user.getDokumen().setUrlKTP(pdfUri.toString());
//            } else if (position == 1){
//                user.getDokumen().setUrlKK(pdfUri.toString());
//            } else if (position == 2){
//                user.getDokumen().setUrlBPS(pdfUri.toString());
//            } else if (position == 3){
//                user.getDokumen().setUrlSBP(pdfUri.toString());
//            }

            upload(pdfUri);
        } else {
            Toast.makeText(this,"Select a file",Toast.LENGTH_SHORT).show();
        }
    }

    public void upload(Uri uriPdf){

        final String fileName = System.currentTimeMillis()+"";

        View viewProgressDialog = getLayoutInflater().inflate(R.layout.progressbar_view,null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this,R.style.DialogTheme);
        dialogBuilder.setView(viewProgressDialog);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        mStorage.child("Upload").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();

                        URL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(); //return value that you uploaded

                        list_perpanjangan_sewa.get(position).setSubKeterangan(URL);

                        mAdaper.notifyItemChanged(position);

                        Toast.makeText(PerpanjangnSewa.this,list_perpanjangan_sewa.get(position).getSubKeterangan(),Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
