package com.example.easyprivate.api;

import com.example.easyprivate.model.Absen;
import com.example.easyprivate.model.JadwalAvailable;
import com.example.easyprivate.model.JadwalPemesananPerminggu;
import com.example.easyprivate.model.FilterGuru;
import com.example.easyprivate.model.Jenjang;
import com.example.easyprivate.model.MataPelajaran;
import com.example.easyprivate.model.MidtransPembayaran;
import com.example.easyprivate.model.Pembayaran;
import com.example.easyprivate.model.PembayaranAbsen;
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

    @FormUrlEncoded
    @POST("user/cari_guru")
    Call<FilterGuru> cariGuru(
            @Field("id_mapel") Integer id_mapel,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("hari[]" ) ArrayList<String> hari,
            @Field("latitude_murid") Double latitude,
            @Field("longitude_murid") Double longitude
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

    @FormUrlEncoded
    @POST("pemesanan/update")
    Call<Pemesanan> pemesananUpdate(
            @Field("id_pemesanan") Integer id_pemesanan,
            @Field("status") Integer status
    );
    @GET("pemesanan/jadwal/{id}")
    Call<JadwalPemesananPerminggu> jadwalPermingguById(
            @Path("id") int idJadwalPerminggu
    );
    @FormUrlEncoded
    @POST("pemesanan/jadwal/filter")
    Call<ArrayList<JadwalPemesananPerminggu>> jadwalPermingguByFilter(
            @Field("id_pemesanan") Integer id_pemesanan,
            @Field("id_guru") Integer id_guru,
            @Field("id_murid") Integer id_murid,
            @Field("status_pemesanan") Integer status
    );
    @FormUrlEncoded
    @POST("absen/filter")
    Call<ArrayList<Absen>> absenFilter(
            @Field("id_absen") Integer id_absen,
            @Field("id_pemesanan") Integer id_pemesanan,
            @Field("status_pemesanan") Integer status,
            @Field("id_jadwal_pemesanan_perminggu") Integer id_jadwal_pemesanan_perminggu,
            @Field("id_guru") Integer id_guru,
            @Field("id_murid") Integer id_murid

    );

    @FormUrlEncoded
    @POST("absen_pembayaran")
    Call<ArrayList<PembayaranAbsen>> pemAbsen(
            @Field("id_murid") Integer id_murid,
            @Field("id_guru") Integer id_guru,
            @Field("id_pemesanan") Integer id_pemesanan,
            @Field("bulan") Integer bulan,
            @Field("tahun") Integer tahun,
            @Field("unpaid") String unpaid,
            @Field("distinct") String distinct

    );
    @FormUrlEncoded
    @POST("pembayaran/filter")
    Call<ArrayList<Pembayaran>> pembayaranSukses(
            @Field("id_user") Integer id_user,
            @Field("id_pembayaran") Integer id_pembayaran,
            @Field("periode_bulan") Integer periode_bulan,
            @Field("periode_tahun") Integer periode_tahun,
            @Field("status") Integer status

    );

    @FormUrlEncoded
    @POST("pembayaran/store")
    Call<Void> pembayaranStore(
            @Field("id_transaksi") String id_transaksi,
            @Field("id_user") Integer id_user,
            @Field("id_order") String id_order,
            @Field("status") String status,
            @Field("jumlah_bayar") Integer jumlah_bayar,
            @Field("tanggal_bayar") String tanggal_bayar,
            @Field("periode_bulan") Integer periode_bulan,
            @Field("periode_tahun") Integer periode_tahun,
            @Field("redirect_url") String redirect_url

    );

}
