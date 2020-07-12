package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("alamat")
    private Alamat alamat;
    @Expose
    @SerializedName("pendaftaran_guru")
    private ArrayList<PendaftaranGuru> pendaftaranGuru;
    @Expose
    @SerializedName("guru_mapel")
    private ArrayList<GuruMapel> guruMapel;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("role")
    private int role;
    @Expose
    @SerializedName("no_handphone")
    private String noHandphone;
    @Expose
    @SerializedName("tanggal_lahir")
    private String tanggalLahir;
    @Expose
    @SerializedName("jenis_kelamin")
    private String jenisKelamin;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("universitas")
    private String universitas;

    public ArrayList<GuruMapel> getGuruMapel() {
        return guruMapel;
    }

    public void setGuruMapel(ArrayList<GuruMapel> guruMapel) {
        this.guruMapel = guruMapel;
    }

    public ArrayList<PendaftaranGuru> getPendaftaranGuru() {
        return pendaftaranGuru;
    }

    public void setPendaftaranGuru(ArrayList<PendaftaranGuru> pendaftaranGuru) {
        this.pendaftaranGuru = pendaftaranGuru;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public void setAlamat(Alamat alamat) {
        this.alamat = alamat;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


//    @Expose
//    @SerializedName("alamat")
//    private Alamat alamat;
//    @Expose
//    @SerializedName("updated_at")
//    private String updatedAt;
//    @Expose
//    @SerializedName("created_at")
//    private String createdAt;
//    @Expose
//    @SerializedName("role")
//    private int role;
//    @Expose
//    @SerializedName("no_handphone")
//    private String noHandphone;
//    @Expose
//    @SerializedName("tanggal_lahir")
//    private String tanggalLahir;
//    @Expose
//    @SerializedName("jenis_kelamin")
//    private String jenisKelamin;
//    @Expose
//    @SerializedName("email")
//    private String email;
//    @Expose
//    @SerializedName("name")
//    private String name;
//    @Expose
//    @SerializedName("avatar")
//    private String avatar;
//    @Expose
//    @SerializedName("id")
//    private int id;
//
//    public Alamat getAlamat() {
//        return alamat;
//    }
//
//    public void setAlamat(Alamat alamat) {
//        this.alamat = alamat;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public int getRole() {
//        return role;
//    }
//
//    public void setRole(int role) {
//        this.role = role;
//    }
//
//    public String getNoHandphone() {
//        return noHandphone;
//    }
//
//    public void setNoHandphone(String noHandphone) {
//        this.noHandphone = noHandphone;
//    }
//
//    public String getTanggalLahir() {
//        return tanggalLahir;
//    }
//
//    public void setTanggalLahir(String tanggalLahir) {
//        this.tanggalLahir = tanggalLahir;
//    }
//
//    public String getJenisKelamin() {
//        return jenisKelamin;
//    }
//
//    public void setJenisKelamin(String jenisKelamin) {
//        this.jenisKelamin = jenisKelamin;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//

}
