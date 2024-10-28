package com.tripointeknologi.saktoko.Models;


import com.google.gson.annotations.SerializedName;

public class MKategori {
    @SerializedName("id_kategori")
    String id_kategori;
    @SerializedName("kategori")
    String kategori;
    @SerializedName("banner")
    String banner;

    @SerializedName("icon")
    String icon;
    @SerializedName("bg_icon")
    String bg_icon;
    //


    public MKategori() {
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBg_icon() {
        return bg_icon;
    }

    public void setBg_icon(String bg_icon) {
        this.bg_icon = bg_icon;
    }
}