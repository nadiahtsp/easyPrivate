package com.example.easyprivate.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.DetailPencarianItem;
import com.example.easyprivate.R;
import com.example.easyprivate.model.SawGuru;
import com.example.easyprivate.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
private Context mContext;
private ArrayList<User> userArrayList = new ArrayList<>();
private Geocoder geocoder;
private CustomUtility cu;
private ArrayList<SawGuru> jarakAl = new ArrayList<>();
private ArrayList<String> hari = new ArrayList<>();

    public ArrayList<String> getHari() {
        return hari;
    }

    public void setHari(ArrayList<String> hari) {
        this.hari = hari;
    }


    public RvAdapter(Context mContext, ArrayList<User> userArrayList,ArrayList<SawGuru> jarakAl) {
        this.mContext = mContext;
        this.userArrayList = userArrayList;
        this.jarakAl = jarakAl;
        this.geocoder = new Geocoder(mContext, Locale.getDefault());
        this.cu = new CustomUtility(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView namaTV,jenisKelaminTV,locationTV,jarakTV;
    private CircleImageView fotoCIV;
    private LinearLayout itemLL;


        public ViewHolder(@NonNull View iv) {
            super(iv);
            namaTV = iv.findViewById(R.id.namaTV);
            jenisKelaminTV=iv.findViewById(R.id.jenis_kelaminTV);
            locationTV= iv.findViewById(R.id.locationTV);
            jarakTV=iv.findViewById(R.id.jarakTV);
            fotoCIV=iv.findViewById(R.id.fotoCIV);
            itemLL=iv.findViewById(R.id.itemLL);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.hasil_pencarian_item, parent,false);
        return new RvAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        SawGuru jarak = jarakAl.get(position);
        String result ="";
        holder.namaTV.setText(user.getName());
        holder.jenisKelaminTV.setText(user.getJenisKelamin());
        if (user.getAvatar() != null ) {
            cu.putIntoImage(user.getAvatar(), holder.fotoCIV);
        }
        Address address = getDetailAlamat(user.getAlamat().getLatitude(),user.getAlamat().getLongitude());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            sb.append(address.getAddressLine(i)).append("\n");//adress
        }
        sb.append(address.getLocality()).append(", "); //kecamatan
        sb.append(address.getSubAdminArea()).append("");//kota

        result = sb.toString();
        holder.locationTV.setText(result);
        holder.jarakTV.setText(String. format("%.1f",jarak.getJarakHaversine())+" Km");
        holder.itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailPencarianItem.class);
                i.putExtra("id_user",user.getId());
                i.putExtra("hariIntent",hari);
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public Address getDetailAlamat(Double latitude, Double longitude){
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
            if (addressList != null && addressList.size() > 0) {
                return addressList.get(0);
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
