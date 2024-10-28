package com.tripointeknologi.saktoko.Activity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.databinding.ActivityKeranjangBinding;

public class KeranjangActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityKeranjangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKeranjangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}