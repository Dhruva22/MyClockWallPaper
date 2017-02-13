package com.music.clocklive.wallpaper.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.custom.CustomTextView;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.music.clocklive.wallpaper.utills.Utiils.hasPermissions;

public class MySettingsActivity extends BaseActivity {
    RelativeLayout rlAds;
    AdView adView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvLableClockStyle)
    CustomTextView tvLableClockStyle;
    @BindView(R.id.tvSummaryClockStyle)
    CustomTextView tvSummaryClockStyle;
    @BindView(R.id.ivSelectedClockStyle)
    ImageView ivSelectedClockStyle;
    @BindView(R.id.rlClockStyle)
    RelativeLayout rlClockStyle;
    @BindView(R.id.tvLableDigitalClockStyle)
    CustomTextView tvLableDigitalClockStyle;
    @BindView(R.id.tvSummaryDigitalClockStyle)
    CustomTextView tvSummaryDigitalClockStyle;
    @BindView(R.id.ivSelectedDigitalClockStyle)
    ImageView ivSelectedDigitalClockStyle;
    @BindView(R.id.rlDigitalClockStyle)
    RelativeLayout rlDigitalClockStyle;
    @BindView(R.id.tvLableHandStyle)
    CustomTextView tvLableHandStyle;
    @BindView(R.id.tvSummaryHandStyle)
    CustomTextView tvSummaryHandStyle;
    @BindView(R.id.ivSelectedHandStyle)
    ImageView ivSelectedHandStyle;
    @BindView(R.id.rlHandStyle)
    RelativeLayout rlHandStyle;
    @BindView(R.id.tvLableMyBackground)
    CustomTextView tvLableMyBackground;
    @BindView(R.id.tvSummaryMyBackground)
    CustomTextView tvSummaryMyBackground;
    @BindView(R.id.ivSelectedMyBackground)
    ImageView ivSelectedMyBackground;
    @BindView(R.id.rlMyBackground)
    RelativeLayout rlMyBackground;
    @BindView(R.id.tvLableBackground)
    CustomTextView tvLableBackground;
    @BindView(R.id.tvSummaryBackground)
    CustomTextView tvSummaryBackground;
    @BindView(R.id.ivSelectedBackground)
    ImageView ivSelectedBackground;
    @BindView(R.id.rlBackground)
    RelativeLayout rlBackground;
    @BindView(R.id.tvLableShowSecond)
    CustomTextView tvLableShowSecond;
    @BindView(R.id.tvSummaryShowSecond)
    CustomTextView tvSummaryShowSecond;
    @BindView(R.id.chBox)
    CheckBox chBox;
    @BindView(R.id.rlShowSecond)
    RelativeLayout rlShowSecond;
    @BindView(R.id.content_setting)
    RelativeLayout contentSetting;
    InterstitialAd mInterstitialAd;
    String strng="";

    Uri myuri;
    Bitmap galleryBitmap, bitmap;
    private final int RESULT_GALLERY_IMG = 1,PIC_CROP = 2;
    int PERMISSION_ALL = 2;
    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.tvSettings)
    CustomTextView tvSettings;
    @BindView(R.id.tvLableMusic)
    CustomTextView tvLableMusic;
    @BindView(R.id.tvSummaryMusic)
    CustomTextView tvSummaryMusic;
    @BindView(R.id.rlMusic)
    RelativeLayout rlMusic;

    private boolean isgalleryImage, isgalleryImagespFlag, showSeondsCheckboxValue;
    int clockdialImage, digitalDialImage, clockBackground, clockHandStyleImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysettings);
        ButterKnife.bind(this);
        strng = getIntent().getStringExtra("ID");

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
        mInterstitialAd = new InterstitialAd(MySettingsActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.intestial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();

    }

    private void onClickOfrlClockStyle() {
        Intent intent = new Intent(MySettingsActivity.this, ShowClockDialActivity.class);
        if(getIntent().hasExtra("ID"))
        {
            strng = getIntent().getStringExtra("ID");
        }

        if(strng !=null && strng.equalsIgnoreCase("MainActivity"))
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }
        startActivity(intent);
    }

    private void onClickofrldigitalClockStyle() {
        Intent intent = new Intent(MySettingsActivity.this, ShowDigitalStyleActivity.class);
        if(getIntent().hasExtra("ID"))
        {
            strng = getIntent().getStringExtra("ID");
        }
        intent.putExtra("ID",strng);
        startActivity(intent);
    }

    private void onClickofrlHandstyle() {
        Intent intent = new Intent(MySettingsActivity.this, ShowHandStyleActivity.class);
        if(getIntent().hasExtra("ID"))
        {
            strng = getIntent().getStringExtra("ID");
        }
        intent.putExtra("ID",strng);
        startActivity(intent);
    }

    private void onClickofrlBackground() {
        Intent intent = new Intent(MySettingsActivity.this, ShowBackGroundActivity.class);
        if(getIntent().hasExtra("ID"))
        {
            strng = getIntent().getStringExtra("ID");
        }
        if(strng.equals("MainActivity"))
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }
        else
        {
        }
        startActivity(intent);
    }
    private void onClickofrlMusic() {
        Intent intent = new Intent(MySettingsActivity.this, MusicActivity.class);
        if(getIntent().hasExtra("ID"))
        {
            strng = getIntent().getStringExtra("ID");
        }
        if(strng.equals("MainActivity"))
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }
        else
        {
        }
        startActivity(intent);
    }


    private void onClickofrlMyBackground() {
        if (hasPermissions(MySettingsActivity.this, PERMISSIONS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(MySettingsActivity.this)){
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), RESULT_GALLERY_IMG);

                }
                else {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(myIntent);
                }
            }else {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), RESULT_GALLERY_IMG);

            }

            } else {
            requestToPermissions();
        }

    }

    /**
     * when take image from gallery get data of selected image
     *
     * @param data
     */
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            Uri selectedimage = data.getData();
            performcrop(selectedimage);

        }
    }

    @OnClick({R.id.rlClockStyle, R.id.rlDigitalClockStyle, R.id.rlHandStyle, R.id.rlBackground, R.id.rlMyBackground,R.id.rlMusic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlClockStyle:
                onClickOfrlClockStyle();
                break;
            case R.id.rlDigitalClockStyle:
                onClickofrldigitalClockStyle();
                break;
            case R.id.rlHandStyle:
                onClickofrlHandstyle();
                break;
            case R.id.rlBackground:
                onClickofrlBackground();
                break;
            case R.id.rlMyBackground:
                onClickofrlMyBackground();
                break;
            case R.id.rlMusic:
                onClickofrlMusic();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_GALLERY_IMG:
                onSelectFromGalleryResult(data);
                break;
            case PIC_CROP:
                if (resultCode== Activity.RESULT_OK){
                    if (data != null) {
                        Uri cropUri = data.getData();
                        saveBitmap("filepath", cropUri.toString());
                        if (galleryBitmap != null) {
                            galleryBitmap.recycle();
                            galleryBitmap = null;
                        }
                        try {
                            myuri = Uri.parse(this.getSharedPreferences("CLOCK_WALLPAPER", 0).getString("filepath", ""));
                            galleryBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(myuri));
                            isgalleryImage = true;
                            saveGalleryFlag("gallaryflag", isgalleryImage);
                            ivSelectedMyBackground.setImageBitmap(galleryBitmap);
                            ivSelectedBackground.setVisibility(View.INVISIBLE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;

        }
    }

    private void saveBitmap(String key, String getpath) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, getpath);
        editor.commit();

    }

    private void saveGalleryFlag(String key, boolean galleryflag) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, galleryflag);
        editor.commit();
    }

    private void saveShowSecondsCheckBoxValue(String key, boolean checkboxvalue) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, checkboxvalue);
        editor.commit();
    }

    private void requestToPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(MySettingsActivity.this)){
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), RESULT_GALLERY_IMG);

                    }
                    else {
                        Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivity(myIntent);
                    }
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), RESULT_GALLERY_IMG);

                }

            }

        } else {
            Toast.makeText(this, "permission is denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        clockdialImage = 0;
        digitalDialImage = 0;
        clockBackground = 0;
        clockHandStyleImage = 0;
        /**
         * check show seconds is on or off:
         */
        chBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
                saveShowSecondsCheckBoxValue("showsecondsflag", isSelected);
            }
        });

        /**
         *get clock features from sharedpreference:
         */
        clockdialImage = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getInt("clockdial", R.drawable.clock_dial_1);
        digitalDialImage = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getInt("digitalstyle", R.drawable.digital_style_1);
        clockBackground = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getInt("clockbackground", R.drawable.back_1);
        clockHandStyleImage = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getInt("handstyleimage", R.drawable.clockhands_prview_1);
        showSeondsCheckboxValue = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("showsecondsflag", true);
        chBox.setChecked(showSeondsCheckboxValue);

        /**
         * set clock features imageview:
         */
        if (clockdialImage==R.drawable.no_dial){
            ivSelectedClockStyle.setVisibility(View.INVISIBLE);
        }
        else {
            ivSelectedClockStyle.setVisibility(View.VISIBLE);
            ivSelectedClockStyle.setImageResource(clockdialImage);
        }
        if (digitalDialImage==R.drawable.no_digits){
            ivSelectedDigitalClockStyle.setVisibility(View.INVISIBLE);
        }
        else {
            ivSelectedDigitalClockStyle.setVisibility(View.VISIBLE);
            ivSelectedDigitalClockStyle.setImageResource(digitalDialImage);
        }
        ivSelectedHandStyle.setImageResource(clockHandStyleImage);
        isgalleryImagespFlag = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("gallaryflag", false);

        try {
            if (isgalleryImagespFlag) {
                if (hasPermissions(MySettingsActivity.this, PERMISSIONS)) {
                    ivSelectedMyBackground.setVisibility(View.VISIBLE);
                    myuri = Uri.parse(this.getSharedPreferences("CLOCK_WALLPAPER", 0).getString("filepath", ""));
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(myuri));
                    ivSelectedMyBackground.setImageBitmap(bitmap);
                    ivSelectedBackground.setVisibility(View.INVISIBLE);
                } else {
                    requestToPermissions();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (isgalleryImagespFlag == false) {
            ivSelectedBackground.setVisibility(View.VISIBLE);
            ivSelectedBackground.setImageResource(clockBackground);
            ivSelectedMyBackground.setVisibility(View.INVISIBLE);
        }

    }
    private Bitmap performcrop(Uri uri) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 2);
            //indicate output X and Y
            cropIntent.putExtra("outputX", width);
            cropIntent.putExtra("outputY", height);

            //retrieve data on return
            cropIntent.putExtra("return_data", true);

            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

        return null;
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if (strng.equals("SettingsActivity")){

        }else {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }
        super.onBackPressed();
    }
}
