package com.music.clocklive.wallpaper.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.adapters.HandStyleImageAdapter;
import com.music.clocklive.wallpaper.modelclass.HandStyleImageData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowHandStyleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvHandStyle)
    RecyclerView rvHandStyle;
    @BindView(R.id.content_show_hand_style)
    RelativeLayout contentShowHandStyle;
    HandStyleImageAdapter handStyleImageAdapter;
    ArrayList<HandStyleImageData> lstHandStyleImageList=new ArrayList<HandStyleImageData>();
    int hourStyleimage,minStyleImage,secStyleImage,handStyleImage;
    RelativeLayout rlAds;
    AdView adView;
    InterstitialAd mInterstitialAd;
    String strng ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hand_style);
        ButterKnife.bind(this);
        strng=getIntent().getStringExtra("ID");
        mInterstitialAd = new InterstitialAd(ShowHandStyleActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();
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


        initiallize();

        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_1,R.drawable.hour_1,R.drawable.min_1,R.drawable.sec_1));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_2,R.drawable.hour_2,R.drawable.min_2,R.drawable.sec_2));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_3,R.drawable.hour_3,R.drawable.min_3,R.drawable.sec_3));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_4,R.drawable.hour_5,R.drawable.min_5,R.drawable.sec_5));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_5,R.drawable.hour_6,R.drawable.min_6,R.drawable.sec_6));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_6,R.drawable.hour_7,R.drawable.min_7,R.drawable.sec_7));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_7,R.drawable.hour_8,R.drawable.min_8,R.drawable.sec_8));
        lstHandStyleImageList.add(new HandStyleImageData(R.drawable.clockhands_prview_8,R.drawable.hour_9,R.drawable.min_9,R.drawable.sec_9));

    }
    private void initiallize() {
        handStyleImageAdapter = new HandStyleImageAdapter(lstHandStyleImageList, this) {
            @Override
            public void onImageClick(int position, View view) {
                handStyleImage=lstHandStyleImageList.get(position).getIvHandStyleImage();
                saveHandStyleImage("handstyleimage",handStyleImage);

                hourStyleimage=lstHandStyleImageList.get(position).getIvHourImage();
                saveHourStyleImage("hourstyleimage",hourStyleimage);

                minStyleImage=lstHandStyleImageList.get(position).getIvMinImage();
                saveMinStyleImage("minstyleimage",minStyleImage);

                secStyleImage=lstHandStyleImageList.get(position).getIvSecImage();
                saveSecStyleImage("secStyleimage",secStyleImage);
                finish();

            }
        };
        rvHandStyle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvHandStyle.setHasFixedSize(true);
        rvHandStyle.setAdapter(handStyleImageAdapter);

    }
    private void saveHandStyleImage(String key,int handStyle) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, handStyle);
        editor.commit();

    }

    private void saveHourStyleImage(String key,int hourStyle) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, hourStyle);
        editor.commit();

    }
    private void saveMinStyleImage(String key,int minStyle) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, minStyle);
        editor.commit();

    }
    private void saveSecStyleImage(String key,int secStyle) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,secStyle);
        editor.commit();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed()
    {
        if(strng.equals("SettingsActivity"))
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }
        else
        {
        }
        super.onBackPressed();
    }


}
