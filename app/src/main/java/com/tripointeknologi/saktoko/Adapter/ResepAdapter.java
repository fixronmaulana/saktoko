package com.tripointeknologi.saktoko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tripointeknologi.saktoko.Activity.DetailActivity;
import com.tripointeknologi.saktoko.Models.MResep;
import com.tripointeknologi.saktoko.R;

import java.util.List;

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.Holder> {

    private Context ctx;
    private List<MResep> list;

    public ResepAdapter(Context ctx, List<MResep> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_resep, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        MResep item = list.get(position);

        // Set nama, kategori, deskripsi
        h.tvNamaMenu.setText(item.getNama_menu());
        h.tvKategori.setText(item.getKategori());
        h.tvDeskripsi.setText(item.getDeskripsi());

        // Load gambar pakai Glide
        Glide.with(ctx)
                .load(item.getFoto_menu())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(h.imgMenu);

        // Klik listener -> kirim data ke DetailActivity
        h.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, DetailActivity.class);
            intent.putExtra("nama_menu", item.getNama_menu());
            intent.putExtra("kategori", item.getKategori());
            intent.putExtra("deskripsi", item.getDeskripsi());
            intent.putExtra("foto_menu", item.getFoto_menu());
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView tvNamaMenu, tvKategori, tvDeskripsi;
        ImageView imgMenu;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            tvKategori = itemView.findViewById(R.id.txtKategori);
            tvDeskripsi = itemView.findViewById(R.id.txtDeskripsi);
            imgMenu = itemView.findViewById(R.id.imFoto);
        }
    }
}
