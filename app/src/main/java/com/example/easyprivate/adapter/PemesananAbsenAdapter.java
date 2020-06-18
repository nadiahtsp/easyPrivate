package com.example.easyprivate.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.QrCodeActivity;
import com.example.easyprivate.R;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PemesananAbsenAdapter extends RecyclerView.Adapter<PemesananAbsenAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Pemesanan> pemesananAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public PemesananAbsenAdapter(Context mContext, ArrayList<Pemesanan> pemesananAL) {
        this.mContext = mContext;
        this.pemesananAL = pemesananAL;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaTV, mapelTV, jamTV, hariTV,jenis_kelaminTV;
        private CircleImageView fotoCIV;
        private LinearLayout itemLL, hariLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTV = itemView.findViewById(R.id.namaTV);
            mapelTV=itemView.findViewById(R.id.mapelTV);
            fotoCIV=itemView.findViewById(R.id.fotoCIV);
            jenis_kelaminTV=itemView.findViewById(R.id.jenis_kelaminTV);
            itemLL=itemView.findViewById(R.id.itemLL);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_qrcode, parent,false);
        return new PemesananAbsenAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pemesanan pemesanan = pemesananAL.get(position);
        String result ="";
        holder.namaTV.setText(pemesanan.getGuru().getName());
        holder.mapelTV.setText(pemesanan.getMataPelajaran().getNamaMapel());
        if (pemesanan.getGuru().getAvatar() != null ) {
            cu.putIntoImage(pemesanan.getGuru().getAvatar(), holder.fotoCIV);
        }
        holder.itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, QrCodeActivity.class);
                i.putExtra("id_pemesanan",pemesanan.getIdPemesanan());
                mContext.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pemesananAL.size();
    }


}
