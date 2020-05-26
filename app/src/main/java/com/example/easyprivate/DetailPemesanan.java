package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
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
                   statusTV.setText("Pemesanan Diterima");
                    statusTV.setTextColor(DetailPemesanan.this.getResources().getColor(R.color.white));
                    statusTV.setBackgroundColor(DetailPemesanan.this.getResources().getColor(R.color.green));
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
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {

            }
        });

    }
}
