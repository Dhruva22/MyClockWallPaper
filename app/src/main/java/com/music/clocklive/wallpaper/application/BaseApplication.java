package com.music.clocklive.wallpaper.application;

import android.app.Application;
import android.content.Context;


/**
 * Created by sellnews on 15/9/16.
 */
public class BaseApplication extends Application {
    public static final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //MobileAds.initialize(this, getResources().getString(R.string.app_id));

        // MobileAds.initialize(this, getString(R.string.app_id));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }
    public static BaseApplication getInstance() {
        return instance;
    }
}
