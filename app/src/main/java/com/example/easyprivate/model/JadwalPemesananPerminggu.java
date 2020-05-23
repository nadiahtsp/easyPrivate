package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalPemesananPerminggu {
    @Expose
    @SerializedName("jadwal_available")
    private JadwalAvailable jadwalAvailable;
    @Expose
    @SerializedName("id_jadwal_available")
    private int idJadwalAvailable;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_jadwal_pemesanan_perminggu")
    private int idJadwalPemesananPerminggu;

    public JadwalAvailable getJadwalAvailable() {
        return jadwalAvailable;
    }

    public void setJadwalAvailable(JadwalAvailable jadwalAvailable) {
        this.jadwalAvailable = jadwalAvailable;
    }

    public int getIdJadwalAvailable() {
        return idJadwalAvailable;
    }

    public void setIdJadwalAvailable(int idJadwalAvailable) {
        this.idJadwalAvailable = idJadwalAvailable;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdJadwalPemesananPerminggu() {
        return idJadwalPemesananPerminggu;
    }

    public void setIdJadwalPemesananPerminggu(int idJadwalPemesananPerminggu) {
        this.idJadwalPemesananPerminggu = idJadwalPemesananPerminggu;
    }
}
