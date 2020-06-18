package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absen {
    @Expose
    @SerializedName("jadwal_pemesanan_perminggu")
    private JadwalPemesananPerminggu jadwalPemesananPerminggu;
    @Expose
    @SerializedName("waktu_absen")
    private String waktuAbsen;
    @Expose
    @SerializedName("id_jadwal_pemesanan_perminggu")
    private int idJadwalPemesananPerminggu;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_absen")
    private int idAbsen;

    public JadwalPemesananPerminggu getJadwalPemesananPerminggu() {
        return jadwalPemesananPerminggu;
    }

    public void setJadwalPemesananPerminggu(JadwalPemesananPerminggu jadwalPemesananPerminggu) {
        this.jadwalPemesananPerminggu = jadwalPemesananPerminggu;
    }

    public String getWaktuAbsen() {
        return waktuAbsen;
    }

    public void setWaktuAbsen(String waktuAbsen) {
        this.waktuAbsen = waktuAbsen;
    }

    public int getIdJadwalPemesananPerminggu() {
        return idJadwalPemesananPerminggu;
    }

    public void setIdJadwalPemesananPerminggu(int idJadwalPemesananPerminggu) {
        this.idJadwalPemesananPerminggu = idJadwalPemesananPerminggu;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }



}
