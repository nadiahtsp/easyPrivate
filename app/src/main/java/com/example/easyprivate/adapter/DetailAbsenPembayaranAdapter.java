package com.example.easyprivate.adapter;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.R;
import com.example.easyprivate.model.PembayaranAbsen;

import java.util.ArrayList;

public class DetailAbsenPembayaranAdapter extends RecyclerView.Adapter<DetailAbsenPembayaranAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<PembayaranAbsen> absenAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public DetailAbsenPembayaranAdapter(Context mContext, ArrayList<PembayaranAbsen> absenAL) {
        this.mContext = mContext;
        this.absenAL = absenAL;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mapel,harga,jumper,jumHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mapel = itemView.findViewById(R.id.mapelTV);
            harga = itemView.findViewById(R.id.hargaTV);
            jumper=itemView.findViewById(R.id.jumPerTV);
            jumHarga=itemView.findViewById(R.id.jumHargaTV);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_detail_pembayaran_absen, parent,false);
        return new DetailAbsenPembayaranAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PembayaranAbsen detPem = absenAL.get(position);
        holder.mapel.setText(detPem.getPemesanan().getMataPelajaran().getNamaMapel()+" ( "+detPem.getGuru().getName()+" )");
        holder.harga.setText("Rp " +detPem.getPemesanan().getMataPelajaran().getJenjang().getHargaPerPertemuan());
        holder.jumper.setText("x "+detPem.getJumlahAbsen());
        int subTotal;
        subTotal = detPem.getPemesanan().getMataPelajaran().getJenjang().getHargaPerPertemuan() * detPem.getJumlahAbsen();
        holder.jumHarga.setText("= Rp "+subTotal);
    }

    @Override
    public int getItemCount() {
        return absenAL.size();
    }


}
