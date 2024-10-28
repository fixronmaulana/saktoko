package com.tripointeknologi.saktoko.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripointeknologi.saktoko.R;
import com.tripointeknologi.saktoko.databinding.FragmentResepBinding;


public class ResepFragment extends Fragment {

    FragmentResepBinding b;
    Context ctx;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentResepBinding.inflate(inflater,container,false);
        return inflater.inflate(R.layout.fragment_resep, container, false);
    }
}