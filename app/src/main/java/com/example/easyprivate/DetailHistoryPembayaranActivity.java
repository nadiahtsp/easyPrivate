package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easyprivate.adapter.DetailAbsenPembayaranAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.MidtransPembayaran;
import com.example.easyprivate.model.PembayaranAbsen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryPembayaranActivity extends AppCompatActivity {
    RecyclerView rvList;
    Button bayarBT;
    TextView total;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    CustomUtility cu;
    Intent intent;
    ArrayList<PembayaranAbsen> pembayaranAbsens =new ArrayList<>();
    private static final String TAG = "DetailHistoryPembayaran";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_pembayaran);
        init();
        callDetailHistoryPembayaran();
    }

    private void callDetailHistoryPembayaran() {
        Log.d(TAG, "callDetailHistoryPembayaran: bulan"+intent.getIntExtra("bulan",0));
        Log.d(TAG, "callDetailHistoryPembayaran: tahun "+intent.getIntExtra("tahun",0));
        Call<ArrayList<PembayaranAbsen>> absenpem = apiInterface.pemAbsen(uh.retrieveUser().getId(),
                null,
                null,
                Integer.parseInt(intent.getStringExtra("bulan")),
                Integer.parseInt(intent.getStringExtra("tahun")),
                null,
                null);
        absenpem.enqueue(new Callback<ArrayList<PembayaranAbsen>>() {
            @Override
            public void onResponse(Call<ArrayList<PembayaranAbsen>> call, Response<ArrayList<PembayaranAbsen>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pembayaranAbsens = response.body();
                DetailAbsenPembayaranAdapter pa = new DetailAbsenPembayaranAdapter(DetailHistoryPembayaranActivity.this,pembayaranAbsens);
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

    private void init() {
        rvList = findViewById(R.id.rvList);
        total=findViewById(R.id.totalbyr);
        intent = getIntent();
        cu = new CustomUtility(this);
        uh = new UserHelper(this);
    }
}
