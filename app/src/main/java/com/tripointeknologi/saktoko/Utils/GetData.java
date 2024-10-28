package com.tripointeknologi.saktoko.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;


public class GetData {
    Context ctx;

    public GetData(Context ctx) {
        this.ctx = ctx;
    }

    protected ApplicationInfo getInfo() {
        ApplicationInfo info;
        try {
            info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    public String baseurl() {
        ApplicationInfo info = getInfo();
        String url = "";
        if (info.metaData != null) {
            url = info.metaData.getString("baseurl");
        }
        return url;
    }

    public String key() {
        ApplicationInfo info = getInfo();
        String key = "";
        if (info.metaData != null) {
            key = info.metaData.getString("key");
        }
        return key;
    }

    public String paket() {
        return ctx.getPackageName();
    }


}