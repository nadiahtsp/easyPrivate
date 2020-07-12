package com.example.easyprivate.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.DetailHistoryPembayaranActivity;
import com.example.easyprivate.DetailPembayaran;
import com.example.easyprivate.R;
import com.example.easyprivate.model.Pembayaran;
import com.example.easyprivate.model.PembayaranAbsen;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

public class HistoryPembayaranAdapter extends RecyclerView.Adapter<HistoryPembayaranAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Pembayaran> pembayarans = new ArrayList<>();
    private Geocoder geocoder;
    private CustomUtility cu;
    private static final String TAG = "HistoryPembayaranAdapte";

    public HistoryPembayaranAdapter(Context mContext, ArrayList<Pembayaran> pembayarans) {
        this.mContext = mContext;
        this.pembayarans = pembayarans;
        this.geocoder = new Geocoder(mContext);
        this.cu = new CustomUtility(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_car_history_pembayaran, parent,false);
        return new HistoryPembayaranAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pembayaran pembayaran =pembayarans.get(position);
        String bulan,date;
        bulan = cu.reformatDateTime(String.valueOf(pembayaran.getPeriodeBulan()),"MM","MMMM");
        date = bulan +" "+ pembayaran.getPeriodeTahun();
        holder.dateTV.setText(date);
        Log.d(TAG, "onBindViewHolder: bulan adapter "+pembayaran.getPeriodeBulan());
        Log.d(TAG, "onBindViewHolder: tahun adapter"+pembayaran.getPeriodeTahun());
        holder.bulanLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailHistoryPembayaranActivity.class);
                i.putExtra("bulan",pembayaran.getPeriodeBulan());
                i.putExtra("tahun",pembayaran.getPeriodeTahun());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pembayarans.size();
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
}
