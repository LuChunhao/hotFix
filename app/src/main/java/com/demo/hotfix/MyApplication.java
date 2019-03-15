package com.demo.hotfix;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.demo.hotfix.library.FixDexUtils;

public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 加载热修复的dex文件
        FixDexUtils.loadFixedDex(this);
    }
}
