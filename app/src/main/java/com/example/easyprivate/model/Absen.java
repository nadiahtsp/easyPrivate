package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absen {

    @Expose
    @SerializedName("pemesanan")
    private Pemesanan pemesanan;
    @Expose
    @SerializedName("jadwal_ajar")
    private JadwalAjar jadwalAjar;
    @Expose
    @SerializedName("waktu_absen")
    private String waktuAbsen;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_jadwal_ajar")
    private int idJadwalAjar;
    @Expose
    @SerializedName("id_absen")
    private int idAbsen;

    public Pemesanan getPemesanan() {
        return pemesanan;
    }

    public void setPemesanan(Pemesanan pemesanan) {
        this.pemesanan = pemesanan;
    }

    public JadwalAjar getJadwalAjar() {
        return jadwalAjar;
    }

    public void setJadwalAjar(JadwalAjar jadwalAjar) {
        this.jadwalAjar = jadwalAjar;
    }

    public String getWaktuAbsen() {
        return waktuAbsen;
    }

    public void setWaktuAbsen(String waktuAbsen) {
        this.waktuAbsen = waktuAbsen;
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

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }

}
