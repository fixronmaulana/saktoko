package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripointeknologi.saktoko.Adapter.ResepAdapter;
import com.tripointeknologi.saktoko.Models.MResep;
import com.tripointeknologi.saktoko.databinding.FragmentResepBinding;

import java.util.ArrayList;
import java.util.List;

public class ResepFragment extends Fragment {

    private FragmentResepBinding b;
    private Context ctx;
    private ResepAdapter adapter;
    private List<MResep> listResep;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentResepBinding.inflate(inflater, container, false);

        // Inisialisasi list resep
        listResep = new ArrayList<>();
        loadDummyResep();

        // Set RecyclerView
        b.recyclerResep.setLayoutManager(new LinearLayoutManager(ctx));
        adapter = new ResepAdapter(ctx, listResep);
        b.recyclerResep.setAdapter(adapter);

        return b.getRoot();
    }

    private void loadDummyResep() {
        String baseUrl = "https://saktoko.com/upload/foto_menu/";

        String[][] data = {
                {"1", "Nasi Gorenggg", "23251003202430.png"},
                {"2", "Mie Ayam", "23251003202419.png"},
                {"3", "Bakso Spesial", "23251003202419.png"},
                {"4", "Soto Ayam", "23251003202419.png"},
                {"5", "Ayam Geprek", "23251003202419.png"}
        };

        for (String[] d : data) {
            listResep.add(new MResep(
                    d[0],
                    d[1],
                    baseUrl + d[2]
            ));
        }
    }
}
