package com.tripointeknologi.saktoko.Models;

import com.google.gson.annotations.SerializedName;

public class MResep {
    String id_resep;
    String nama_menu;
    String foto_menu;
    String deskripsi;
    String kategori;

    public MResep(String id_resep, String nama_menu, String foto_menu) {
        this.id_resep = id_resep;
        this.nama_menu = nama_menu;
        this.foto_menu = foto_menu;
    }

    // getter setter
    public String getId_resep() { return id_resep; }
    public String getNama_menu() { return nama_menu; }
    public String getFoto_menu() { return foto_menu; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
}
