package com.example.easyprivate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private Button simpan_alamat;
    private SearchView seacrhET;
    private GoogleMap gMap;
    private boolean mLocationPermission= false;
    ArrayList<String> suggestionSearch = new ArrayList<>();
    private static final int DEFAULT_MAP_ZOOM = 10;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    ArrayAdapter<String> adapter;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private FusedLocationProviderClient mflfc;
    private static final String TAG = "MapActivity";
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);
            init();
            mLocationPermission = checkPermission();
            initMap();
            if (!mLocationPermission){
                askLocationPermission();
            }
            else{
                getDevicelocation();
            }


            simpan_alamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatLng latlng = gMap.getCameraPosition().target;
                    Intent i = new Intent();
                    i.putExtra("latitude",latlng.latitude);
                    i.putExtra("longitude",latlng.longitude);
                    setResult(RESULT_OK,i);
                    finish();
                }
            });
        }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = seacrhET.getQuery().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }
        suggestionSearch.clear();
        for(int i=0;i<list.size();i++){
            Address address = list.get(i);
            suggestionSearch.add(address.getAddressLine(i));
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()),15));

    }

    private void init(){
        simpan_alamat = findViewById(R.id.simpanAlamat);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, suggestionSearch);
        seacrhET = findViewById(R.id.searchSV);
       // seacrhET.setVisibility(View.GONE);
        seacrhET.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                geoLocate();
            return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void askLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermission = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                for(int i = 0; i < grantResults.length; i++) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        mLocationPermission = false;
                        return;
                    }
                }
                mLocationPermission = true;
                getDevicelocation();
                if (mLocationPermission){
                    if (ActivityCompat.checkSelfPermission(MapActivity.this ,Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    gMap.setMyLocationEnabled(true);
                }
                break;
        }
    }
    private void getDevicelocation() {
        Log.d(TAG, "getDevicelocation: called");
        mflfc = LocationServices.getFusedLocationProviderClient(this);

        try {
            Log.d(TAG, "getDevicelocation: "+mLocationPermission);
            if (mLocationPermission) {
                Task location = mflfc.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: task: success");
                            Location currentLocation = (Location) task.getResult();
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),15));
                        }
                        else {
                            Log.d(TAG, "onComplete: Failed");
                        }
                    }
                });

            }
        }catch (SecurityException e){
            Log.d(TAG, "getDevicelocation: "+e.getMessage());
            e.printStackTrace();
        }

    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.setMyLocationEnabled(true);
                View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).
                        getParent()).findViewById(Integer.parseInt("2"));

                // and next place it, for exemple, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                // position on right bottom
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                rlp.setMargins(0, 200, 100, 0);
            }
        });
    }
}
