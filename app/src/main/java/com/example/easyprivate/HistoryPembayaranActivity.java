package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.easyprivate.adapter.AbsenPembayaranAdapter;
import com.example.easyprivate.adapter.HistoryPembayaranAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Pembayaran;
import com.example.easyprivate.model.PembayaranAbsen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPembayaranActivity extends AppCompatActivity {
    RecyclerView rvList;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    ArrayList<Pembayaran> pembayarans =new ArrayList<>();
    private static final String TAG = "HistoryPembayaran";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pembayaran);
        init();
        callHistoryPembayaran();
    }

    private void callHistoryPembayaran() {
        Call<ArrayList<Pembayaran>> cpem = apiInterface.pembayaranSukses(uh.retrieveUser().getId(),
                null,
                null,
                null,
                200);
        cpem.enqueue(new Callback<ArrayList<Pembayaran>>() {
            @Override
            public void onResponse(Call<ArrayList<Pembayaran>> call, Response<ArrayList<Pembayaran>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pembayarans = response.body();
                HistoryPembayaranAdapter hpa = new HistoryPembayaranAdapter(HistoryPembayaranActivity.this,pembayarans);
                rvList.setAdapter(hpa);
                rvList.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onFailure(Call<ArrayList<Pembayaran>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void init(){
        rvList = findViewById(R.id.rvList);
        uh = new UserHelper(this);
    }
}
