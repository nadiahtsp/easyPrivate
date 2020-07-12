package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.GuruMapel;
import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPencarianItem extends AppCompatActivity {
    private User user;
    TextView namaTV, jkTV, uniTV, lokasiTV,pengalamanMengajarTV,ipkTV,usiaTV,hargaTV;
    Button simpan;
    LinearLayout mapelLL;
    CustomUtility cu;
    RadioButton jamRB;
    Spinner tglSS;
    CircleImageView fotoCIV;
    ArrayList<JadwalAvailable> jaAL = new ArrayList<>();
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();
    private LinearLayout llJadwal;
    private Geocoder geocoder;
    private Pemesanan pesanan;
    private UserHelper uh;
    ArrayList<Integer> idRadioGroup = new ArrayList<>();

    private static final String TAG = "DetailPencarianItem";
    ArrayList<String> hari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pencarian_item);

        init();
        int idGuru = getIntent().getIntExtra("id_user", 0);
        ArrayList<String> hari = getIntent().getStringArrayListExtra("hariIntent");
        Log.d(TAG, "onCreate: hari size " + hari.size());
        getDetailGuru(idGuru);

    }

    public void init() {
        geocoder = new Geocoder(this, Locale.getDefault());
        namaTV = findViewById(R.id.namaTV);
        jkTV = findViewById(R.id.jenis_kelaminTV);
        cu = new CustomUtility(this);
        hargaTV = findViewById(R.id.hargaTV);
        fotoCIV = findViewById(R.id.civProfilePic);
        llJadwal = findViewById(R.id.LLjadwal);
        lokasiTV = findViewById(R.id.locationTV);
        tglSS = findViewById(R.id.spinnerTgl);
        pengalamanMengajarTV = findViewById(R.id.pengalamanMengajar);
        ipkTV = findViewById(R.id.ipk);
        usiaTV = findViewById(R.id.usiaTV);
        mapelLL = findViewById(R.id.mapelLL);
        uh = new UserHelper(this);
        pesanan = uh.retrievePemesanan();
        hari = getIntent().getStringArrayListExtra("hariIntent");
        Log.d(TAG, "init: size hari : " + hari.size());
        simpan= findViewById(R.id.buttonBelajar);
    }

    public void showDialog(){
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle("Form Tidak Lengkap");
        ad.setMessage("Mohon Pilih Jam Belajar Les");

        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }
    public void getUser(User user) {
        namaTV.setText(user.getName());

        jkTV.setText(user.getJenisKelamin());

        cu.putIntoImage(user.getAvatar(), fotoCIV);

        ipkTV.setText(String.valueOf(user.getPendaftaranGuru().get(0).getNilaiIpk()));

        //pengalaman ajar
        Integer pengalaman_ajar;
        pengalaman_ajar =  user.getPendaftaranGuru().get(0).getPengalamanMengajar();
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

        //usia
        String tgl_lahir = user.getTanggalLahir();
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

        //mapel
        ArrayList<GuruMapel> guruMapels = user.getGuruMapel();
        Pemesanan pemesanan = uh.retrievePemesanan();
        Integer id_mapel = pemesanan.getIdMapel();
        for (int i=0; i<guruMapels.size(); i++){
            MataPelajaran currMapel = guruMapels.get(i).getMataPelajaran();
            TextView mapel = new TextView(this);
            mapel.setText(currMapel.getNamaMapel());
            if (currMapel.getIdMapel() == id_mapel){
                mapel.setTypeface(mapel.getTypeface(), Typeface.BOLD);
                mapel.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
                mapel.setTextColor(this.getResources().getColor(R.color.white));
                //harga
                hargaTV.setText("Rp "+currMapel.getJenjang().getHargaPerPertemuan());
            }
            else {
                mapel.setTextColor(this.getResources().getColor(R.color.fontDark));
            }
            mapelLL.addView(mapel);
        }

        //alamat
        Address address = cu.getAddress(user.getAlamat().getLatitude(), user.getAlamat().getLongitude());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            sb.append(address.getAddressLine(i)).append("\n");//adress
        }
        sb.append(address.getLocality()).append(", "); //kecamatan
        sb.append(address.getSubAdminArea()).append("");//kota
        String result = "";
        result = sb.toString();

        lokasiTV.setText(result);


    }


    public void getDetailGuru(int id) {
        Call<User> guru = apiInterface.detailGuruById(id);
        ProgressDialog p = rci.getProgressDialog(this, "Menampilkan pemesanan");
        p.show();
        guru.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                p.dismiss();
                if (!response.isSuccessful()) {
                    return;
                }
                user = response.body();
                getUser(user);
                getJadwalAvailable();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void getJadwalAvailable() {
        Call<ArrayList<JadwalAvailable>> ja = apiInterface.getJadwalAvailable(user.getId(), 1, null, null, null);
        ja.enqueue(new Callback<ArrayList<JadwalAvailable>>() {
            @Override
            public void onResponse(Call<ArrayList<JadwalAvailable>> call, Response<ArrayList<JadwalAvailable>> response) {
                Log.d(TAG, "onResponse: jadwal "+response.message());
                if (!response.isSuccessful()) {

                    return;
                }
                jaAL = response.body();
                viewJadwalAvailable();
                getTglPertemuan();
            }

            @Override
            public void onFailure(Call<ArrayList<JadwalAvailable>> call, Throwable t) {
                Log.d(TAG, "onFailure: jadwal "+t.getMessage());
            }
        });
    }

    public boolean isHariAvailable(String hariStr) {
        if (hari.size() == 0) {
            return true;
        }
        for (int i = 0; i < hari.size(); i++) {
            String currHari = hari.get(i);
            if (currHari.equals(hariStr)) {
                Log.d(TAG, "isHariAvailable: harii " + hari.get(i));
                return true;
            }
        }
        return false;
    }

    public void insertJadwalAvailablePerHari(ArrayList<JadwalAvailable> jaPerHari) {
        Log.d(TAG, "insertJadwalAvailablePerHari: size " + jaPerHari.size());
        TextView hariTv = new TextView(this);
        hariTv.setText(jaPerHari.get(0).getHari());
        hariTv.setTextColor(Color.BLACK);
        RadioGroup rg = new RadioGroup(this);
        Integer id = ViewCompat.generateViewId();
        rg.setId(id);
        idRadioGroup.add(id);

        for (int i = 0; i < jaPerHari.size(); i++) {
            JadwalAvailable ja = jaPerHari.get(i);
            String jamStr = cu.reformatDateTime(ja.getStart(), "HH:mm:ss", "HH:mm");
            String jamEnd = cu.reformatDateTime(ja.getEnd(), "HH:mm:ss", "HH:mm");
            RadioButton jamCB = new RadioButton(this);
            jamCB.setText(jamStr + " s/d " + jamEnd);
            jamCB.setId(ViewCompat.generateViewId());
            jamCB.setTag(ja);
            rg.addView(jamCB);
        }
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 10);
        rg.setLayoutParams(params);
        llJadwal.addView(hariTv);
        llJadwal.addView(rg);

    }

    public void viewJadwalAvailable() {
        ArrayList<JadwalAvailable> jaPerHari = new ArrayList<>();
        for (int i = 0; i < jaAL.size(); i++) {

            JadwalAvailable ja = jaAL.get(i);

            if (i > 0) {
                JadwalAvailable jx = jaAL.get(i - 1);
                if (!ja.getHari().equals(jx.getHari())) {
                    Log.d(TAG, "viewJadwalAvailable: index " + i);
                    Log.d(TAG, "viewJadwalAvailable: hari : " + ja.getHari());
                    if (isHariAvailable(jx.getHari().toLowerCase())) {
                        insertJadwalAvailablePerHari(jaPerHari);
                    }
                    jaPerHari.clear();
                }
            }

            jaPerHari.add(ja);
            if (isHariAvailable(ja.getHari().toLowerCase())) {
                if (i == jaAL.size() - 1) {
                    Log.d(TAG, "viewJadwalAvailable: index " + i);
                    Log.d(TAG, "viewJadwalAvailable: hari : " + ja.getHari());
                    insertJadwalAvailablePerHari(jaPerHari);
                    jaPerHari.clear();
                }
            }
        }
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfFilled()){
                    callTambahPesanan();
                }
                else {
                    showDialog();
                }

            }
        });

    }

    private void getTglPertemuan() {
        Log.d(TAG, "getTglPertemuan: call ");
        List<String> tglPertemuannAr = new ArrayList<>();
        ArrayList<Integer> hariIntAr = new ArrayList<>();
        String formatStr = "EEEE - dd MMMM yyyy ";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.getDefault());
//        tglPertemuannAr.add("22 maret 2020");
//        tglPertemuannAr.add("31 maret 2020");
//        tglPertemuannAr.add("20 maret 2020");
//        tglPertemuannAr.add("21 maret 2020");
        for (int i =0; i < hari.size(); i++){
            String hariStr=hari.get(i);
            Integer hariInt = hariToInt(hariStr);
            hariIntAr.add(hariInt);
            Log.d(TAG, "getTglPertemuan: hari string "+hariStr);
        }

        Calendar calendar = Calendar.getInstance();
        long dateLong = calendar.getTimeInMillis();
        long limitLong = dateLong + (14 * 24 * 60 * 60 * 1000);
        while (dateLong <= limitLong) {
            dateLong += (24 * 60 * 60 * 1000);
            calendar.setTimeInMillis(dateLong);
            Log.d(TAG, "getTglPertemuan: date long "+dateLong);
            //Hari yang sedang di looping
            for(int i = 0; i < hariIntAr.size(); i ++) {
                Log.d(TAG, "getTglPertemuan: index tgl : " + i);
                Integer currHari = hariIntAr.get(i);

                if (calendar.get(Calendar.DAY_OF_WEEK) == currHari) {
                    Log.d(TAG, "getTglPertemuan: curr hari = day of week " +currHari);
                    String dateStr = sdf.format(calendar.getTime());
                    tglPertemuannAr.add(dateStr);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, tglPertemuannAr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tglSS.setAdapter(adapter);
    }

    private Integer hariToInt(String hariStr) {
        Integer hariInt=0;
                switch (hariStr) {
                    case "minggu":
                        hariInt = 1;
                        break;
                    case "senin":
                        hariInt = 2;
                        break;
                    case "selasa":
                        hariInt = 3;
                        break;
                    case "rabu":
                        hariInt = 4;
                        break;
                    case "kamis":
                        hariInt = 5;
                        break;
                    case "jumat":
                        hariInt = 6;
                        break;
                    case "sabtu":
                        hariInt = 7;
                        break;
                }
                return hariInt;

    }
    public boolean checkIfFilled(){
        ArrayList<Boolean> rbBol =new ArrayList<>();
        for (int i=0; i<idRadioGroup.size(); i++){
            RadioGroup rg = findViewById(idRadioGroup.get(i));
            Integer selectedId = rg.getCheckedRadioButtonId();
            Log.d(TAG, "checkIfFilled: sel id" +selectedId);
            if (selectedId==-1){
                return false;
            }
        }
        return true;

    }

    public void callTambahPesanan(){
        pesanan.setIdGuru(user.getId());
        CustomUtility cu = new CustomUtility(this);
        String firstMeet = cu.reformatDateTime(tglSS.getSelectedItem().toString(),"EEEE - dd MMMM yyyy ","yyyy-MM-dd HH:mm:ss");
        ArrayList<Integer> idJadwal =new ArrayList<>();
        for (int i=0; i<idRadioGroup.size(); i++){
        RadioGroup rg = findViewById(idRadioGroup.get(i));
            int selectedId = rg.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            Log.d(TAG, "callTambahPesanan: text rb "+radioButton.getText().toString());
            JadwalAvailable ja = (JadwalAvailable) radioButton.getTag();
            Log.d(TAG, "callTambahPesanan: id ja " +ja.getIdJadwalAvailable());
            idJadwal.add(ja.getIdJadwalAvailable());
        }


        Call<Pemesanan> pemesananCall = apiInterface.createPemesananBaru(pesanan.getIdMurid(),
                pesanan.getIdGuru(),
                pesanan.getIdMapel(),
                idJadwal,
                pesanan.getKelas(),
                firstMeet
                );
        pemesananCall.enqueue(new Callback<Pemesanan>() {
            @Override
            public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if(!response.isSuccessful()){

                    return;
                }
                Intent i = new Intent(DetailPencarianItem.this, DetailPemesanan.class);
                i.putExtra("id_pemesanan",response.body().getIdPemesanan());
                i.putExtra("alert_dialog",true);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(DetailPencarianItem.this,"Pemesanan Anda Gagal",Toast.LENGTH_LONG).show();

            }
        });
    }
}

