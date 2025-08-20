package com.tripointeknologi.saktoko.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

        // Set nama menu
        h.tvNamaMenu.setText(item.getNama_menu());

        // Load gambar pakai Glide
        Glide.with(ctx)
                .load(item.getFoto_menu())
                .placeholder(R.drawable.ic_launcher_background) // gambar default jika error
                .error(R.drawable.ic_launcher_background)       // gambar default jika gagal load
                .into(h.imgMenu);

        // Kalau mau klik listener
        h.itemView.setOnClickListener(v -> {
            // Contoh Toast, bisa diarahkan ke DetailActivity
            // Toast.makeText(ctx, "Klik: " + item.getNamaMenu(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView tvNamaMenu;
        ImageView imgMenu;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            imgMenu = itemView.findViewById(R.id.imFoto);
        }
    }
}
