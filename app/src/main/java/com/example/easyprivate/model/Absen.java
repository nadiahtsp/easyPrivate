package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absen {
    @Expose
    @SerializedName("pemesanan")
    private Pemesanan pemesanan;
    @Expose
    @SerializedName("id_absen")
    private int idAbsen;
    @Expose
    @SerializedName("jadwal_ajar")
    private JadwalAjar jadwalAjar;
    @Expose
    @SerializedName("waktu_absen")
    private String waktuAbsen;
    @Expose
    @SerializedName("id_pemesanan")
    private int idPemesanan;
    @Expose
    @SerializedName("id_jadwal_ajar")
    private int idJadwalAjar;

    public Pemesanan getPemesanan() {
        return pemesanan;
    }

    public void setPemesanan(Pemesanan pemesanan) {
        this.pemesanan = pemesanan;
    }

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }

    public JadwalAjar getJadwalAjar() {
        return jadwalAjar;
    }

    public void setJadwalAjar(JadwalAjar jadwalAjar) {
        this.jadwalAjar = jadwalAjar;
    }

    public String getWaktuAbsen() {
        return waktuAbsen;
    }

    public void setWaktuAbsen(String waktuAbsen) {
        this.waktuAbsen = waktuAbsen;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdJadwalAjar() {
        return idJadwalAjar;
    }

    public void setIdJadwalAjar(int idJadwalAjar) {
        this.idJadwalAjar = idJadwalAjar;
    }

    public static class Pemesanan {
        @Expose
        @SerializedName("mata_pelajaran")
        private MataPelajaran mataPelajaran;
        @Expose
        @SerializedName("guru")
        private Guru guru;
        @Expose
        @SerializedName("murid")
        private Murid murid;
        @Expose
        @SerializedName("jumlah_pertemuan")
        private int jumlahPertemuan;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("waktu_pemesanan")
        private String waktuPemesanan;
        @Expose
        @SerializedName("kelas")
        private int kelas;
        @Expose
        @SerializedName("id_mapel")
        private int idMapel;
        @Expose
        @SerializedName("id_murid")
        private int idMurid;
        @Expose
        @SerializedName("id_guru")
        private int idGuru;
        @Expose
        @SerializedName("id_pemesanan")
        private int idPemesanan;

        public MataPelajaran getMataPelajaran() {
            return mataPelajaran;
        }

        public void setMataPelajaran(MataPelajaran mataPelajaran) {
            this.mataPelajaran = mataPelajaran;
        }

        public Guru getGuru() {
            return guru;
        }

        public void setGuru(Guru guru) {
            this.guru = guru;
        }

        public Murid getMurid() {
            return murid;
        }

        public void setMurid(Murid murid) {
            this.murid = murid;
        }

        public int getJumlahPertemuan() {
            return jumlahPertemuan;
        }

        public void setJumlahPertemuan(int jumlahPertemuan) {
            this.jumlahPertemuan = jumlahPertemuan;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

    public static class MataPelajaran {
        @Expose
        @SerializedName("jenjang")
        private Jenjang jenjang;
        @Expose
        @SerializedName("nama_mapel")
        private String namaMapel;
        @Expose
        @SerializedName("id_jenjang")
        private int idJenjang;
        @Expose
        @SerializedName("id_mapel")
        private int idMapel;

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

        public int getIdMapel() {
            return idMapel;
        }

        public void setIdMapel(int idMapel) {
            this.idMapel = idMapel;
        }
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

    public static class Guru {
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
        @SerializedName("id")
        private int id;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Murid {
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
        @SerializedName("id")
        private int id;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class JadwalAjar {
        @Expose
        @SerializedName("waktu_ajar")
        private String waktuAjar;
        @Expose
        @SerializedName("id_pemesanan")
        private int idPemesanan;
        @Expose
        @SerializedName("id_jadwal_ajar")
        private int idJadwalAjar;

        public String getWaktuAjar() {
            return waktuAjar;
        }

        public void setWaktuAjar(String waktuAjar) {
            this.waktuAjar = waktuAjar;
        }

        public int getIdPemesanan() {
            return idPemesanan;
        }

        public void setIdPemesanan(int idPemesanan) {
            this.idPemesanan = idPemesanan;
        }

        public int getIdJadwalAjar() {
            return idJadwalAjar;
        }

        public void setIdJadwalAjar(int idJadwalAjar) {
            this.idJadwalAjar = idJadwalAjar;
        }
    }


//    @Expose
//    @SerializedName("pemesanan")
//    private Pemesanan pemesanan;
//    @Expose
//    @SerializedName("jadwal_ajar")
//    private JadwalAjar jadwalAjar;
//    @Expose
//    @SerializedName("waktu_absen")
//    private String waktuAbsen;
//    @Expose
//    @SerializedName("id_pemesanan")
//    private int idPemesanan;
//    @Expose
//    @SerializedName("id_jadwal_ajar")
//    private int idJadwalAjar;
//    @Expose
//    @SerializedName("id_absen")
//    private int idAbsen;
//
//    public Pemesanan getPemesanan() {
//        return pemesanan;
//    }
//
//    public void setPemesanan(Pemesanan pemesanan) {
//        this.pemesanan = pemesanan;
//    }
//
//    public JadwalAjar getJadwalAjar() {
//        return jadwalAjar;
//    }
//
//    public void setJadwalAjar(JadwalAjar jadwalAjar) {
//        this.jadwalAjar = jadwalAjar;
//    }
//
//    public String getWaktuAbsen() {
//        return waktuAbsen;
//    }
//
//    public void setWaktuAbsen(String waktuAbsen) {
//        this.waktuAbsen = waktuAbsen;
//    }
//
//    public int getIdPemesanan() {
//        return idPemesanan;
//    }
//
//    public void setIdPemesanan(int idPemesanan) {
//        this.idPemesanan = idPemesanan;
//    }
//
//    public int getIdJadwalAjar() {
//        return idJadwalAjar;
//    }
//
//    public void setIdJadwalAjar(int idJadwalAjar) {
//        this.idJadwalAjar = idJadwalAjar;
//    }
//
//    public int getIdAbsen() {
//        return idAbsen;
//    }
//
//    public void setIdAbsen(int idAbsen) {
//        this.idAbsen = idAbsen;
//    }

}
