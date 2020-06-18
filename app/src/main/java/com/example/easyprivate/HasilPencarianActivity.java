package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.easyprivate.adapter.RvAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.SawGuru;
import com.example.easyprivate.model.FilterGuru;
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
    public static Activity hasilPencarian;
    private UserHelper uh;
    private static final String TAG = "HasilPencarianActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian);
        hasilPencarian = this;

        init();
        callCariGuru();
    }

    private void init(){
        rvList = findViewById(R.id.rv_item_cari);
        if(currIntent==null) {
            currIntent = getIntent();
        }
        uh = new UserHelper(this);
        if (uh.retrieveUser()==null){
            Log.d(TAG, "init: user = null");
        }
        if (uh == null){
            Log.d(TAG, "init: user helper = null");
        }
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
        Call<FilterGuru> call = apiInterface.cariGuru(id_mapelInt,
                jk,
                hari,
                uh.retrieveUser().getAlamat().getLatitude(),
                uh.retrieveUser().getAlamat().getLongitude()
        );
        call.enqueue(new Callback<FilterGuru>() {
            @Override
            public void onResponse(Call<FilterGuru> call, Response<FilterGuru> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                FilterGuru fg = response.body();
                Log.d(TAG, "onResponse:response body size: "+response.body().getSawGuru().size());
                RvAdapter rvAdapter = new RvAdapter(HasilPencarianActivity.this,fg.getUser(),fg.getSawGuru());
                rvAdapter.setHari(hari);
                rvList.setAdapter(rvAdapter);
                rvList.setLayoutManager(new LinearLayoutManager(HasilPencarianActivity.this));
            }

            @Override
            public void onFailure(Call<FilterGuru> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });
    }
    public FilterGuru sortGuru(FilterGuru fguru){

        return null;
    }
}
