package com.tripointeknologi.saktoko.Utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.balysv.materialripple.MaterialRippleLayout;
import com.tripointeknologi.saktoko.R;


public class ValidasiInput implements TextWatcher {
    TextView tvError;
    MaterialRippleLayout btn;
    Context ctx;
    EditText edit;

    public ValidasiInput(Context ctx, EditText edit, TextView tvError, MaterialRippleLayout btn) {
        this.tvError = tvError;
        this.btn = btn;
        this.ctx = ctx;
        this.edit = edit;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
        int u = s.length();
        if (u == 0) {
            tvError.setAlpha(1.0f);
            btn.setEnabled(false);
        } else {
            tvError.setAlpha(0f);
            btn.setEnabled(true);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        int u = s.length();
        if (u == 0) {
            tvError.setAlpha(1.0f);
            btn.setEnabled(false);
        } else {
            tvError.setAlpha(0f);
            btn.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
