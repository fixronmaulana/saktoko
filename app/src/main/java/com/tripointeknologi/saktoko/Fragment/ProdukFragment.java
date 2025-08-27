package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripointeknologi.saktoko.Adapter.BarangHomeAdapter;
import com.tripointeknologi.saktoko.Models.MBarang;
import com.tripointeknologi.saktoko.databinding.FragmentProdukBinding;

import java.util.ArrayList;
import java.util.List;

public class ProdukFragment extends Fragment {
    FragmentProdukBinding b;
    Context ctx;
    List<MBarang> listProduk;
    BarangHomeAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentProdukBinding.inflate(inflater, container, false);

        // Contoh data dummy dulu
        listProduk = new ArrayList<>();
        MBarang p1 = new MBarang();
        p1.setId_barang("1");
        p1.setNama_barang("Sepatu Sport");
        p1.setHarga(250000);
        p1.setKategori("Fashion");
        p1.setStok(10);
        p1.setBerat(500);
        p1.setFoto("https://via.placeholder.com/150");
        listProduk.add(p1);

        MBarang p2 = new MBarang();
        p2.setId_barang("2");
        p2.setNama_barang("Tas Ransell");
        p2.setHarga(150000);
        p2.setKategori("Aksesoris");
        p2.setStok(5);
        p2.setBerat(300);
        p2.setFoto("https://via.placeholder.com/150");
        listProduk.add(p2);

        // Set adapter
        adapter = new BarangHomeAdapter(listProduk);
        b.rvProduk.setLayoutManager(new GridLayoutManager(ctx, 2)); // grid 2 kolom
        b.rvProduk.setAdapter(adapter);

        return b.getRoot();
    }
}
