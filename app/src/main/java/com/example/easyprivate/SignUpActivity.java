package com.example.easyprivate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private FrameLayout flMaps;
    private EditText datePicker,name,email,no_telf,alamat;
    private RadioGroup jenis_kelamin;
    private Button simpan,set_alamat;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private GoogleMap gMap;
    private Marker currMarker;
    private LatLng alamatlatlon;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    private boolean mLocationPermission= false;
    private static final int DEFAULT_MAP_ZOOM = 10;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cekForm()) {
                    callUser();
                }

            }
        });
//        set_alamat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SignUpActivity.this, MapActivity.class);
//                startActivityForResult(i,1);
//            }
//        });

    }
    private  boolean cekForm(){
        String nameStr="",emailStr="",tanggal_lahirStr="",alamatStr="",no_telStr="";
        nameStr = name.getText().toString();
        emailStr = account.getEmail();
        Integer jenis_kelaminInt = jenis_kelamin.getCheckedRadioButtonId();
       no_telStr = no_telf.getText().toString();
       alamatStr = alamat.getText().toString();
       tanggal_lahirStr = datePicker.getText().toString() ;

       if (nameStr.equals("")||
               emailStr.equals("")||
               no_telStr.equals("")||
               alamatStr.equals("")||
               tanggal_lahirStr.equals("")||
               jenis_kelaminInt==null||
               alamatlatlon==null){
           Toast.makeText(this,"Lengkapi yang belum terisi",Toast.LENGTH_LONG).show();
           return false;
        }
        else {
            return true;
       }

    }
    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                if (currMarker!= null){
                    currMarker.remove();
                }
                Double lat = data.getDoubleExtra("latitude",0.0);
                Double lon = data.getDoubleExtra("longitude", 0.0);
                alamatlatlon = new LatLng(lat,lon);
                Log.d(TAG, "onActivityResult: lat:"+alamatlatlon.latitude);
                Log.d(TAG, "onActivityResult: lon"+alamatlatlon.longitude);
                gMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(alamatlatlon));
                currMarker = gMap.addMarker(new MarkerOptions().position(alamatlatlon).title("alamat saya")) ;
            }
        }
    }

    private void init(){
        account = GoogleSignIn.getLastSignedInAccount(this);
//        flMaps = findViewById(R.id.flMaps);
        initMap();
        datePicker = findViewById(R.id.date_picker);
        name = findViewById(R.id.txtName);
        name.setText(account.getDisplayName());
        email = findViewById(R.id.txtEmail);
        email.setText(account.getEmail());
        email.setFocusable(false);
        no_telf = findViewById(R.id.txtMob);
        alamat = findViewById(R.id.alamat);
        jenis_kelamin = findViewById(R.id.jenisKelaminRGroup);
        simpan = findViewById(R.id.btnSimpan);
//        set_alamat = findViewById(R.id.setAlamat);

    }
//    private void showMaps(){
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.flMaps, new MapFragment(),"mapFrag")
//                .commit();
//    }

    private void showDate(){
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
       DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                datePicker.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private String reformatDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        try {
            Date date = sdf.parse(dateStr);

            sdf.applyPattern("yyyy-MM-dd");
            String tanggal = sdf.format(date);
            return tanggal;
        } catch (ParseException e) {
            Log.d(TAG, "reformatDate: " + e.getMessage());
            return "2000-01-01";
        }
    }
    private void askLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermission = true;
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
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
                break;
        }
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent i = new Intent(SignUpActivity.this, MapActivity.class);
                        startActivityForResult(i,1);
                    }
                });
            }
        });

    }
    private void callUser(){
        String dateStr = reformatDate(datePicker.getText().toString());
        int selectedId = jenis_kelamin.getCheckedRadioButtonId();
        RadioButton rb = findViewById(selectedId);
        String jenisKelaminStr = rb.getText().toString();
        String savePhoto = "";
        try {
            savePhoto =  account.getPhotoUrl().toString();
            Log.d(TAG, "callUser: "+savePhoto);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        Call<User> call = apiInterface.postDaftar(account.getEmail(),
                name.getText().toString(),
                savePhoto,
                jenisKelaminStr,
                dateStr,
                no_telf.getText().toString(),
                alamatlatlon.latitude,
                alamatlatlon.longitude,
                alamat.getText().toString());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d(TAG, "onResponse: "+response.message());
                    Intent mAccount = new Intent(SignUpActivity.this, MainNavigation.class);
                    User user = response.body();
                    UserHelper uh = new UserHelper(SignUpActivity.this);
                    uh.storeUser(user);
                    startActivity(mAccount);
                    finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                    t.printStackTrace();
                    Toast.makeText(SignUpActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            });
    }
}
