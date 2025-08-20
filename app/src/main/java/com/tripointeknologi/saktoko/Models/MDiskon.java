package com.tripointeknologi.saktoko.Models;

import com.google.gson.annotations.SerializedName;

public class MDiskon {
    @SerializedName("diskon")
    int diskon;

    @SerializedName("tipe")
    int tipe;

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }
}
