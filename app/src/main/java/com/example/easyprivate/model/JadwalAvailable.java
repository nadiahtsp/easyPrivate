package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalAvailable {
    @Expose
    @SerializedName("available")
    private int available;
    @Expose
    @SerializedName("end")
    private String end;
    @Expose
    @SerializedName("start")
    private String start;
    @Expose
    @SerializedName("hari")
    private String hari;
    @Expose
    @SerializedName("id_user")
    private int idUser;
    @Expose
    @SerializedName("id_jadwal_available")
    private int idJadwalAvailable;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdJadwalAvailable() {
        return idJadwalAvailable;
    }

    public void setIdJadwalAvailable(int idJadwalAvailable) {
        this.idJadwalAvailable = idJadwalAvailable;
    }
}
