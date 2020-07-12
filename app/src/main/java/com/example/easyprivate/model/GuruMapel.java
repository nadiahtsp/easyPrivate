package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuruMapel {

    @Expose
    @SerializedName("mata_pelajaran")
    private MataPelajaran mataPelajaran;
    @Expose
    @SerializedName("id_mapel")
    private int idMapel;
    @Expose
    @SerializedName("id_guru")
    private int idGuru;
    @Expose
    @SerializedName("id_guru_mapel")
    private int idGuruMapel;

    public MataPelajaran getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(MataPelajaran mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public int getIdGuru() {
        return idGuru;
    }

    public void setIdGuru(int idGuru) {
        this.idGuru = idGuru;
    }

    public int getIdGuruMapel() {
        return idGuruMapel;
    }

    public void setIdGuruMapel(int idGuruMapel) {
        this.idGuruMapel = idGuruMapel;
    }
}
