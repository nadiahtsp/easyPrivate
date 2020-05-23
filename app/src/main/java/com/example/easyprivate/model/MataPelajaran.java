package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MataPelajaran {
    @Expose
    @SerializedName("id_mapel")
    private int idMapel;
    @Expose
    @SerializedName("jenjang")
    private Jenjang jenjang;
    @Expose
    @SerializedName("nama_mapel")
    private String namaMapel;
    @Expose
    @SerializedName("id_jenjang")
    private int idJenjang;

    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public Jenjang getJenjang() {
        return jenjang;
    }

    public void setJenjang(Jenjang jenjang) {
        this.jenjang = jenjang;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public int getIdJenjang() {
        return idJenjang;
    }

    public void setIdJenjang(int idJenjang) {
        this.idJenjang = idJenjang;
    }

    public static class Jenjang {
        @Expose
        @SerializedName("upah_guru_per_pertemuan")
        private int upahGuruPerPertemuan;
        @Expose
        @SerializedName("harga_per_pertemuan")
        private int hargaPerPertemuan;
        @Expose
        @SerializedName("nama_jenjang")
        private String namaJenjang;
        @Expose
        @SerializedName("id_jenjang")
        private int idJenjang;

        public int getUpahGuruPerPertemuan() {
            return upahGuruPerPertemuan;
        }

        public void setUpahGuruPerPertemuan(int upahGuruPerPertemuan) {
            this.upahGuruPerPertemuan = upahGuruPerPertemuan;
        }

        public int getHargaPerPertemuan() {
            return hargaPerPertemuan;
        }

        public void setHargaPerPertemuan(int hargaPerPertemuan) {
            this.hargaPerPertemuan = hargaPerPertemuan;
        }

        public String getNamaJenjang() {
            return namaJenjang;
        }

        public void setNamaJenjang(String namaJenjang) {
            this.namaJenjang = namaJenjang;
        }

        public int getIdJenjang() {
            return idJenjang;
        }

        public void setIdJenjang(int idJenjang) {
            this.idJenjang = idJenjang;
        }
    }


}
