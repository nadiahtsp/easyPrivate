package com.example.easyprivate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.QrCodeActivity;
import com.example.easyprivate.R;
import com.example.easyprivate.UserHelper;
import com.example.easyprivate.adapter.PemesananAbsenAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AbsenFragment extends Fragment {
    RecyclerView absenRV;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    ArrayList<Pemesanan> pemesananAL = new ArrayList<>();
    private static final String TAG = "QrCodeActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_absen, container, false);
        mContext = v.getContext();

        init(v);
        callPemesananMapel();
        return v;
    }

    public void init(View v) {
        absenRV = v.findViewById(R.id.absenRV);
        uh = new UserHelper(mContext);
    }

    private void callPemesananMapel() {
        Call<ArrayList<Pemesanan>> cPemesanan = apiInterface.pemesananFilter(null,
                uh.retrieveUser().getId(),
                null,
                1);
        cPemesanan.enqueue(new Callback<ArrayList<Pemesanan>>() {
            @Override
            public void onResponse(Call<ArrayList<Pemesanan>> call, Response<ArrayList<Pemesanan>> response) {
                Log.d(TAG, "onResponse: " + response.message());
                if (!response.isSuccessful()) {
                    return;
                }
                pemesananAL = response.body();
                PemesananAbsenAdapter ma = new PemesananAbsenAdapter(mContext, pemesananAL);
                absenRV.setAdapter(ma);
                absenRV.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onFailure(Call<ArrayList<Pemesanan>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
