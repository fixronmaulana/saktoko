package com.tripointeknologi.saktoko.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.tripointeknologi.saktoko.Models.Mbanner;
import com.tripointeknologi.saktoko.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private Context ctx;
    List<Mbanner> list;

    public BannerAdapter(Context ctx, List<Mbanner> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.layout_banner, container, false);
        ImageView imageView = view.findViewById(R.id.imgBanner);
        Glide.with(ctx)
                .load(list.get(i % list.size()).getBanner())
                .centerCrop()
                .placeholder(R.drawable.banner_kosong)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}