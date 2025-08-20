package com.tripointeknologi.saktoko.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.tripointeknologi.saktoko.databinding.ActivityWellcomeBinding;

public class WellcomeActivity extends AppCompatActivity {
    ActivityWellcomeBinding b;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        b = ActivityWellcomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        ctx = this;

        b.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ctx,LoginActivity.class);
            startActivity(intent);
        });

        b.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ctx,RegisterActivity.class);
            startActivity(intent);
        });

    }
}