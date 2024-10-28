package com.tripointeknologi.saktoko.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tripointeknologi.saktoko.Models.MResep;
import com.tripointeknologi.saktoko.R;

import java.util.List;

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.Holder> {

    Context ctx;
    List<MResep>list;

    public ResepAdapter(List<MResep> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ResepAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.list_resep,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResepAdapter.Holder h, int i) {
        h.tvNamaMenu.setText(list.get(i).getNama_menu());
        Glide.with(ctx).load(list.get(i).getFoto_menu()).into(h.imFoto);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        MaterialRippleLayout btnResep;
        RoundedImageView imFoto;
        TextView tvNamaMenu;
        public Holder(@NonNull View v) {
            super(v);
            btnResep = v.findViewById(R.id.btnResep);
            imFoto = v.findViewById(R.id.imFoto);
            tvNamaMenu = v.findViewById(R.id.tvNamaMenu);
        }
    }
}
