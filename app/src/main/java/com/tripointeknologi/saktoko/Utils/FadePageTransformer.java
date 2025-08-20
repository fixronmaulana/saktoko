package com.tripointeknologi.saktoko.Utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class FadePageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setTranslationX(-position * page.getWidth());

        if (position <= -1 || position >= 1) {
            page.setAlpha(0);
        } else if (position == 0) {
            page.setAlpha(1);
        } else {
            page.setAlpha(1 - Math.abs(position));
        }
    }
}
