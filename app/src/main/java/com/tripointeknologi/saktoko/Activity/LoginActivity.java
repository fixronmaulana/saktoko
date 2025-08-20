package com.tripointeknologi.saktoko.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.MaterialRippleLayout;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.Utils.SessionApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String URL_LOGIN = "https://saktoko.com/api/login"; // Sesuaikan API
    private static final String PAKET = "com.tripointeknologi.saktoko";
    private static final String Q_KEY = "5d4b17e3b87f086fc86ca63dddada6d480f1960f";

    private EditText etNoHP;
    private TextView tverrorHP;
    private ProgressBar loading;
    private SessionApp session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNoHP = findViewById(R.id.etNoHP);
        tverrorHP = findViewById(R.id.tverrorHP);
        loading = findViewById(R.id.loading);
        MaterialRippleLayout btnLogin = findViewById(R.id.btnLogin);

        session = new SessionApp(this);

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String noHp = etNoHP.getText().toString().trim();

        if (noHp.isEmpty()) {
            tverrorHP.setTextColor(ContextCompat.getColor(this, R.color.merah));
            tverrorHP.setText(R.string.error_no_hp_empty);
            return;
        }

        loading.setVisibility(android.view.View.VISIBLE);
        tverrorHP.setText("");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                response -> {
                    loading.setVisibility(android.view.View.GONE);
                    try {
                        Log.d(TAG, "Response: " + response);

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.optString("status", "");
                        String message = jsonObject.optString("message", getString(R.string.error_server));

                        if (status.equalsIgnoreCase("ada")) {
                            JSONObject data = jsonObject.optJSONObject("data");
                            if (data != null) {
                                session.storelogin(
                                        data.optString("id_pelanggan", ""),
                                        data.optString("nama", ""),
                                        data.optString("email", ""),
                                        data.optString("password", ""),
                                        data.optString("no_hp", ""),
                                        data.optString("tipe", ""),
                                        data.optString("foto", ""),
                                        true
                                );

                                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this, R.string.error_no_user_data, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: ", e);
                        Toast.makeText(this, R.string.error_invalid_response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loading.setVisibility(android.view.View.GONE);
                    Log.e(TAG, "Volley error: ", error);
                    Toast.makeText(this, getString(R.string.error_connection) + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("no_hp", noHp);
                params.put("paket", PAKET);
                params.put("q", Q_KEY);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
