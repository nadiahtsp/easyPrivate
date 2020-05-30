package com.example.easyprivate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.R;
import com.example.easyprivate.UserHelper;
import com.example.easyprivate.adapter.PemesananAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananFragment extends Fragment {
    RecyclerView rvList;
    ArrayList<Pemesanan> pemesananAL =new ArrayList<>();
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private View v;
    private User user;
    UserHelper uh;
    Context mContext;
    private boolean hasBeenRefreshed = true;

    @Override
    public void onResume() {
        super.onResume();
        if(hasBeenRefreshed == false){
            pemesananAL.clear();
            rvList.setAdapter(null);

            callPemesan();
            hasBeenRefreshed = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hasBeenRefreshed = false;
    }
    private static final String TAG = "PesananFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_pesanan, container, false);
        mContext = v.getContext();
        if(mContext==null){
            Log.d(TAG, "onCreateView: contex = null");
        }
        
        init(v);
        callPemesan();
        return v;
    }

    private void init(View v){
        rvList = v.findViewById(R.id.rvList);
        uh = new UserHelper(mContext);
        if (uh == null){
            Log.d(TAG, "init: user helper = null");
        }
        if (uh.retrieveUser()==null){
            Log.d(TAG, "init: user = null");
            
        }
        user = uh.retrieveUser();
    }

    public void callPemesan(){
        Call<ArrayList<Pemesanan>> cPemesanan = apiInterface.pemesananFilter(null,
                user.getId(),
                null,
                null);
        cPemesanan.enqueue(new Callback<ArrayList<Pemesanan>>() {
            @Override
            public void onResponse(Call<ArrayList<Pemesanan>> call, Response<ArrayList<Pemesanan>> response) {
                Log.d(TAG, "onResponse: "+ response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pemesananAL = response.body();
                PemesananAdapter pa = new PemesananAdapter(mContext,pemesananAL);
                rvList.setAdapter(pa);
                rvList.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onFailure(Call<ArrayList<Pemesanan>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
