package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.easyprivate.adapter.AbsenPembayaranAdapter;
import com.example.easyprivate.adapter.DetailAbsenPembayaranAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.PembayaranAbsen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaran extends AppCompatActivity {
    RecyclerView rvList;
    Button bayarBT;
    TextView total;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    Intent intent;
    ArrayList<PembayaranAbsen> pembayaranAbsens =new ArrayList<>();
    private static final String TAG = "DetailPembayaran";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);
        init();
        callDetailPembayaran();

    }

    private void init() {
        rvList = findViewById(R.id.rvList);
        total=findViewById(R.id.totalbyr);
        bayarBT = findViewById(R.id.bayarBT);
        intent = getIntent();
        uh = new UserHelper(this);
    }
    private void callDetailPembayaran() {
        Call<ArrayList<PembayaranAbsen>> absenpem = apiInterface.pemAbsen(uh.retrieveUser().getId(),
                null,
                null,
                intent.getIntExtra("bulan",0),
                intent.getIntExtra("tahun",0),
                null);
        absenpem.enqueue(new Callback<ArrayList<PembayaranAbsen>>() {
            @Override
            public void onResponse(Call<ArrayList<PembayaranAbsen>> call, Response<ArrayList<PembayaranAbsen>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pembayaranAbsens = response.body();
                DetailAbsenPembayaranAdapter pa = new DetailAbsenPembayaranAdapter(DetailPembayaran.this,pembayaranAbsens);
                rvList.setAdapter(pa);
                rvList.setLayoutManager(new LinearLayoutManager(mContext));
                int totalByr=0;
                for (int i =0;i<pembayaranAbsens.size();i++){
                    PembayaranAbsen pabs=pembayaranAbsens.get(i);
                    int jumper,harga;
                    jumper = pabs.getJumlahAbsen();
                    harga=pabs.getPemesanan().getMataPelajaran().getJenjang().getHargaPerPertemuan();
                    totalByr+= jumper*harga;
                }
                total.setText("Rp "+totalByr);
            }

            @Override
            public void onFailure(Call<ArrayList<PembayaranAbsen>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

}
