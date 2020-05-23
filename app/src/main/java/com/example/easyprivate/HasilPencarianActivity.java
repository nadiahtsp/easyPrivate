package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.easyprivate.adapter.RvAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilPencarianActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private  ArrayList<User> userArrayList = new ArrayList<>();
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Intent currIntent;
    private static final String TAG = "HasilPencarianActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian);

        init();
        callCariGuru();
    }

    private void init(){
        rvList = findViewById(R.id.rv_item_cari);
        currIntent = getIntent();

    }

    private void callCariGuru(){
        Integer id_mapelInt = currIntent.getIntExtra("id_mapel",0);
        if (id_mapelInt==0){
            id_mapelInt =null;
        }
        ArrayList<String> hari = currIntent.getStringArrayListExtra("hari");




        String jk = "";
        if ( !currIntent.getStringExtra("jenisKelaminStr").equals("-") ){
            jk =  currIntent.getStringExtra("jenisKelaminStr");
        }
        Call<ArrayList<User>> call = apiInterface.cariGuru(id_mapelInt,
                jk,
                hari
        );
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
             userArrayList = response.body();
                Log.d(TAG, "onResponse:response body size: "+response.body().size());
                RvAdapter rvAdapter = new RvAdapter(HasilPencarianActivity.this,userArrayList);
                rvAdapter.setHari(hari);
                rvList.setAdapter(rvAdapter);
                rvList.setLayoutManager(new LinearLayoutManager(HasilPencarianActivity.this));
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });
    }
}
