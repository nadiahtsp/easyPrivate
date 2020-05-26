package com.example.easyprivate.api;

import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.Jenjang;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/murid")
    Call<User> getMurid(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/daftar")
    Call<User> postDaftar(
            @Field("email") String email,
            @Field("name") String name,
            @Field("avatar") String avatar,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("no_handphone") String no_handphone,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude,
            @Field("alamat_lengkap") String alamat_lengkap
    );
    @FormUrlEncoded
    @POST("user/murid/valid")
    Call<Integer> getMuridValid(
            @Field("email") String email
    );

    @GET("jenjang")
    Call<ArrayList<Jenjang>> getJenjang(
    );

    @GET("mapel/jenjang/{id}")
    Call<ArrayList<MataPelajaran>> getMapelByIdJenjang(
            @Path("id") int idJenjang
    );

    @FormUrlEncoded
    @POST("user/cari_guru")
    Call<ArrayList<User>> cariGuru(
            @Field("id_mapel") Integer id_mapel,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("hari[]" ) ArrayList<String> hari
            );
    @GET("user/detail/{id}")
    Call<User> detailGuruById(
            @Path("id") int idGuru
    );

    @FormUrlEncoded
    @POST("jadwalAvailable/filter")
    Call<ArrayList<JadwalAvailable>> getJadwalAvailable(
            @Field("id_user") Integer id_user,
            @Field("available") Integer available,
            @Field("hari[]" ) ArrayList<String> hari,
            @Field("start" ) String start,
            @Field("end" ) String end
    );

    @FormUrlEncoded
    @POST("pemesanan/tambah_pesanan")
    Call<Pemesanan> createPemesananBaru(
            @Field("id_murid") Integer id_murid,
            @Field("id_guru") Integer id_guru,
            @Field("id_mapel") Integer id_mapel,
            @Field("id_jadwal_available[]" ) ArrayList<Integer> id_jadwal_available,
            @Field("kelas") Integer kelas,
            @Field("first_meet") String first_meet
    );
    @GET("pemesanan/{id}")
    Call<Pemesanan> detailPemesanan(
            @Path("id") int idPemesanan
    );
    @FormUrlEncoded
    @POST("pemesanan/filter")
    Call<ArrayList<Pemesanan>> pemesananFilter(
            @Field("id_pemesanan") Integer id_pemesanan,
            @Field("id_murid") Integer id_murid,
            @Field("id_guru") Integer id_guru,
            @Field("status") Integer status
    );
}
