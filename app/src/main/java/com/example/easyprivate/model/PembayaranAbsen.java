package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PembayaranAbsen {
    @Expose
    @SerializedName("pemesanan")
    private Pemesanan pemesanan;
    @Expose
    @SerializedName("guru")
    private User guru;
    @Expose
    @SerializedName("murid")
    private User murid;
    @Expose
    @SerializedName("bulan")
    private int bulan;
    @Expose
    @SerializedName("tahun")
    private int tahun;
    @Expose
    @SerializedName("jumlah_absen")
    private int jumlahAbsen;
    @Expose
    @SerializedName("id_guru")
    private int idGuru;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_murid")
    private int idMurid;

    public Pemesanan getPemesanan() {
        return pemesanan;
    }

    public void setPemesanan(Pemesanan pemesanan) {
        this.pemesanan = pemesanan;
    }

    public User getGuru() {
        return guru;
    }

    public void setGuru(User guru) {
        this.guru = guru;
    }

    public User getMurid() {
        return murid;
    }

    public void setMurid(User murid) {
        this.murid = murid;
    }

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public int getJumlahAbsen() {
        return jumlahAbsen;
    }

    public void setJumlahAbsen(int jumlahAbsen) {
        this.jumlahAbsen = jumlahAbsen;
    }

    public int getIdGuru() {
        return idGuru;
    }

    public void setIdGuru(int idGuru) {
        this.idGuru = idGuru;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdMurid() {
        return idMurid;
    }

    public void setIdMurid(int idMurid) {
        this.idMurid = idMurid;
    }
}
