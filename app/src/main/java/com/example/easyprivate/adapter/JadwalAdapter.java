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
import com.example.easyprivate.DetailPemesanan;
import com.example.easyprivate.R;
import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.JadwalPemesananPerminggu;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.CamcorderProfile.get;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<JadwalPemesananPerminggu> jappAL = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;

    public JadwalAdapter(Context mContext, ArrayList<JadwalPemesananPerminggu> jappAL) {
        this.mContext = mContext;
        this.jappAL = jappAL;
        this.cu=new CustomUtility(mContext);
        this.geocoder = new Geocoder(mContext);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView namaTV,mapelTV,jamTV,hariTV;
        private CircleImageView fotoCIV;
        private LinearLayout itemLL,hariLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTV = itemView.findViewById(R.id.namaTV);
            mapelTV=itemView.findViewById(R.id.mapelTV);
            fotoCIV=itemView.findViewById(R.id.fotoCIV);
            jamTV=itemView.findViewById(R.id.jamTV);
            itemLL=itemView.findViewById(R.id.itemTV);
            hariLL=itemView.findViewById(R.id.hariLL);
            hariTV=itemView.findViewById(R.id.hariTV);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_jadwal, parent,false);
        return new JadwalAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JadwalPemesananPerminggu japp = jappAL.get(position);
        String result ="";
        holder.namaTV.setText(japp.getPemesanan().getGuru().getName());
        holder.mapelTV.setText(japp.getPemesanan().getMataPelajaran().getNamaMapel());
        if (japp.getPemesanan().getGuru().getAvatar() != null ) {
            cu.putIntoImage(japp.getPemesanan().getGuru().getAvatar(), holder.fotoCIV);
        }
        String start,end;
        start = cu.reformatDateTime(japp.getJadwalAvailable().getStart(),"HH:mm:ss","HH:mm");
        end = cu.reformatDateTime(japp.getJadwalAvailable().getEnd(),"HH:mm:ss","HH:mm");
        holder.jamTV.setText(start+"-"+end);
        holder.itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailPemesanan.class);
                i.putExtra("id_pemesanan",japp.getIdPemesanan());
                mContext.startActivity(i);
            }
        });
        if (position>0){
            JadwalPemesananPerminggu jx = jappAL.get(position-1);
            if(!jx.getJadwalAvailable().getHari().equals(japp.getJadwalAvailable().getHari())){
                holder.hariTV.setText(japp.getJadwalAvailable().getHari());
                holder.hariLL.setVisibility(View.VISIBLE);
            }

        }
        else if (position==0){
            holder.hariTV.setText(japp.getJadwalAvailable().getHari());
            holder.hariLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return jappAL.size();
    }



}
