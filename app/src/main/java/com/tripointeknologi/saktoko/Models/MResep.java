package com.tripointeknologi.saktoko.Models;

import com.google.gson.annotations.SerializedName;

public class MResep {
    @SerializedName("id_resep")
    String id_resep;
    @SerializedName("nama_menu")
    String nama_menu;
    @SerializedName("foto_menu")
    String foto_menu;

    public MResep(String id_resep, String nama_menu, String foto_menu) {
        this.id_resep = id_resep;
        this.nama_menu = nama_menu;
        this.foto_menu = foto_menu;
    }


    public String getId_resep() {
        return id_resep;
    }

    public void setId_resep(String id_resep) {
        this.id_resep = id_resep;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getFoto_menu() {
        return foto_menu;
    }

    public void setFoto_menu(String foto_menu) {
        this.foto_menu = foto_menu;
    }
}
