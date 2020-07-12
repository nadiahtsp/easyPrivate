package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendaftaranGuru {
    @Expose
    @SerializedName("nilai_ipk")
    private double nilaiIpk;
    @Expose
    @SerializedName("pengalaman_mengajar")
    private int pengalamanMengajar;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("id_user")
    private int idUser;
    @Expose
    @SerializedName("id_season")
    private int idSeason;
    @Expose
    @SerializedName("id_pendaftaran")
    private int idPendaftaran;

    public double getNilaiIpk() {
        return nilaiIpk;
    }

    public void setNilaiIpk(double nilaiIpk) {
        this.nilaiIpk = nilaiIpk;
    }

    public int getPengalamanMengajar() {
        return pengalamanMengajar;
    }

    public void setPengalamanMengajar(int pengalamanMengajar) {
        this.pengalamanMengajar = pengalamanMengajar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSeason() {
        return idSeason;
    }

    public void setIdSeason(int idSeason) {
        this.idSeason = idSeason;
    }

    public int getIdPendaftaran() {
        return idPendaftaran;
    }

    public void setIdPendaftaran(int idPendaftaran) {
        this.idPendaftaran = idPendaftaran;
    }
}
