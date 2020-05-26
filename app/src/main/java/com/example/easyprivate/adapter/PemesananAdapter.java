package com.example.easyprivate.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.DetailPemesanan;
import com.example.easyprivate.DetailPencarianItem;
import com.example.easyprivate.R;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Pemesanan> pemesananAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public PemesananAdapter(Context mContext, ArrayList<Pemesanan> pemesananAL) {
        this.mContext = mContext;
        this.pemesananAL = pemesananAL;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView namaTV,jenisKelaminTV,locationTV,mapelTV,statusTV;
        private CircleImageView fotoCIV;
        private LinearLayout itemLL;
        public ViewHolder(@NonNull View iv) {
            super(iv);
            namaTV = iv.findViewById(R.id.namaTV);
            mapelTV=iv.findViewById(R.id.mapelTV);
            jenisKelaminTV=iv.findViewById(R.id.jenis_kelaminTV);
            locationTV= iv.findViewById(R.id.lokasiTV);
            fotoCIV=iv.findViewById(R.id.fotoCIV);
            itemLL=iv.findViewById(R.id.itemLL);
            statusTV=iv.findViewById(R.id.statusTV);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_history_pemesanan, parent,false);
        return new PemesananAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pemesanan pemesanan = pemesananAL.get(position);
        String result ="";
        holder.namaTV.setText(pemesanan.getGuru().getName());
        holder.jenisKelaminTV.setText(pemesanan.getGuru().getJenisKelamin());
        holder.mapelTV.setText(pemesanan.getMataPelajaran().getNamaMapel());
        if (pemesanan.getGuru().getAvatar() != null ) {
            cu.putIntoImage(pemesanan.getGuru().getAvatar(), holder.fotoCIV);
        }
        Address address = cu.getAddress(pemesanan.getGuru().getAlamat().getLatitude(),pemesanan.getGuru().getAlamat().getLongitude());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            sb.append(address.getAddressLine(i)).append("\n");//adress
        }
        sb.append(address.getLocality()).append(", "); //kecamatan
        sb.append(address.getSubAdminArea()).append("");//kota

        result = sb.toString();
        holder.locationTV.setText(result);
        switch(pemesanan.getStatus()){
            case 0:
               holder.statusTV.setText("Menunggu Konfirmasi");
               holder.statusTV.setTextColor(mContext.getResources().getColor(R.color.white));
               holder.statusTV.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
               break;
            case 1:
                holder.statusTV.setText("Pemesanan Diterima");
                holder.statusTV.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.statusTV.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case 2:
                holder.statusTV.setText("Pemesanan Ditolak");
                holder.statusTV.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.statusTV.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                break;
            case 3:
                holder.statusTV.setText("Pemesanan Selesai");
                holder.statusTV.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.statusTV.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                break;
        }

        holder.itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailPemesanan.class);
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
