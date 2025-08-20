package com.tripointeknologi.saktoko.Activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.GetData;
import com.tripointeknologi.saktoko.Utils.Pesan;
import com.tripointeknologi.saktoko.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements Pesan.ClosePesan {
    ActivityRegisterBinding b;
    Context ctx;
    GetData data;
    Pesan pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        b = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        ctx = this;
        data = new GetData(ctx);
        pesan = new Pesan(ctx, this);
        b.etNamaLengkap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int u = s.length();
                if (u < 4) {
                    b.tverrorNama.setAlpha(1.0f);
                    b.btnRegister.setEnabled(false);
                } else {
                    b.tverrorNama.setAlpha(0f);
                    b.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.etNoHP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int u = s.length();
                if (u < 9) {
                    b.tverrorHP.setAlpha(1.0f);
                    b.btnRegister.setEnabled(false);
                } else {
                    b.tverrorHP.setAlpha(0f);
                    b.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        b.btnRegister.setOnClickListener(v -> registrasi());
    }

    private void registrasi() {
        if (cek()) {
            b.loading.setVisibility(View.VISIBLE);
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024 * 10);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
            String url = data.baseurl() + "cek_no_hp";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                b.loading.setVisibility(View.GONE);
                try {
                    if (response != null) {
                        Log.d("ISI",response);
                        JSONObject d = new JSONObject(response);
                        String status = d.getString("status");
                        if (status.equals("ok")) {
                            Intent intent = new Intent(ctx, OTPActivity.class);
                            intent.putExtra("nama", b.etNamaLengkap.getText().toString());
                            intent.putExtra("no_hp", b.etNoHP.getText().toString().trim());
                            startActivity(intent);
                            finish();
                        } else if (status.equals("ada")) {
                            if (!pesan.isShowing()) {
                                pesan.tampil(R.drawable.bg_btn_putih, R.string.judul_hp_sudah_ada,
                                        R.string.pesan_hp_ada, R.drawable.ic_no_hp, R.color.abu_abu, R.drawable.bg_btn_putih);
                            }
                        } else {
                            showPesan();
                        }
                    }
                } catch (JSONException e) {
                    showPesan();
                }

            }, error -> showPesan()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("no_hp", b.etNoHP.getText().toString().trim());
                    params.put("q", data.key());
                    params.put("paket", data.paket());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


    @SuppressLint("SetTextI18n")
    private boolean cek() {
        String nama = b.etNamaLengkap.getText().toString();
        String noHp = b.etNoHP.getText().toString();
        if (nama.trim().isEmpty()) {
            b.etNamaLengkap.setText("Masukkan Nama Lengkap Terlebih Dahulu!");
            b.tverrorNama.setAlpha(1.0f);
            b.etNamaLengkap.requestFocus();
            return false;
        } else if (noHp.trim().isEmpty()) {
            b.tverrorHP.setText("Masukkan No HP Terlebih Dahulu!");
            b.tverrorHP.setAlpha(1.0f);
            b.etNoHP.requestFocus();
            return false;
        } else if (nama.length() < 6) {
            b.tverrorNama.setText("Nama Minimal 5 Karakter");
            b.tverrorNama.setAlpha(1.0f);
            b.etNamaLengkap.requestFocus();
            return false;
        } else if (noHp.length() < 9) {
            b.tverrorHP.setText("No. HP Tidak Lengkap");
            b.tverrorHP.setAlpha(1.0f);
            b.etNoHP.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    void showPesan() {
        b.loading.setVisibility(View.GONE);
        if (!pesan.isShowing()) {
            pesan.tampil();
        }
    }

    @Override
    public void closePesan() {
        if (pesan.isShowing()) {
            pesan.close();
        }
    }
}