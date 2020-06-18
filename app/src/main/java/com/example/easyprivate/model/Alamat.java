package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alamat {
    @Expose
    @SerializedName("alamat_lengkap")
    private String alamatLengkap;
    @Expose
    @SerializedName("longitude")
    private Double longitude;
    @Expose
    @SerializedName("latitude")
    private Double latitude;
    @Expose
    @SerializedName("id_user")
    private int idUser;
    @Expose
    @SerializedName("id_alamat")
    private int idAlamat;

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(int idAlamat) {
        this.idAlamat = idAlamat;
    }
}

