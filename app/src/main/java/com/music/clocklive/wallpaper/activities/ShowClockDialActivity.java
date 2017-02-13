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
import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.adapters.ClockImageAdapter;
import com.music.clocklive.wallpaper.modelclass.ImageData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowClockDialActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvClockStyle)
    RecyclerView rvClockStyle;
    @BindView(R.id.content_show_clock_dial)
    RelativeLayout contentShowClockDial;
    ClockImageAdapter clockImageAdapter;
    ArrayList<ImageData> lstClockDialList=new ArrayList<ImageData>();
    RelativeLayout rlAds;
    AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clock_dial);
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
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_1));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_2));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_3));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_4));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_5));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_6));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_7));
        lstClockDialList.add(new ImageData(R.drawable.clock_dial_8));


        lstClockDialList.add(new ImageData(R.drawable.no_dial));


    }
    private void initiallize() {
        clockImageAdapter = new ClockImageAdapter(lstClockDialList, this) {
            @Override
            public void onImageClick(int position, View view) {
                saveClockDialImage("clockdial",lstClockDialList.get(position).getIvImage());
                finish();
            }
        };
        rvClockStyle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvClockStyle.setHasFixedSize(true);
        rvClockStyle.setAdapter(clockImageAdapter);

    }
    private void saveClockDialImage(String key,int clockDial) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, clockDial);
        editor.commit();

    }

}
