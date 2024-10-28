package com.tripointeknologi.saktoko.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mbanner {
    @SerializedName("banner")
    String banner;

    public Mbanner() {
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
