package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Jenjang;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.User;
import com.google.android.gms.common.api.Api;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity {
    EditText nama,tanggal_pertemuan;
    Spinner jenjangSP,kelasSP,mapelSP,jenis_kelaminSP;
    CheckBox senin,selasa,rabu,kamis,jumat,sabtu,minggu;
    ArrayList<Jenjang> jenjangAL;
    ArrayList<MataPelajaran> mapelAL;
    private Integer id_mapel = null;

    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        init();
        tanggal_pertemuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });
        callJenjang();
        getJenisKelamin();
    }

    private void init(){
        nama = findViewById(R.id.namaMuridET);
        tanggal_pertemuan = findViewById(R.id.tanggalPertemuanET);
        jenjangSP = findViewById(R.id.spinner_jenjang);
        kelasSP = findViewById(R.id.spinner_kelas);
        mapelSP = findViewById(R.id.spinner_mapel);
        jenis_kelaminSP = findViewById(R.id.spinner_jeniskelaminguru);
        senin = findViewById(R.id.cbSenin);
        selasa = findViewById(R.id.cbSelasa);
        rabu = findViewById(R.id.cbRabu);
        kamis = findViewById(R.id.cbKamis);
        jumat = findViewById(R.id.cbJumat);
        sabtu = findViewById(R.id.cbSabtu);
        minggu = findViewById(R.id.cbMinggu);
    }

    private void showDate(){
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PemesananActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggal_pertemuan.setText(dateFormatter.format(newDate.getTime()));
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
            return "2000-01-01";
        }
    }

    private void  getAllJenjang(){

        ArrayList<Jenjang> spinnerJenjang =  jenjangAL;
        HashMap<Integer,Integer> idJenjang = new HashMap<Integer,Integer>();
        List<String> namaJenjang = new ArrayList<>();
        for(int i = 0; i < spinnerJenjang.size(); i++){
            Jenjang jen = spinnerJenjang.get(i);
                idJenjang.put(i,jen.getIdJenjang());
                namaJenjang.add(jen.getNamaJenjang());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, namaJenjang);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenjangSP.setAdapter(adapter);

        jenjangSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String jenjangStr = adapterView.getSelectedItem().toString();
                int id = idJenjang.get(adapterView.getSelectedItemPosition());
                callMapelbyjenjang(id);
                getKelas(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mapelSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int indexMapel = adapterView.getSelectedItemPosition();
                id_mapel = getIdMapel(mapelAL, indexMapel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getMapelbyjenjang(ArrayList<MataPelajaran> mapelAr){
        HashMap<Integer,Integer> idMapel = new HashMap<Integer, Integer>();
        List<String> namaMapel = new ArrayList<>();
        for(int i = 0; i < mapelAr.size(); i++){
            MataPelajaran jen = mapelAr.get(i);
            idMapel.put(i,jen.getIdJenjang());
            namaMapel.add(jen.getNamaMapel());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, namaMapel);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapelSP.setAdapter(adapter);
    }

    private void callMapelbyjenjang(int idJenjang){
        Call<ArrayList<MataPelajaran>> call = apiInterface.getMapelByIdJenjang(idJenjang);
        call.enqueue(new Callback<ArrayList<MataPelajaran>>() {
            @Override
            public void onResponse(Call<ArrayList<MataPelajaran>> call, Response<ArrayList<MataPelajaran>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                mapelAL = response.body();
                getMapelbyjenjang(mapelAL);

            }

            @Override
            public void onFailure(Call<ArrayList<MataPelajaran>> call, Throwable t) {

            }
        });
    }

    private void callJenjang(){
        Call<ArrayList<Jenjang>> call = apiInterface.getJenjang();
        call.enqueue(new Callback<ArrayList<Jenjang>>() {
            @Override
            public void onResponse(Call<ArrayList<Jenjang>> call, Response<ArrayList<Jenjang>> response) {

                if(!response.isSuccessful()){
                    return;
                }

              jenjangAL = response.body();
                getAllJenjang();

            }

            @Override
            public void onFailure(Call<ArrayList<Jenjang>> call, Throwable t) {

            }
        });

    }

    private  void getKelas(int idJenjang){
        List<Integer> kelasAr = new ArrayList<>();
         if (idJenjang == 1) {
             kelasAr.add(1);
             kelasAr.add(2);
             kelasAr.add(3);
             kelasAr.add(4);
             kelasAr.add(5);
             kelasAr.add(6);
         }
         else if (idJenjang ==2 ){
             kelasAr.add(7);
             kelasAr.add(8);
             kelasAr.add(9);
         }
         else if (idJenjang ==3 ){
             kelasAr.add(10);
             kelasAr.add(11);
             kelasAr.add(12);
         }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                this,android.R.layout.simple_spinner_item, kelasAr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kelasSP.setAdapter(adapter);
    }

    private void getJenisKelamin(){
        List<String> jenisKelaminAr = new ArrayList<>();
        jenisKelaminAr.add("Perempuan");
        jenisKelaminAr.add("Laki-Laki");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, jenisKelaminAr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenis_kelaminSP.setAdapter(adapter);
    }

    public Integer getIdMapel(ArrayList<MataPelajaran> mp, int indexMapel){

        HashMap<Integer, Integer> idMapels = new HashMap<Integer, Integer>();

        for(int i = 0 ; i < mp.size() ; i++){
            idMapels.put(i, mp.get(i).getIdMapel());
        }

        int idInt = idMapels.get(indexMapel);
        return idInt;
    }

    private void callPemesanan(){
        String seninStr= null, selasaStr = null, rabuStr = null, kamisStr = null, jumatStr = null, sabtuStr= null, mingguStr=null;
        if(senin.isChecked()){
            seninStr = "senin";
        }
        if(selasa.isChecked()){
            selasaStr = "selasa";
        }
        if(rabu.isChecked()){
            rabuStr = "rabu";
        }
        if(kamis.isChecked()){
            kamisStr = "kamis";
        }
        if(jumat.isChecked()){
            jumatStr = "jumat";
        }
        if(sabtu.isChecked()){
            sabtuStr = "sabtu";
        }
        if(minggu.isChecked()){
            mingguStr = "minggu";
        }
        Call<ArrayList<User>> call = apiInterface.cariGuru(id_mapel,
                jenis_kelaminSP.getSelectedItem().toString(),
                seninStr,
                selasaStr,
                rabuStr,
                kamisStr,
                jumatStr,
                sabtuStr,
                mingguStr
                );
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

}
