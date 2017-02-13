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
import com.music.clocklive.wallpaper.adapters.ClockImageAdapter;
import com.music.clocklive.wallpaper.modelclass.ImageData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDigitalStyleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvDigitalClockStyle)
    RecyclerView rvDigitalClockStyle;
    @BindView(R.id.content_show_digital_style)
    RelativeLayout contentShowDigitalStyle;
    ClockImageAdapter clockImageAdapter;
    ArrayList<ImageData> lstDigitalClockStyleList=new ArrayList<ImageData>();
    RelativeLayout rlAds;
    AdView adView;
    InterstitialAd mInterstitialAd;
    String strng ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_digital_style);
        ButterKnife.bind(this);
        strng= getIntent().getStringExtra("ID");
        mInterstitialAd = new InterstitialAd(ShowDigitalStyleActivity.this);
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
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_1));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_2));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_3));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_4));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_5));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_6));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_7));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_8));
        lstDigitalClockStyleList.add(new ImageData(R.drawable.digital_style_9));

    }
    private void initiallize() {
        clockImageAdapter = new ClockImageAdapter(lstDigitalClockStyleList, this) {
            @Override
            public void onImageClick(int position, View view) {
                saveDigitalStyleImage("digitalstyle",lstDigitalClockStyleList.get(position).getIvImage());
                finish();
            }
        };
        rvDigitalClockStyle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvDigitalClockStyle.setHasFixedSize(true);
        rvDigitalClockStyle.setAdapter(clockImageAdapter);

    }
    private void saveDigitalStyleImage(String key,int digitalstyle) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, digitalstyle);
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
