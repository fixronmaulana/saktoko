package com.tripointeknologi.saktoko.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tripointeknologi.saktoko.R;

public class DetailActivity extends AppCompatActivity {

    TextView tvNama, tvKategori, tvDeskripsi;
    ImageView imgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi view
        tvNama = findViewById(R.id.tvDetailNama);
        tvKategori = findViewById(R.id.tvDetailKategori);
        tvDeskripsi = findViewById(R.id.tvDetailDeskripsi);
        imgMenu = findViewById(R.id.imgDetailMenu);

        // Ambil data dari Intent
        String nama = getIntent().getStringExtra("nama_menu");
        String kategori = getIntent().getStringExtra("kategori");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String foto = getIntent().getStringExtra("foto_menu");

        // Set ke view
        tvNama.setText(nama);
        tvKategori.setText(kategori);
        tvDeskripsi.setText(deskripsi);

        Glide.with(this)
                .load(foto)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imgMenu);
    }
}
