package com.tripointeknologi.saktoko.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MBarang {
    @SerializedName("id_barang")
    String id_barang;

    @SerializedName("nama_barang")
    String nama_barang;

    @SerializedName("foto")
    String foto;

    @SerializedName("berat")
    int berat;

    @SerializedName("kategori")
    String kategori;

    @SerializedName("satuan")
    String satuan;

    @SerializedName("harga")
    int harga;

    @SerializedName("qty")
    int qty;

    @SerializedName("stok")
    int stok;

    @SerializedName("total")
    int total;

    @SerializedName("diskon")
    List<MDiskon> diskon; // <-- Ubah dari int ke List<MDiskon>

    public MBarang() {
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MDiskon> getDiskon() {
        return diskon;
    }

    public void setDiskon(List<MDiskon> diskon) {
        this.diskon = diskon;
    }
}
