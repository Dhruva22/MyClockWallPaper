package com.music.clocklive.wallpaper.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.custom.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationActivity extends BaseActivity {

    @BindView(R.id.tvInformation)
    CustomTextView tvInformation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_information)
    RelativeLayout contentInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
}
