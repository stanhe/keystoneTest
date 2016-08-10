package com.example.stan.keystonetest;

import android.app.Application;

import org.xutils.x;

/**
 * Created by stan on 16/8/9.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
