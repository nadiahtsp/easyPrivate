package com.example.easyprivate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyprivate.R;


public class AbsenFragment extends Fragment {
    RecyclerView rvAbsen;
    ImageButton iBtnQRScanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_absen, container, false);Context mContext = v.getContext();

        init(v);

        return v;
    }

    private void init(View v){

    }
}
