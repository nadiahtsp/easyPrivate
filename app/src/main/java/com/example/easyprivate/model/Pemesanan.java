package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pemesanan {

    @Expose
    @SerializedName("jadwal_pemesanan_perminggu")
    private List<JadwalPemesananPerminggu> jadwalPemesananPerminggu;
    @Expose
    @SerializedName("mata_pelajaran")
    private MataPelajaran mataPelajaran;
    @Expose
    @SerializedName("guru")
    private User guru;
    @Expose
    @SerializedName("murid")
    private User murid;
    @Expose
    @SerializedName("status")
    private Integer status;
    @Expose
    @SerializedName("first_meet")
    private String firstMeet;
    @Expose
    @SerializedName("waktu_pemesanan")
    private String waktuPemesanan;
    @Expose
    @SerializedName("kelas")
    private Integer kelas;
    @Expose
    @SerializedName("id_mapel")
    private Integer idMapel;
    @Expose
    @SerializedName("id_murid")
    private Integer idMurid;
    @Expose
    @SerializedName("id_guru")
    private Integer idGuru;
    @Expose
    @SerializedName("id_pemesanan")
    private Integer idPemesanan;

    public List<JadwalPemesananPerminggu> getJadwalPemesananPerminggu() {
        return jadwalPemesananPerminggu;
    }

    public void setJadwalPemesananPerminggu(List<JadwalPemesananPerminggu> jadwalPemesananPerminggu) {
        this.jadwalPemesananPerminggu = jadwalPemesananPerminggu;
    }

    public MataPelajaran getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(MataPelajaran mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setKelas(Integer kelas) {
        this.kelas = kelas;
    }

    public void setIdMapel(Integer idMapel) {
        this.idMapel = idMapel;
    }

    public void setIdMurid(Integer idMurid) {
        this.idMurid = idMurid;
    }

    public void setIdGuru(Integer idGuru) {
        this.idGuru = idGuru;
    }

    public void setIdPemesanan(Integer idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstMeet() {
        return firstMeet;
    }

    public void setFirstMeet(String firstMeet) {
        this.firstMeet = firstMeet;
    }

    public String getWaktuPemesanan() {
        return waktuPemesanan;
    }

    public void setWaktuPemesanan(String waktuPemesanan) {
        this.waktuPemesanan = waktuPemesanan;
    }

    public int getKelas() {
        return kelas;
    }

    public void setKelas(int kelas) {
        this.kelas = kelas;
    }

    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public int getIdMurid() {
        return idMurid;
    }

    public void setIdMurid(int idMurid) {
        this.idMurid = idMurid;
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

}
