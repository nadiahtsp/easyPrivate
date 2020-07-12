package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pembayaran {

    @Expose
    @SerializedName("periode_tahun")
    private String periodeTahun;
    @Expose
    @SerializedName("periode_bulan")
    private String periodeBulan;
    @Expose
    @SerializedName("tanggal_bayar")
    private String tanggalBayar;
    @Expose
    @SerializedName("jumlah_bayar")
    private String jumlahBayar;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("id_order")
    private String idOrder;
    @Expose
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @Expose
    @SerializedName("id_user")
    private String idUser;
    @Expose
    @SerializedName("id_pembayaran")
    private int idPembayaran;

    public String getPeriodeTahun() {
        return periodeTahun;
    }

    public void setPeriodeTahun(String periodeTahun) {
        this.periodeTahun = periodeTahun;
    }

    public String getPeriodeBulan() {
        return periodeBulan;
    }

    public void setPeriodeBulan(String periodeBulan) {
        this.periodeBulan = periodeBulan;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(String tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public String getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(String jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }
}
