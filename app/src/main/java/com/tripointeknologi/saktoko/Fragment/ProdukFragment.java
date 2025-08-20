package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.databinding.FragmentProdukBinding;

public class ProdukFragment extends Fragment {
    FragmentProdukBinding b;
    Context ctx;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentProdukBinding.inflate(inflater,container,false);
        return b.getRoot();
    }
}