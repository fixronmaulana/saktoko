package com.tripointeknologi.saktoko.Utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.balysv.materialripple.MaterialRippleLayout;
import com.tripointeknologi.saktoko.R;

public class Pesan extends Dialog {
    Context ctx;
    ClosePesan closePesan;
    MaterialRippleLayout btnClose;
    TextView judul,pesan,tvButton;
    ImageView icon;
    LinearLayout parent;

    public interface ClosePesan {
        void closePesan();
    }
    public Pesan(@NonNull Context context, ClosePesan closePesan) {
        super(context,R.style.backgroundialog);
        this.ctx = context;
        this.closePesan = closePesan;
        this.setContentView(R.layout.layout_pesan);
        this.btnClose = findViewById(R.id.btnClose);
        this.judul = findViewById(R.id.judul);
        this.pesan = findViewById(R.id.pesan);
        this.icon = findViewById(R.id.icon);
        this.parent = findViewById(R.id.parent);
        this.tvButton = findViewById(R.id.tvButton);
        btnClose.setOnClickListener(v->closePesan.closePesan());

    }

    public void close(){
        if(isShowing()){
            dismiss();
        }
    }

    public void tampil(){
        if(!isShowing()){
            show();
        }
    }
    public void tampil(String judul_pesan, String isi_pesan, int icon,int bg){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        parent.setBackground(ContextCompat.getDrawable(ctx,bg));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        if(!isShowing()){
            show();
        }
    }

    public void tampil(String judul_pesan, String isi_pesan, int icon){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        if(!isShowing()){
            show();
        }
    }

    public void tampil(int judul_pesan, int isi_pesan, int icon){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        if(!isShowing()){
            show();
        }
    }
    public void tampil(int judul_pesan, int isi_pesan, int icon,int warna_text, int bg_button){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        judul.setTextColor(ContextCompat.getColor(ctx,warna_text));
        pesan.setTextColor(ContextCompat.getColor(ctx,warna_text));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        tvButton.setTextColor(ContextCompat.getColor(ctx,bg_button));
        if(!isShowing()){
            show();
        }
    }

    public void tampil(int background, int judul_pesan, int isi_pesan, int icon,int warna_text, int bg_button){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        judul.setTextColor(ContextCompat.getColor(ctx,warna_text));
        pesan.setTextColor(ContextCompat.getColor(ctx,warna_text));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        btnClose.setBackground(ContextCompat.getDrawable(ctx,bg_button));
        parent.setBackground(ContextCompat.getDrawable(ctx,background));
        if(!isShowing()){
            show();
        }
    }

    public void tampil(int background, int judul_pesan, String isi_pesan, int icon,int warna_text, int bg_button){
        judul.setText(judul_pesan);
        pesan.setText(isi_pesan);
        judul.setTextColor(ContextCompat.getColor(ctx,warna_text));
        pesan.setTextColor(ContextCompat.getColor(ctx,warna_text));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon));
        btnClose.setBackground(ContextCompat.getDrawable(ctx,bg_button));
        parent.setBackground(ContextCompat.getDrawable(ctx,background));
        if(!isShowing()){
            show();
        }
    }

    public void tampil(String[] text, int[] icon){
        judul.setText(text[0]);
        pesan.setText(text[1]);
        judul.setTextColor(ContextCompat.getColor(ctx,icon[0]));
        pesan.setTextColor(ContextCompat.getColor(ctx,icon[0]));
        parent.setBackground(ContextCompat.getDrawable(ctx,icon[1]));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon[2]));
        btnClose.setBackground(ContextCompat.getDrawable(ctx,icon[3]));
        tvButton.setTextColor(ContextCompat.getColor(ctx,icon[4]));
        if(!isShowing()){
            show();
        }
    }
    public void tampil(int[] icon){
        judul.setText(icon[0]);
        pesan.setText(icon[1]);
        judul.setTextColor(ContextCompat.getColor(ctx,icon[2]));
        pesan.setTextColor(ContextCompat.getColor(ctx,icon[2]));
        parent.setBackground(ContextCompat.getDrawable(ctx,icon[3]));
        this.icon.setImageDrawable(ContextCompat.getDrawable(ctx,icon[4]));
        btnClose.setBackground(ContextCompat.getDrawable(ctx,icon[5]));
        tvButton.setTextColor(ContextCompat.getColor(ctx,icon[6]));
        if(!isShowing()){
            show();
        }
    }


}
