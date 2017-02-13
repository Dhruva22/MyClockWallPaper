package com.music.clocklive.wallpaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.music.clocklive.wallpaper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.ivSplash)
    ImageView ivSplash;
    @BindView(R.id.content_splash_screen)
    LinearLayout contentSplashScreen;
    private static int SPLASH_TIME_OUT = 3000;
    CountDownTimer waitTimer;
    InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        mInterstitialAd = new InterstitialAd(SplashScreenActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();


        waitTimer = new CountDownTimer(SPLASH_TIME_OUT, 1000) {

            public void onTick(long millisUntilFinished) {
                //called every 300 milliseconds, which could be used to
                //send messages or some other action
            }

            public void onFinish() {

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

                finish();

            }
        }.start();


    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
