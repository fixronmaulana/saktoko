package com.tripointeknologi.saktoko.Activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.GetData;
import com.tripointeknologi.saktoko.Utils.Pesan;
import com.tripointeknologi.saktoko.Utils.SessionApp;
import com.tripointeknologi.saktoko.databinding.ActivitySplasScreenBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplasScreenActivity extends AppCompatActivity implements Pesan.ClosePesan {
    ActivitySplasScreenBinding b;
    Context ctx;
    GetData data;
    SessionApp sa;
    boolean isLogin;
    Pesan pesan;
    boolean tutup = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        b = ActivitySplasScreenBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        ctx = this;
        data = new GetData(ctx);
        sa = new SessionApp(ctx);
        pesan = new Pesan(ctx,this);
        isLogin = sa.getLogin();
        hideSystemUI();
        new Handler().postDelayed(this::homeFunction, 1000);
        b.btnRefresh.setOnClickListener(v -> homeFunction());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("GAGAL", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    new SessionApp(ctx).storeToken(token);
                });
    }


    private void homeFunction() {
        b.btnRefresh.setVisibility(View.GONE);
        if (cekInternet()) {
            b.btnRefresh.setVisibility(View.GONE);
            if (isLogin) {
                cekUser();
            } else {
                Intent intent = new Intent(ctx, WellcomeActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            b.btnRefresh.setVisibility(View.VISIBLE);
        }
    }

    private boolean cekInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    b.tvStatusKoneksi.setText(R.string.menggunakan_wifi);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    b.tvStatusKoneksi.setText(R.string.paket_data);
                    break;
            }
            return true;
        } else {
            b.tvStatusKoneksi.setText(R.string.tidak_tersambung_internet);
            return false;
        }
    }

    @Override
    public void closePesan() {
        if (tutup) {
         onBackPressed();
        } else {
            cekUser();
        }
        pesan.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void cekUser() {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024 * 10);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        @SuppressLint("NotifyDataSetChanged")
        StringRequest stringRequest = new StringRequest(Request.Method.POST, data.baseurl() + "cek_pelanggan", response -> {
            if (response != null) {
                try {
                    JSONObject d = new JSONObject(response);
                    String status = d.getString("status");
                    Log.d("TES",response);
                    if (status.equals("ok")) {
                        Intent intent = new Intent(ctx, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (status.equals("block")) {
                        int[] elemen = {R.string.kesalahan_autentikasi,R.string.tidak_diizinkan_akses,
                                R.color.putih,R.drawable.bg_merah_radius,R.drawable.ic_locked,R.drawable.bg_btn_putih,R.color.abu_abu};
                        pesan.tampil(elemen);
                        tutup = true;
                    } else {
                        int[] elemen = {R.string.akses_ilegal,R.string.isi_pesan_ilegal,
                                R.color.putih,R.drawable.bg_merah_radius,R.drawable.ic_locked,R.drawable.bg_btn_putih,R.color.abu_abu};
                        pesan.tampil(elemen);
                        tutup = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    int[] elemen = {R.string.kesalahan_data,R.string.isi_pesan_kesalahan_data,
                            R.color.putih,R.drawable.bg_merah_radius,R.drawable.ic_kesalahan_data,R.drawable.bg_btn_putih,R.color.abu_abu};
                    pesan.tampil(elemen);
                }
            }
        }, e -> {
            pesan.show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("paket", data.paket());
                params.put("q",data.key());
                params.put("id", sa.getIdUser());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        decorView.setOnSystemUiVisibilityChangeListener(
                visibility -> {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

                }
        );
    }
}