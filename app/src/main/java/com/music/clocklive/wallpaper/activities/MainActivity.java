package com.music.clocklive.wallpaper.activities;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.custom.CustomTextView;
import com.music.clocklive.wallpaper.service.ClockWallpaperService;
import com.music.clocklive.wallpaper.utills.Utiils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tvMyClockWallPaper)
    CustomTextView tvMyClockWallPaper;
    @BindView(R.id.ivRateApp)
    ImageView ivRateApp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSetClock)
    CustomTextView tvSetClock;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.clMainActivity)
    CoordinatorLayout clMainActivity;
    RelativeLayout rlAds;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rlAds = (RelativeLayout) findViewById(R.id.rlAds);
        adView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                rlAds.setVisibility(View.VISIBLE);
                super.onAdLoaded();

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

            }
        });

    }

    public void setClockLw(View v) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, ClockWallpaperService.class));
        startActivity(intent);
    }


    public void showSettings(View v) {
        Intent intent = new Intent(MainActivity.this, MySettingsActivity.class);
        intent.putExtra("ID","MainActivity");
        startActivity(intent);
    }

    @OnClick(R.id.ivRateApp)
    public void onClick() {
        Utiils.rateapp(MainActivity.this);
    }
    public void dialogForExit() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.item_exit_alert);
        Button btnAlertOk = (Button) dialog.findViewById(R.id.btnAlertExit);
        Button btnAlertCancle = (Button) dialog.findViewById(R.id.btnAlertCancle);
        btnAlertOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAlertCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        dialogForExit();
    }
}
