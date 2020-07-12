package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.easyprivate.adapter.AbsenPembayaranAdapter;
import com.example.easyprivate.adapter.MapelAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.PembayaranAbsen;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    RecyclerView rvList;
    Button hispem;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    public static Activity pembayaranActivity;
    ArrayList<PembayaranAbsen> pembayaranAbsens =new ArrayList<>();
    private static final String TAG = "PembayaranActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        pembayaranActivity = this;
        init();
        callAbsenPembayaran();
        hispem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PembayaranActivity.this, HistoryPembayaranActivity.class);
                startActivity(i);
            }
        });
    }

    public void init(){
        rvList = findViewById(R.id.rvList);
        uh = new UserHelper(this);
        hispem = findViewById(R.id.hispem);
    }

    private void callAbsenPembayaran(){
        Call<ArrayList<PembayaranAbsen>> cPemAbsen = apiInterface.pemAbsen(uh.retrieveUser().getId(),
                null,
                null,
                null,
                null,
                "unpaid",
                "id_murid");
        cPemAbsen.enqueue(new Callback<ArrayList<PembayaranAbsen>>() {
            @Override
            public void onResponse(Call<ArrayList<PembayaranAbsen>> call, Response<ArrayList<PembayaranAbsen>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pembayaranAbsens = response.body();
                AbsenPembayaranAdapter pa = new AbsenPembayaranAdapter(PembayaranActivity.this,pembayaranAbsens);
                rvList.setAdapter(pa);
                rvList.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onFailure(Call<ArrayList<PembayaranAbsen>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
