package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tripointeknologi.saktoko.Adapter.BarangHomeAdapter;
import com.tripointeknologi.saktoko.Models.MBarang;
import com.tripointeknologi.saktoko.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdukFragment extends Fragment {

    private RecyclerView rvProduk;
    private SearchView searchViewProduk;
    private Spinner spinnerKategori;

    private List<MBarang> listProduk;       // data original dari API
    private List<MBarang> listFiltered;     // data hasil filter / search
    private BarangHomeAdapter produkAdapter;

    private RequestQueue requestQueue;
    private Context context;

    // daftar kategori manual dulu, nanti bisa dynamic kalau endpoint tersedia
    private String[] daftarKategori = {"Semua", "Daging", "Ikan", "Sayuran", "Buah Buahan", "Bumbu"};

    public ProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produk, container, false);

        context = getContext();
        rvProduk = view.findViewById(R.id.rvProduk);
        searchViewProduk = view.findViewById(R.id.searchViewProduk);
        spinnerKategori = view.findViewById(R.id.spinnerKategori);

        // Setup RecyclerView (grid 2 kolom)
        rvProduk.setLayoutManager(new GridLayoutManager(context, 2));
        listProduk = new ArrayList<>();
        listFiltered = new ArrayList<>();
        produkAdapter = new BarangHomeAdapter(listFiltered);
        rvProduk.setAdapter(produkAdapter);

        requestQueue = Volley.newRequestQueue(context);

        // Setup Spinner kategori
        ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                daftarKategori
        );
        spinnerKategori.setAdapter(kategoriAdapter);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // tidak ada aksi
            }
        });

        // Setup SearchView
        searchViewProduk.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData();
                return true;
            }
        });

        // load data produk pertama kali
        loadProduk();

        return view;
    }

    private void loadProduk() {
        String url = "https://saktoko.com/api/barang";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        String status = obj.getString("status");

                        if (status.equals("sukses")) {
                            JSONArray dataBarang = obj.getJSONArray("data");

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<MBarang>>() {}.getType();
                            List<MBarang> barangList = gson.fromJson(dataBarang.toString(), listType);

                            listProduk.clear();
                            listProduk.addAll(barangList);

                            // tampilkan semua data dulu
                            filterData();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ProdukFragment", "JSON error: " + e.getMessage());
                    }
                },
                error -> Log.e("ProdukFragment", "Volley error: " + error.getMessage())) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("paket", "com.tripointeknologi.saktoko");
                params.put("q", "5d4b17e3b87f086fc86ca63dddada6d480f1960f");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void filterData() {
        String query = searchViewProduk.getQuery().toString().toLowerCase().trim();
        String kategoriDipilih = spinnerKategori.getSelectedItem().toString();

        listFiltered.clear();

        for (MBarang barang : listProduk) {
            boolean cocokKategori = kategoriDipilih.equals("Semua") ||
                    (barang.getKategori() != null && barang.getKategori().equalsIgnoreCase(kategoriDipilih));

            boolean cocokSearch = query.isEmpty() ||
                    (barang.getNama_barang() != null && barang.getNama_barang().toLowerCase().contains(query));

            if (cocokKategori && cocokSearch) {
                listFiltered.add(barang);
            }
        }

        produkAdapter.notifyDataSetChanged();
    }
}
