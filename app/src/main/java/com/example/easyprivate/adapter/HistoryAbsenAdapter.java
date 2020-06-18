package com.example.easyprivate.adapter;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.R;
import com.example.easyprivate.model.Absen;
import com.example.easyprivate.model.JadwalPemesananPerminggu;

import java.util.ArrayList;

public class HistoryAbsenAdapter extends RecyclerView.Adapter<HistoryAbsenAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Absen> absenAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public HistoryAbsenAdapter(Context mContext, ArrayList<Absen> absenAL) {
        this.mContext = mContext;
        this.absenAL = absenAL;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.dateTV);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_history_absen, parent,false);
        return new HistoryAbsenAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Absen absen = absenAL.get(position);
        String hari;
        hari = cu.reformatDateTime(absen.getWaktuAbsen(),"yyyy-MM-dd HH:mm:ss","EEEE, dd MMMM yyyy - HH:mm");
        holder.dateTV.setText(hari);

    }

    @Override
    public int getItemCount() {
        return absenAL.size();
    }


}
