package com.tripointeknologi.saktoko.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.tripointeknologi.saktoko.Fragment.AkunFragment;
import com.tripointeknologi.saktoko.Fragment.HomeFragment;
import com.tripointeknologi.saktoko.Fragment.PesananFragment;
import com.tripointeknologi.saktoko.Fragment.ProdukFragment;
import com.tripointeknologi.saktoko.Fragment.ResepFragment;
import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    ActivityMainBinding b;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        ctx = this;
        b.bottomNavigationView.setOnItemSelectedListener(this);
        b.bottomNavigationView.setSelectedItemId(R.id.dataBeranda);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.dataBeranda:
                fragment = new HomeFragment();
                break;
            case R.id.dataProduk:
                fragment = new ProdukFragment();
                break;
            case R.id.dataResep:
                fragment = new ResepFragment();
                break;
            case R.id.dataPesanan:
                fragment = new PesananFragment();
                break;
            case R.id.dataAkun:
                fragment = new AkunFragment();
                break;
        }
        return loadFragment(fragment);

    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void gantiWarnaStatus(int warna, int text) {

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(text);
        window.setStatusBarColor(ContextCompat.getColor(ctx, warna));

    }
}

