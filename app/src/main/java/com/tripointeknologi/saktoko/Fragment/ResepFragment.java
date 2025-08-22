package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tripointeknologi.saktoko.Adapter.ResepAdapter;
import com.tripointeknologi.saktoko.Models.MResep;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.NukeSSLCerts;
import com.tripointeknologi.saktoko.databinding.FragmentResepBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class ResepFragment extends Fragment {

    private FragmentResepBinding b;
    private Context ctx;
    private ResepAdapter adapter;
    private List<MResep> listResep;
    private RequestQueue requestQueue;

    private static final String URL_API = "https://saktoko.com/api/resep"; // endpoint backend kamu
    private static final String PAKET = "com.tripointeknologi.saktoko";
    private static final String Q_KEY = "5d4b17e3b87f086fc86ca63dddada6d480f1960f"; // key sesuai backend

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;

        // 🔑 aktifkan bypass SSL sebelum buat RequestQueue
        NukeSSLCerts.nuke();
        SSLSocketFactory sf = HttpsURLConnection.getDefaultSSLSocketFactory();
        requestQueue = Volley.newRequestQueue(ctx, new HurlStack(null, sf));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentResepBinding.inflate(inflater, container, false);

        listResep = new ArrayList<>();
        adapter = new ResepAdapter(ctx, listResep);

        b.recyclerResep.setLayoutManager(new LinearLayoutManager(ctx));
        b.recyclerResep.setAdapter(adapter);

        fetchResep();

        return b.getRoot();
    }

    private void fetchResep() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_API,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status", "gagal");

                        if (status.equals("sukses")) {
                            JSONArray dataArray = jsonResponse.getJSONArray("data");
                            listResep.clear();

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject obj = dataArray.getJSONObject(i);

                                String id = obj.getString("id_resep");
                                String nama = obj.getString("nama_menu");
                                String deskripsi = obj.optString("deskripsi", "");
                                String foto = obj.getString("foto_menu");
                                String kategori = obj.optString("kategori_resep", "");

                                MResep resep = new MResep(id, nama, foto);
                                resep.setDeskripsi(deskripsi);
                                resep.setKategori(kategori);

                                listResep.add(resep);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ctx, "Data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ctx, "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(ctx, "Volley Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error: ", error);
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("q", Q_KEY);
                params.put("paket", PAKET);
                return params;
            }
        };

        requestQueue.add(request);
    }
}
