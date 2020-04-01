package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class SignUp extends AppCompatActivity {
    private FrameLayout flMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        flMaps = findViewById(R.id.flMaps);
        showMaps();
    }

    private void showMaps(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMaps, new MapFragment(),"mapFrag")
                .commit()
        ;
    }

}
