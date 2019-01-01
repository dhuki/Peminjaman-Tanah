package com.example.ddr.peminjamantanah.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;

import com.example.ddr.peminjamantanah.Activity.PerpanjangnSewa;
import com.example.ddr.peminjamantanah.Model.MenuUtama;
import com.example.ddr.peminjamantanah.R;


// NEW APPROACH USING RECYCLE VIEW
// http://www.devexchanges.info/2016/10/adding-header-and-footer-layouts-for.html

import java.util.ArrayList;

public class PerpanjanganAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //<Recycler.Viewholder> berarti kita dapat mengambil Recycler.ViewHolder
    //dimana Recycler.View dapat diambil lebih dari satu ViewHolder (common : Header - Main Content - Footer)

    ArrayList<MenuUtama> listmenuperpanjangan;
    Context context;
    PerpanjangnSewa activity; //ini digunakan untuk memanggil fungsi request untuk mengakses file

    public PerpanjanganAdapter(ArrayList<MenuUtama> listmenuperpanjangna, Context context, PerpanjangnSewa activity) {
        this.context = context;
        this.listmenuperpanjangan = listmenuperpanjangna;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == 1) {
            View footer_menu_perpanjangan = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_menu_perpanjangan_sewa, viewGroup, false);
            return new FooterViewHolder(footer_menu_perpanjangan);

            //Fungsi yang digunakan untuk memasukan view dari layout 1 ke dalam RecyclerView bedasarkan fungsi getItemViewType

        } else {
            View list_menu_perpanjangan = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_menu_perpanjangan_sewa, viewGroup, false);
            return new ListViewHolder(list_menu_perpanjangan);

            //Fungsi yang digunakan untuk memasukan view dari layout 0 ke dalam RecyclerView bedasarkan fungsi getItemViewType
        }
    }

    @Override //invoke onCreateViewHolder
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof ListViewHolder) {
            final ListViewHolder listMenu = (ListViewHolder) viewHolder; //di cast agar RecyclerView.ViewHolder mengambil ListViewHolder.ViewHolder
            listMenu.Judul.setText(listmenuperpanjangan.get(i).getKeterangan());
            listMenu.subJudul.setText(listmenuperpanjangan.get(i).getSubKeterangan());
            listMenu.btn_tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.reqPermission();
                    } else {
                        activity.selectPDF();
                    }

                    //it will local broadcast message in apps
                    //Intent filter will selected specific action that given from parameter (ex: position)
                    Intent intent = new Intent("position");
                    intent.putExtra("position", viewHolder.getAdapterPosition());

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        } else if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footerMenu = (FooterViewHolder) viewHolder; //di cast agar RecyclerView.ViewHolder mengambil FooterViewHolder.ViewHolder
            footerMenu.btn_send_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("VT", String.valueOf(position));
        return (position == listmenuperpanjangan.size()) ? 1 : 0;

        //fungsi ini digunakan untuk membedakan Item pada konten, di konten manakah terdapat Item tersebut
        //ex diatas: Item 4 berarti konten pada layout 1 sedangkan Item 0,1,2,3 berarti konten pada layout 0
    }

    @Override
    public int getItemCount() {
        return listmenuperpanjangan.size() + 1;

        //fungsi ini harus disesuaikan dengan jumlah konten,
        //karena banyaknya konten akan di generate disini untuk dijadikan Item pada recyclerView
    }


    //extends RecyclerView.ViewHolder tersebut dapat di gunakan untuk class manapun yang meng-extends RecyclerView.ViewHolder
    //karena RecyclerView.Adapter<RecyclerView.ViewHolder>

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView Judul;
        TextView subJudul;
        ImageButton btn_tambah;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            Judul = itemView.findViewById(R.id.tf_judul_title);
            subJudul = itemView.findViewById(R.id.tf_sub_judul);
            btn_tambah = itemView.findViewById(R.id.btn_tambah);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        Button btn_send_file;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_send_file = itemView.findViewById(R.id.btn_send_file);
        }
    }

}
