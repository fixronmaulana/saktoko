package com.tripointeknologi.saktoko.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.tripointeknologi.saktoko.Activity.KategoriActivity;
import com.tripointeknologi.saktoko.Models.MKategori;
import com.tripointeknologi.saktoko.R;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.Holder> {

    List<MKategori> list;
    Context ctx;

    public KategoriAdapter(List<MKategori> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public KategoriAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.list_kategori, parent, false);
        return new Holder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.Holder h, int i) {
        h.tvKategori.setText(list.get(i).getKategori());
        Glide.with(ctx).load(list.get(i).getIcon()).into(h.imIcon);
        h.imIcon.setBackgroundColor(ContextCompat.getColor(ctx, com.balysv.materialripple.R.color.transparent));
        h.bgIcon.setBackgroundColor(Color.parseColor(list.get(i).getBg_icon()));
        h.btnKategori.setRippleColor(Color.parseColor(list.get(i).getBg_icon()));
        h.btnKategori.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, KategoriActivity.class);
            intent.putExtra("banner", list.get(i).getBanner());
            intent.putExtra("icon", list.get(i).getIcon());
            intent.putExtra("bg_icon", list.get(i).getBg_icon());
            intent.putExtra("kategori", list.get(i).getKategori());
            ctx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ImageView imIcon;
        TextView tvKategori;
        RelativeLayout bgIcon;
        MaterialRippleLayout btnKategori;

        public Holder(@NonNull View v) {
            super(v);
            imIcon = v.findViewById(R.id.imIcon);
            tvKategori = v.findViewById(R.id.tvKategori);
            bgIcon = v.findViewById(R.id.bgIcon);
            btnKategori = v.findViewById(R.id.btnKategori);
        }
    }
}
