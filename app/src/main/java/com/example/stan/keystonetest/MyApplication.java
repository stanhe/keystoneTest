package com.example.stan.keystonetest;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.xutils.x;

/**
 * Created by stan on 16/8/9.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
