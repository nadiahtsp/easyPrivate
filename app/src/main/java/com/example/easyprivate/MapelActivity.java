package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.easyprivate.adapter.MapelAdapter;
import com.example.easyprivate.adapter.PemesananAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.JadwalPemesananPerminggu;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapelActivity extends AppCompatActivity {
    RecyclerView mapelRV;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    ArrayList<Pemesanan> pemesananAL =new ArrayList<>();
    private static final String TAG = "MapelActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapel);
        init();
        callPemesananMapel();
    }

    public void init(){
        mapelRV=findViewById(R.id.mapelRV);
        uh = new UserHelper(this);
    }

    private void callPemesananMapel() {
        Call<ArrayList<Pemesanan>> cPemesanan = apiInterface.pemesananFilter(null,
                uh.retrieveUser().getId(),
                null,
                1);
        cPemesanan.enqueue(new Callback<ArrayList<Pemesanan>>() {
            @Override
            public void onResponse(Call<ArrayList<Pemesanan>> call, Response<ArrayList<Pemesanan>> response) {
                Log.d(TAG, "onResponse: "+ response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pemesananAL = response.body();
                MapelAdapter ma = new MapelAdapter(MapelActivity.this,pemesananAL);
                mapelRV.setAdapter(ma);
                mapelRV.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onFailure(Call<ArrayList<Pemesanan>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
