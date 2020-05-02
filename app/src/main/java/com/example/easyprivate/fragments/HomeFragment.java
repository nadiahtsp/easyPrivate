package com.example.easyprivate.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyprivate.PemesananActivity;
import com.example.easyprivate.R;


public class HomeFragment extends Fragment {
    private LinearLayout cari_guru,jadwal,guru_saya;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = v.getContext();
        init(v);
        cari_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PemesananActivity.class);
                mContext.startActivity(i);
            }
        });
        return v;
    }

    private void init(View v){
        cari_guru = v.findViewById(R.id.cari_guru);
        jadwal = v.findViewById(R.id.jadwal);
        guru_saya = v.findViewById(R.id.guruSaya);
    }
}
