package com.tripointeknologi.saktoko.Activity;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.GetData;
import com.tripointeknologi.saktoko.Utils.Pesan;
import com.tripointeknologi.saktoko.Utils.SessionApp;
import com.tripointeknologi.saktoko.databinding.ActivityOtpactivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity implements Pesan.ClosePesan {
    ActivityOtpactivityBinding b;
    FirebaseAuth mAuth;
    String no_hp, nama, cd;
    String verificationID;
    Context ctx;
    GetData data;
    SessionApp sa;
    Pesan pesan;
    CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        b = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        ctx = this;
        data = new GetData(ctx);
        sa = new SessionApp(ctx);
        pesan = new Pesan(ctx, this);
        no_hp = getIntent().getStringExtra("no_hp");
        nama = getIntent().getStringExtra("nama");
        b.tvNoTelp.setText(no_hp);
        mAuth = FirebaseAuth.getInstance();
        int j = no_hp.length();
        String no = "+62" + no_hp.substring(1, j);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(no)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        b.hitung.setVisibility(View.VISIBLE);
        b.btnKirimUlang.setVisibility(View.GONE);
        b.btnVerifikasi.setVisibility(View.VISIBLE);
        b.btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = b.kode.getText().toString().trim();
                if (code.length() == 6) { // Pastikan panjang kode 6
                    if (cd == null) {
                        verifikasi(code);
                    } else {
                        cancelTimer();
                        b.loading.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Tampilkan error jika kode kurang dari 6 karakter
                    Toast.makeText(ctx, "Kode verifikasi harus 6 karakter", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b.btnKirimUlang.setOnClickListener(v -> resendVerificationCode());

        startTimer();
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
            Log.d("CEK_VERIFIKSI", "Ini code force= " + forceResendingToken);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifikasi(code);
                cd = code;
            }
            if (verificationID == null) {
                if (!pesan.isShowing()) {
                    pesan.tampil(R.drawable.bg_btn_putih, R.string.verifikasi_gagal,
                            R.string.pesan_verifikasi_gagal, R.drawable.ic_no_hp, R.color.abu_abu, R.drawable.bg_btn_putih);
                }
                cancelTimer();
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if (!pesan.isShowing()) {
                pesan.tampil(R.drawable.bg_btn_putih, R.string.verifikasi_gagal,
                        e.getMessage(), R.drawable.ic_no_hp, R.color.abu_abu, R.drawable.bg_btn_putih);
            }
        }
    };

    public void verifikasi(String code) {
        b.kode.setText(code);
        cancelTimer();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);
        b.loading.setVisibility(View.VISIBLE);

    }

    @Override
    public void closePesan() {
        if (pesan.isShowing()) {
            pesan.close();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        task.getResult();
                        FirebaseUser user = task.getResult().getUser();
                        assert user != null;
                        String Uid = user.getUid();
                        b.loading.setVisibility(View.VISIBLE);
                        RequestQueue requestQueue;
                        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024 * 10);
                        Network network = new BasicNetwork(new HurlStack());
                        requestQueue = new RequestQueue(cache, network);
                        requestQueue.start();
                        String url = data.baseurl() + "registrasi";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                            b.loading.setVisibility(View.GONE);
                            try {
                                if (response != null) {
                                    JSONObject d = new JSONObject(response);
                                    String status = d.getString("status");
                                    String id = d.getString("id");
                                    if (status.equals("ok")) {
                                        sa.storelogin(id, nama, "", "", no_hp,"1", "", true);
                                        finish();
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
                                params.put("nama", nama);
                                params.put("no_hp", no_hp);
                                params.put("uid", Uid);
                                params.put("q", data.key());
                                params.put("paket", data.paket());
                                params.put("token", sa.getToken());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);

                    } else {
                        showPesan();
                    }
                });
    }

    void startTimer() {
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                b.hitung.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                b.hitung.setText("0");
                b.hitung.setVisibility(View.GONE);
                b.btnVerifikasi.setVisibility(View.GONE);
                b.btnKirimUlang.setVisibility(View.VISIBLE);
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
        b.hitung.setText("0");
        b.hitung.setVisibility(View.GONE);
        b.btnVerifikasi.setVisibility(View.VISIBLE);
        b.btnKirimUlang.setVisibility(View.GONE);
    }

    void showPesan() {
        b.loading.setVisibility(View.GONE);
        if (!pesan.isShowing()) {
            pesan.tampil();
        }
    }


    private void resendVerificationCode() {
        b.hitung.setVisibility(View.VISIBLE);
        b.btnKirimUlang.setVisibility(View.GONE);
        b.btnVerifikasi.setVisibility(View.VISIBLE);
        startTimer();
        int j = no_hp.length();
        String no = "+62" + no_hp.substring(1, j);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}