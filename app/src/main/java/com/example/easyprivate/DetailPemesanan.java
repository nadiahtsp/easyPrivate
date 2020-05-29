package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.fragments.PesananFragment;
import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemesanan extends AppCompatActivity {
    TextView namaTV, jkTV, uniTV, lokasiTV,tglTV,statusTV;
    CustomUtility cu;
    Button selesaiTV;
    LinearLayout jadwalLL;
    CircleImageView fotoCIV;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();
    private Geocoder geocoder;
    private Pemesanan pesanan;
    private static final String TAG = "DetailPemesanan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);
        int idPemesanan = getIntent().getIntExtra("id_pemesanan",0);
        init();
        getDetailPemesanan(idPemesanan);

    }

    public void init() {
        geocoder = new Geocoder(this, Locale.getDefault());
        namaTV = findViewById(R.id.namaTV);
        jkTV = findViewById(R.id.jenis_kelaminTV);
        cu = new CustomUtility(this);
        fotoCIV = findViewById(R.id.civProfilePic);;
        lokasiTV = findViewById(R.id.locationTV);
        tglTV = findViewById(R.id.tglTV);
        uniTV = findViewById(R.id.universitasTV);
        jadwalLL =findViewById(R.id.TVjadwal);
        statusTV=findViewById(R.id.statusTV);
        selesaiTV=findViewById(R.id.selesai);
    }

    public void showAlert(){
        AlertDialog ad = new AlertDialog.Builder(DetailPemesanan.this ).create();
        ad.setTitle("Anda Telah Memesan Guru "+pesanan.getGuru().getName());
        ad.setMessage("Mohon Menunggu Konfirmasi Guru");
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                dialogInterface.dismiss();

            }
        });
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }

    public void showDialog(){
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle("Akhiri Pemesanan");
        ad.setMessage("Apakah Anda Yakin Mengakhiri Pemesanan dengan Guru "+pesanan.getGuru().getName()+" ?");
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "Akhiri", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callUpdatePemesanan();
                finish();
            }
        });
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }

    public void getPemesanan(Pemesanan pemesanan){
        namaTV.setText(pemesanan.getGuru().getName());
        jkTV.setText(pemesanan.getGuru().getJenisKelamin());
        cu.putIntoImage(pemesanan.getGuru().getAvatar(), fotoCIV);
        uniTV.setText(pemesanan.getGuru().getUniversitas());
        Address address = cu.getAddress(pemesanan.getGuru().getAlamat().getLatitude(), pemesanan.getGuru().getAlamat().getLongitude());
        ;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            sb.append(address.getAddressLine(i)).append("\n");//adress
        }
        sb.append(address.getLocality()).append(", "); //kecamatan
        sb.append(address.getSubAdminArea()).append("");//kota
        String result = "";
        result = sb.toString();

        lokasiTV.setText(result);
        if (pemesanan.getFirstMeet() != null) {
            String tglPertemuanPertama = cu.reformatDateTime(pemesanan.getFirstMeet(), "yyyy-MM-dd HH:mm:ss", "EEEE - dd MMMM yyyy");
            tglTV.setText(tglPertemuanPertama);
        }
        for(int i=0; i< pesanan.getJadwalPemesananPerminggu().size(); i++){
            JadwalAvailable ja = pesanan.getJadwalPemesananPerminggu().get(i).getJadwalAvailable();

           View v = LayoutInflater.from(this).inflate(R.layout.item_card_schedule,jadwalLL,false);

           TextView hariTv,jamTv;
           hariTv=v.findViewById(R.id.hariTV);
           jamTv=v.findViewById(R.id.jamTV);

           hariTv.setText(ja.getHari());

           String start, end;
           start = cu.reformatDateTime(ja.getStart(),"HH:mm:ss","HH:mm");
            end = cu.reformatDateTime(ja.getEnd(),"HH:mm:ss","HH:mm");

            jamTv.setText(start + " - " +end);
            jadwalLL.addView(v);

            switch(pemesanan.getStatus()){
                case 0:
                    statusTV.setText("Menunggu Konfirmasi");
                    statusTV.setTextColor(DetailPemesanan.this.getResources().getColor(R.color.white));
                    statusTV.setBackgroundColor(DetailPemesanan.this.getResources().getColor(R.color.yellow));
                    break;
                case 1:
                    statusTV.setVisibility(View.GONE);
                    selesaiTV.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    statusTV.setText("Pemesanan Ditolak");
                    statusTV.setTextColor(DetailPemesanan.this.getResources().getColor(R.color.white));
                    statusTV.setBackgroundColor(DetailPemesanan.this.getResources().getColor(R.color.red));
                    break;
                case 3:
                    statusTV.setText("Pemesanan Selesai");
                    statusTV.setTextColor(DetailPemesanan.this.getResources().getColor(R.color.white));
                    statusTV.setBackgroundColor(DetailPemesanan.this.getResources().getColor(R.color.gray));
                    break;
            }

        }
    }


    public void getDetailPemesanan(int id) {
        Call<Pemesanan> detailPemesanan = apiInterface.detailPemesanan(id);
        ProgressDialog p = rci.getProgressDialog(this, "Menampilkan detail pemesanan");
        p.show();
        detailPemesanan.enqueue(new Callback<Pemesanan>() {
            @Override
            public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                p.dismiss();
                if (!response.isSuccessful()) {
                    return;
                }
                pesanan = response.body();
                getPemesanan(pesanan);

                if(getIntent().hasExtra("alert_dialog")){
                    if (getIntent().getBooleanExtra("alert_dialog",false)){
                        showAlert();
                        PemesananActivity.pemesananActivity.finish();
                        HasilPencarianActivity.hasilPencarian.finish();
                    }
                }

                selesaiTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog();
                    }
                });
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {

            }
        });

    }

    public void callUpdatePemesanan(){
        Call<Pemesanan> updatePemesanan = apiInterface.pemesananUpdate(pesanan.getIdPemesanan(),3);
        updatePemesanan.enqueue(new Callback<Pemesanan>() {
            @Override
            public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()) {
                    return;
                }
                pesanan = response.body();
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
