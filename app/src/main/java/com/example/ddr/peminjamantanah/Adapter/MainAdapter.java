package com.example.ddr.peminjamantanah.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddr.peminjamantanah.Activity.LoginActivity;
import com.example.ddr.peminjamantanah.Activity.PerpanjangnSewa;
import com.example.ddr.peminjamantanah.Model.MenuUtama;
import com.example.ddr.peminjamantanah.Model.User;
import com.example.ddr.peminjamantanah.R;
import java.util.ArrayList;

public class MainAdapter extends android.support.v7.widget.RecyclerView.Adapter<MainAdapter.ViewHolder>{
    //<MainAdapter.Viewholder> berarti kita hanya mengambil ViewHolder dari MainAdapter saja sehingga mengakibatkan
    //hanya dapat diambil satu ViewHolder saja tidak bisa lebih (common : Main Content).

    ArrayList<MenuUtama> listmenu;
    Context mContext;
    User user;

    public MainAdapter(ArrayList<MenuUtama> listMenu, Context mContext, User user) {
        this.listmenu = listMenu;
        this.mContext = mContext;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("createVH Main",String.valueOf(i));

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_menu_utama_user, viewGroup,false);
        return new ViewHolder(view); //give layout of Cardview to RecycleView
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //give the value to each R.id.?? (in this case tf_nama) in Cardview filled it by automated looping
        viewHolder.images.setImageResource(listmenu.get(i).getImage());
        viewHolder.Keterangan.setText(listmenu.get(i).getKeterangan());
        viewHolder.subKeterangan.setText(listmenu.get(i).getSubKeterangan());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (i){
                    case 0:
                        Intent i = new Intent(mContext,PerpanjangnSewa.class);
                        i.putExtra("User", user);
                        mContext.startActivity(i);
                        break;
                    case 1:
                        Toast.makeText(mContext,"Currently is not available",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(mContext,"Currently is not available",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext,"Currently is not available",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listmenu.size(); //give total row in arraylist
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //extends RecyclerView.ViewHolder tersebut hanya di gunakan untuk MainAdapter saja
        //karena RecyclerView.Adapter<MainAdapter.ViewHolder>

        ImageView images;
        TextView Keterangan;
        TextView subKeterangan;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView); //View from Cardview

            images = itemView.findViewById(R.id.wv_image);
            Keterangan = itemView.findViewById(R.id.tf_keterangan);
            subKeterangan = itemView.findViewById(R.id.tf_subKeterangan);
            cardView = itemView.findViewById(R.id.btn_cardview);
        }
    }
}
