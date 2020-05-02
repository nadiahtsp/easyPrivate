package com.example.easyprivate.api;

import com.example.easyprivate.model.Jenjang;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.Pemesanan;
import com.example.easyprivate.model.User;

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

    @POST("user/cari_guru")
    Call<ArrayList<User>> cariGuru(
            @Field("id_mapel") Integer id_mapel,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("senin") String senin,
            @Field("selasa") String selasa,
            @Field("rabu") String rabu,
            @Field("kamis") String kamis,
            @Field("jumat") String jumat,
            @Field("sabtu") String sabtu,
            @Field("minggu") String minggu
    );
}
