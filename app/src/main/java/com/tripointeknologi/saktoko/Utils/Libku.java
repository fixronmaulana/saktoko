package com.tripointeknologi.saktoko.Utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class Libku {
    Context context;

    public Libku(Context context) {
        this.context = context;
    }
    public String convertRibuan(String angka)
    {
        String hasil;
        if(!TextUtils.isEmpty(angka)) {
            Double n = Double.parseDouble(angka);
            NumberFormat x = NumberFormat.getInstance(Locale.getDefault());
            x.setMinimumFractionDigits(0);
            hasil = x.format(n);
        } else {
            hasil = "-";

        }

        return hasil;
    }

    public String convertRibuan(double angka)
    {
        String hasil;
        if(angka > 0) {
            Double n = Double.parseDouble(String.valueOf(angka));
            NumberFormat x = NumberFormat.getInstance(Locale.getDefault());
            x.setMinimumFractionDigits(0);
            hasil = x.format(n);
        } else {
            hasil = "-";

        }

        return hasil;
    }

    public String convertRibuan(int angka)
    {
        String hasil;
        if(angka > 0) {
            Double n = Double.parseDouble(String.valueOf(angka));
            NumberFormat x = NumberFormat.getInstance(Locale.getDefault());
            x.setMinimumFractionDigits(0);
            hasil = x.format(n);
        } else {
            hasil = "-";

        }

        return hasil;
    }
}
