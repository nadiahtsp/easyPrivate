package com.example.easyprivate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.R;
public class PesananFragment extends Fragment {
    RecyclerView rvPesanan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesanan, container, false);
        Context mContext = v.getContext();

        init(v);

        return v;
    }

    private void init(View v){
        rvPesanan = v.findViewById(R.id.rvPesanan);
    }
}
