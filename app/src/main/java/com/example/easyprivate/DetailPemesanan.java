package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.easyprivate.model.GuruMapel;
import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemesanan extends AppCompatActivity {
    TextView namaTV, jkTV, uniTV, lokasiTV,tglTV,statusTV,pengalamanMengajarTV,ipkTV,usiaTV;
    CustomUtility cu;
    Button selesaiTV;
    TextView hargaTV;
    UserHelper uh;
    LinearLayout mapelLL;
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
        uh = new UserHelper(this);
        hargaTV = findViewById(R.id.hargaTV);
        cu = new CustomUtility(this);
        fotoCIV = findViewById(R.id.civProfilePic);;
        lokasiTV = findViewById(R.id.locationTV);
        tglTV = findViewById(R.id.tglTV);
        pengalamanMengajarTV = findViewById(R.id.pengalamanMengajar);
        ipkTV = findViewById(R.id.ipk);
        usiaTV = findViewById(R.id.usiaTV);
        mapelLL = findViewById(R.id.mapelLL);
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
//        uniTV.setText(pemesanan.getGuru().getUniversitas());
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

        ipkTV.setText(String.valueOf(pemesanan.getGuru().getPendaftaranGuru().get(0).getNilaiIpk()));

        //pengalaman ajar
        Integer pengalaman_ajar;
        pengalaman_ajar =  pemesanan.getGuru().getPendaftaranGuru().get(0).getPengalamanMengajar();
        Double pengalaman_ajar_double = Double.valueOf(pengalaman_ajar);
        Double tahun_ajar = pengalaman_ajar_double/12;
        Integer pembulatan = tahun_ajar.intValue();
        Integer modulus = pengalaman_ajar%12;
        if (pengalaman_ajar>12 && modulus>0) {
            pengalamanMengajarTV.setText(pembulatan + " Tahun " + modulus + " Bulan");
        }
        else if(modulus==0){
            pengalamanMengajarTV.setText(pembulatan+" Tahun");
        }
        else{
            pengalamanMengajarTV.setText(modulus+" Bulan");
        }

        //mapel
        ArrayList<GuruMapel> guruMapels = pemesanan.getGuru().getGuruMapel();
        Pemesanan getmapel = uh.retrievePemesanan();
        Integer id_mapel = getmapel.getIdMapel();
        for (int i=0; i<guruMapels.size(); i++){
            MataPelajaran currMapel = guruMapels.get(i).getMataPelajaran();
            TextView mapel = new TextView(this);
            mapel.setText(currMapel.getNamaMapel());
            if (currMapel.getIdMapel() == id_mapel){
                mapel.setTypeface(mapel.getTypeface(), Typeface.BOLD);
                mapel.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
                mapel.setTextColor(this.getResources().getColor(R.color.white));
            }
            else {
                mapel.setTextColor(this.getResources().getColor(R.color.fontDark));
            }
            mapelLL.addView(mapel);
        }

        //harga
        hargaTV.setText("Rp "+pemesanan.getMataPelajaran().getJenjang().getHargaPerPertemuan());

        //usia
        String tgl_lahir = pemesanan.getGuru().getTanggalLahir();
        Calendar now = Calendar.getInstance();

        String tahun = cu.reformatDateTime(tgl_lahir,"yyyy-MM-dd", "yyyy");
        String bulan = cu.reformatDateTime(tgl_lahir,"yyyy-MM-dd", "MM");
        String hari = cu.reformatDateTime(tgl_lahir,"yyyy-MM-dd", "dd");


        Calendar tgl_lahir_cal = Calendar.getInstance();
        tgl_lahir_cal.set(Calendar.YEAR,Integer.parseInt(tahun));
        tgl_lahir_cal.set(Calendar.MONTH,Integer.parseInt(bulan)-1);
        tgl_lahir_cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(hari));

        Long nowLong = now.getTime().getTime();
        Long tgl_lahir_long = tgl_lahir_cal.getTime().getTime();

        Long selisih = nowLong - tgl_lahir_long;
        Log.d(TAG, "getUser: selisih " +selisih);

        Long usiaLong = selisih/1000/60/60/24/365;
        Log.d(TAG, "getUser: usiaLong " + usiaLong);
        usiaTV.setText(usiaLong + " Tahun");


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
                Log.d(TAG, "onResponse: pemesanan detail "+response.message());
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
                Log.d(TAG, "onFailure: pemesanan detail "+t.getMessage());
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
