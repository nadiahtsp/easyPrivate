package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalAjar {
    @Expose
    @SerializedName("waktu_ajar")
    private String waktuAjar;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_jadwal_ajar")
    private int idJadwalAjar;

    public String getWaktuAjar() {
        return waktuAjar;
    }

    public void setWaktuAjar(String waktuAjar) {
        this.waktuAjar = waktuAjar;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdJadwalAjar() {
        return idJadwalAjar;
    }

    public void setIdJadwalAjar(int idJadwalAjar) {
        this.idJadwalAjar = idJadwalAjar;
    }
}
