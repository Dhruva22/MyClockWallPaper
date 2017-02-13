package com.music.clocklive.wallpaper.activities;

import android.content.Context;
import android.content.Intent;
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
import com.music.clocklive.wallpaper.adapters.ClockImageAdapter;
import com.music.clocklive.wallpaper.modelclass.ImageData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowBackGroundActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvBackground)
    RecyclerView rvBackground;
    @BindView(R.id.content_show_back_ground)
    RelativeLayout contentShowBackGround;
    ClockImageAdapter clockImageAdapter;
    ArrayList<ImageData> lstClockBackgroundList=new ArrayList<ImageData>();
    private boolean galleyflag ;
    RelativeLayout rlAds;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_back_ground);
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


        initiallize();
        lstClockBackgroundList.add(new ImageData(R.drawable.back_1));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_2));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_3));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_4));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_5));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_6));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_7));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_8));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_9));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_10));
        lstClockBackgroundList.add(new ImageData(R.drawable.back_11));



        galleyflag=getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("gallaryflag",false);


    }

    private void initiallize() {
        clockImageAdapter = new ClockImageAdapter(lstClockBackgroundList, this) {
            @Override
            public void onImageClick(int position, View view) {

                saveClockBackgroundImage("clockbackground",lstClockBackgroundList.get(position).getIvImage());
                if (galleyflag){
                    galleyflag=false;
                    saveGalleryFlag("gallaryflag",galleyflag);
                }
                finish();

            }
        };
        rvBackground.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvBackground.setHasFixedSize(true);
        rvBackground.setAdapter(clockImageAdapter);

    }
    private void saveClockBackgroundImage(String key,int clockImage) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, clockImage);
        editor.commit();

    }
    private void saveGalleryFlag(String key,boolean galleryflag){
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,galleryflag);
        editor.commit();
    }


}
