package com.tripointeknologi.saktoko.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import android.os.Handler;

import java.util.Objects;

public class CustomViewPager extends ViewPager {

    private final Handler handler = new Handler();
    int slideInterval = 3000;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            int currentItem = getCurrentItem();
            int nextItem = (currentItem == Objects.requireNonNull(getAdapter()).getCount() - 1) ? 0 : currentItem + 1;
            setCurrentItem(nextItem, true);
            handler.postDelayed(this, 10000);
        }
    };

    public CustomViewPager(Context context) {
        super(context);
        initAutoSlide();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAutoSlide();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h; // mendapatkan tinggi terbesar dari child
        }

        if (height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initAutoSlide() {
        startAutoSlide();
    }

    public void startAutoSlide() {
        handler.postDelayed(runnable, slideInterval);
    }

    public void stopAutoSlide() {
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoSlide();
    }

    public void setPageTransformer(FadePageTransformer fadePageTransformer) {
    }
}