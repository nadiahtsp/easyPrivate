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
import com.example.easyprivate.DetailPembayaran;
import com.example.easyprivate.DetailPencarianItem;
import com.example.easyprivate.R;
import com.example.easyprivate.model.Absen;
import com.example.easyprivate.model.PembayaranAbsen;

import java.util.ArrayList;

public class AbsenPembayaranAdapter extends RecyclerView.Adapter<AbsenPembayaranAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<PembayaranAbsen> absenAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public AbsenPembayaranAdapter(Context mContext, ArrayList<PembayaranAbsen> absenAL) {
        this.mContext = mContext;
        this.absenAL = absenAL;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTV,status;
        private LinearLayout bulanLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.dateTV);
            bulanLL = itemView.findViewById(R.id.dateDetailLL);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_list_pembayaran, parent,false);
        return new AbsenPembayaranAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PembayaranAbsen absen = absenAL.get(position);
        String bulan,date;
        bulan = cu.reformatDateTime(String.valueOf(absen.getBulan()),"MM","MMMM");
        date = bulan +" "+ absen.getTahun();
        holder.dateTV.setText(date);
        holder.bulanLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailPembayaran.class);
                i.putExtra("bulan",absen.getBulan());
                i.putExtra("tahun",absen.getTahun());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return absenAL.size();
    }


}
