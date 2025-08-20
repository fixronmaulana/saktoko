package com.tripointeknologi.saktoko.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tripointeknologi.saktoko.Adapter.BannerAdapter;
import com.tripointeknologi.saktoko.Adapter.BarangHomeAdapter;
import com.tripointeknologi.saktoko.Adapter.KategoriAdapter;
import com.tripointeknologi.saktoko.Adapter.ResepAdapter;
import com.tripointeknologi.saktoko.Models.MBarang;
import com.tripointeknologi.saktoko.Models.MKategori;
import com.tripointeknologi.saktoko.Models.MResep;
import com.tripointeknologi.saktoko.Models.Mbanner;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.GetData;
import com.tripointeknologi.saktoko.Utils.Pesan;
import com.tripointeknologi.saktoko.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements Pesan.ClosePesan, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    FragmentHomeBinding b;
    Context ctx;
    GetData data;
    Pesan pesan;

    List<Mbanner> list = new ArrayList<>();
    BannerAdapter bannerAdapter;

    List<MKategori> listkategori = new ArrayList<>();
    KategoriAdapter kategoriAdapter;

    List<MResep> listResep = new ArrayList<>();
    ResepAdapter resepAdapter;

    List<MBarang> listbarang = new ArrayList<>();
    BarangHomeAdapter barangHomeAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
        data = new GetData(ctx);
        pesan = new Pesan(ctx, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentHomeBinding.inflate(inflater, container, false);

        b.sRefresh.setRefreshing(false);
        b.sRefresh.setOnRefreshListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(ctx, 5);
        b.rvkategori.setHasFixedSize(true);
        b.rvkategori.setLayoutManager(layoutManager);

        LinearLayoutManager lMResep = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        b.rvResep.setHasFixedSize(true);
        b.rvResep.setLayoutManager(lMResep);

        GridLayoutManager layoutBarang = new GridLayoutManager(ctx, 2);
        b.rvBarang.setLayoutManager(layoutBarang);
        b.rvBarang.setHasFixedSize(true);

        loadHome();
        return b.getRoot();
    }

    private void loadHome() {
        listkategori.clear();

        Log.d(TAG, "Memulai request data Home...");

        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024 * 10);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        @SuppressLint("NotifyDataSetChanged")
        StringRequest stringRequest = new StringRequest(Request.Method.POST, data.baseurl() + "get_home", response -> {
            try {
                Log.d(TAG, "Response mentah: " + response);

                b.loading.setVisibility(View.GONE);
                b.sRefresh.setRefreshing(false);
                b.sRefresh.setVisibility(View.VISIBLE);
                b.shimmer.setVisibility(View.GONE);

                if (response != null) {
                    JSONObject d = new JSONObject(response);
                    String status = d.getString("status");

                    if (status.equals("ok")) {
                        String banner = d.getString("banner");
                        String kategori = d.getString("kategori");
                        String resep = d.getString("resep");
                        String barang = d.getString("barang");

                        Gson gson = new Gson();
                        Type typeBanner = new TypeToken<List<Mbanner>>() {}.getType();
                        list = gson.fromJson(banner, typeBanner);
                        bannerAdapter = new BannerAdapter(ctx, list);
                        b.vPBanner.setAdapter(bannerAdapter);

                        Gson gsonKategori = new Gson();
                        Type typeKategori = new TypeToken<List<MKategori>>() {}.getType();
                        listkategori = gsonKategori.fromJson(kategori, typeKategori);
                        kategoriAdapter = new KategoriAdapter(listkategori);
                        b.rvkategori.setAdapter(kategoriAdapter);

                        Gson gResep = new Gson();
                        Type tResep = new TypeToken<List<MResep>>() {}.getType();
                        listResep = gResep.fromJson(resep, tResep);
//                        resepAdapter = new ResepAdapter(ctx, listResep);
                        b.rvResep.setAdapter(resepAdapter);

                        Gson gBarang = new Gson();
                        Type tBarang = new TypeToken<List<MBarang>>() {}.getType();
                        listbarang = gBarang.fromJson(barang, tBarang);
                        barangHomeAdapter = new BarangHomeAdapter(listbarang);
                        b.rvBarang.setAdapter(barangHomeAdapter);

                        Log.d(TAG, "Data Home berhasil dimuat.");
                    } else {
                        Log.w(TAG, "Status bukan OK: " + status);
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "JSON Parsing error", e); // stack trace lengkap
                int[] elemen = {R.string.kesalahan_data, R.string.isi_pesan_kesalahan_data,
                        R.color.putih, R.drawable.bg_merah_radius, R.drawable.ic_kesalahan_data,
                        R.drawable.bg_btn_putih, R.color.abu_abu};
                pesan.tampil(elemen);
            } catch (Exception e) {
                Log.e(TAG, "Error umum saat memproses response", e); // stack trace lengkap
            }
        }, (VolleyError e) -> {
            Log.e(TAG, "Volley error saat request get_home", e); // stack trace lengkap
            b.sRefresh.setRefreshing(false);
            b.loading.setVisibility(View.GONE);
            pesan.show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("paket", data.paket());
                params.put("q", data.key());
                Log.d(TAG, "Params dikirim: " + params.toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void closePesan() {
        if (pesan.isShowing()) {
            pesan.close();
        }
    }

    @Override
    public void onRefresh() {
        loadHome();
    }
}
