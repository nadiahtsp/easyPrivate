package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.easyprivate.adapter.HistoryAbsenAdapter;
import com.example.easyprivate.adapter.MapelAdapter;
import com.example.easyprivate.adapter.PemesananAbsenAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Absen;
import com.example.easyprivate.model.Pemesanan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrCodeActivity extends AppCompatActivity {
    ImageView qrCodeIv;
    RecyclerView rvList;
    Pemesanan currPemesanan;
    CustomUtility cu;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    Pemesanan pem ;
    ArrayList<Pemesanan> pemesananAL = new ArrayList<>();
    int idPemesanan ;
    private static final String TAG = "QrCodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        idPemesanan = getIntent().getIntExtra("id_pemesanan",0);
        init();
        callPemesananbsen();
    }

    private void init() {
        qrCodeIv = findViewById(R.id.qrCodeIv);
        uh = new UserHelper(this);
        rvList = findViewById(R.id.rvList);
        cu = new CustomUtility(this);
    }

    public void callPemesananbsen() {
        Call<Pemesanan> cPemesanan = apiInterface.detailPemesanan(idPemesanan);
        cPemesanan.enqueue(new Callback<Pemesanan>() {
            @Override
            public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                Log.d(TAG, "onResponse: qrcode" + response.message());
                if (!response.isSuccessful()) {
                    return;
                }

                pem = response.body();
                Pemesanan pms = response.body();
                pms.setMurid(null);
                pms.setGuru(null);
                pms.setMataPelajaran(null);
                pms.setJadwalPemesananPerminggu(null);
                String jsonPemesanan = cu.pemesananToJson(pms);

                Bitmap bitmapQrCode = cu.encodeToQrCode(jsonPemesanan,700);

                //Menampilkan qr code ke dalam image view
                qrCodeIv.setImageBitmap(bitmapQrCode);
                callAbsen();
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void callAbsen(){
        Call<ArrayList<Absen>> cAbsen = apiInterface.absenFilter(
                null,
                pem.getIdPemesanan(),
                null,
                null,
                null,
                null
        );
        cAbsen.enqueue(new Callback<ArrayList<Absen>>() {
            @Override
            public void onResponse(Call<ArrayList<Absen>> call, Response<ArrayList<Absen>> response) {
                Log.d(TAG, "onResponse: history"+response.message());
                if (!response.isSuccessful()) {
                    return;
                }

                ArrayList<Absen> abs = response.body();
                HistoryAbsenAdapter historyAbsen = new HistoryAbsenAdapter(QrCodeActivity.this,abs);
                rvList.setAdapter(historyAbsen);
                rvList.setLayoutManager(new LinearLayoutManager(QrCodeActivity.this));
            }

            @Override
            public void onFailure(Call<ArrayList<Absen>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +t.getMessage());
            }
        });
    }


}
