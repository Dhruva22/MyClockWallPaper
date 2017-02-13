package com.music.clocklive.wallpaper.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaccess.datetimepicker.TimePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.custom.CustomTextView;
import com.music.clocklive.wallpaper.service.MusicService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.music.clocklive.wallpaper.utills.Utiils.hasPermissions;


public class MusicActivity extends BaseActivity implements TimePickerCallback {

    @BindView(R.id.tvMusic)
    CustomTextView tvMusic;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_music)


    RelativeLayout contentMusic;
    MediaPlayer mediaPlayer;

    String[] alarmtone = {"bell.mp3", "Cuckoo_bird_sound.mp3", "Ringing_clock.mp3", "Short_ringtone.mp3"};
    String[] galleryTone = {"bell.mp3", "Cuckoo_bird_sound.mp3"};
    String selectedRingtone, ringtone, time;
    int PERMISSION = 0;
    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int position = 0;
    @BindView(R.id.tvEveryHour)
    TextView tvEveryHour;
    @BindView(R.id.cbEveryHour)
    CheckBox cbEveryHour;
    @BindView(R.id.tvScheduledTime)
    TextView tvScheduledTime;
    @BindView(R.id.cbScheduledTime)
    CheckBox cbScheduledTime;
    @BindView(R.id.btnSetTime)
    Button btnSetTime;
    @BindView(R.id.etHour)
    EditText etHour;
    @BindView(R.id.tvColon)
    TextView tvColon;
    @BindView(R.id.etMin)
    EditText etMin;
    @BindView(R.id.btnSelectMusic)
    Button btnSelectMusic;
    @BindView(R.id.etMusicFileName)
    EditText etMusicFileName;
    @BindView(R.id.ivInfo)
    ImageView ivInfo;

    private Boolean everyHourChkboxValue, ScheduleTimeChkboxValue, hourFlag;
    boolean isCameFirstTime = true;
    RelativeLayout rlAds;
    AdView adView;
    Intent serviceintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
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

        serviceintent = new Intent(MusicActivity.this, MusicService.class);
        /**
         * get data from sharedpreference :
         */
        ScheduleTimeChkboxValue = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("FixedChecked", false);
        cbScheduledTime.setChecked(ScheduleTimeChkboxValue);
        everyHourChkboxValue = this.getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("HourChecked", false);
        cbEveryHour.setChecked(everyHourChkboxValue);

        ringtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
        etMusicFileName.setText(ringtone);
        saveMusicFile("Ringtone", ringtone);
        cbEveryHour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (everyHourChkboxValue && isCameFirstTime) {
                    isCameFirstTime = false;
                    return;
                }
                saveEveryHour("HourChecked", isChecked);

                if (isChecked) {

                    hourFlag = true;
                    saveHourFlag("hourflag", hourFlag);
                    // startService(new Intent(getBaseContext(), MusicService.class));
                    startService(serviceintent);
                } else {
                    hourFlag = false;
                    saveHourFlag("hourflag", hourFlag);
                    freezMediaPlayer();

                }
            }
        });

        cbScheduledTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveFixedTime("FixedChecked", isChecked);
                if (isChecked) {
                    //startService(new Intent(getBaseContext(), MusicService.class));
                    startService(serviceintent);
                } else {
                    freezMediaPlayer();
                    etHour.setText("00");
                    etMin.setText("00");

                }
            }
        });


        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragmentDialog.
                        newInstance(true).show(getSupportFragmentManager(),
                        "TimePickerFragmentDialog");
            }
        });

        btnSelectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleTimeChkboxValue = getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("FixedChecked", false);
                everyHourChkboxValue = getSharedPreferences("CLOCK_WALLPAPER", 0).getBoolean("HourChecked", false);


                if (everyHourChkboxValue && ScheduleTimeChkboxValue) {
                    selectedRingtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
                    saveMusicFile("Ringtone", selectedRingtone);
                    etMusicFileName.setText(selectedRingtone);
                    showDialogForGallery();
                } else if (everyHourChkboxValue && !ScheduleTimeChkboxValue) {
                    selectedRingtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
                    saveMusicFile("Ringtone", selectedRingtone);
                    etMusicFileName.setText(selectedRingtone);
                    showDialogOfListForAlarmTone();
                } else if (ScheduleTimeChkboxValue && !everyHourChkboxValue) {
                    selectedRingtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
                    saveMusicFile("Ringtone", selectedRingtone);
                    etMusicFileName.setText(selectedRingtone);
                    showDialogForGallery();

                } else {
                    etMusicFileName.setText("bell.mp3");
                    Toast.makeText(MusicActivity.this, "Please select any state! ", Toast.LENGTH_SHORT).show();
                    stopService(serviceintent);
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        freezMediaPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        freezMediaPlayer();
    }

    @Override
    public void onTimeSet(long timeOnly, long dateWithTime) {
        etHour.setText(new SimpleDateFormat("HH").format(new Date(timeOnly)));
        etMin.setText(new SimpleDateFormat("mm").format(new Date(timeOnly)));
        time = etHour.getText() + ":" + etMin.getText();

        saveScheduledTime("ScheduledTime", time);
        saveFixedTime("playsound", true);
        startService(serviceintent);

        // startService(new Intent(getBaseContext(), MusicService.class));
    }

    private void showDialogForGallery() {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(MusicActivity.this);
        alertdialogbuilder.setTitle("Set notification Ringtone");
        ringtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
        etMusicFileName.setText(ringtone);
        for (int i = 0; i < galleryTone.length; i++) {
            if (galleryTone[i].equals(ringtone)) {
                position = i;
            }

        }

        alertdialogbuilder.setSingleChoiceItems(galleryTone, position, new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedRingtone = Arrays.asList(galleryTone).get(which);

                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }

                mediaPlayer = new MediaPlayer();
                if (!mediaPlayer.isPlaying()) {
                    playSound(selectedRingtone);
                }


            }
        });

        alertdialogbuilder.setPositiveButton("Ok", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                saveMusicFile("Ringtone", selectedRingtone);
                etMusicFileName.setText(selectedRingtone);
                freezMediaPlayer();
            }
        });

        alertdialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                freezMediaPlayer();
            }
        });

        alertdialogbuilder.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (hasPermissions(MusicActivity.this, PERMISSIONS)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.canDrawOverlays(MusicActivity.this)) {
                            Intent intent = new Intent();
                            intent.setType("audio/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(Intent.createChooser(
                                    intent, "Open Audio (mp3) file"), 1);
                        } else {
                            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            startActivity(myIntent);
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.setType("audio/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(
                                intent, "Open Audio (mp3) file"), 1);
                    }

                } else {
                    requestToPermissions();
                }

            }


        });

        AlertDialog dialog = alertdialogbuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                freezMediaPlayer();
            }
        });
    }

    private void showDialogOfListForAlarmTone() {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(MusicActivity.this);
        alertdialogbuilder.setTitle("Set notification Ringtone");
        ringtone = getSharedPreferences("CLOCK_WALLPAPER", 0).getString("Ringtone", "bell.mp3");
        etMusicFileName.setText(ringtone);
        for (int i = 0; i < alarmtone.length; i++) {

            if (alarmtone[i].equals(ringtone)) {
                position = i;
            }

        }
        alertdialogbuilder.setSingleChoiceItems(alarmtone, position, new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedRingtone = Arrays.asList(alarmtone).get(which);

                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }

                mediaPlayer = new MediaPlayer();
                if (!mediaPlayer.isPlaying()) {
                    playSound(selectedRingtone);
                }


            }
        });

        alertdialogbuilder.setPositiveButton("Ok", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                saveMusicFile("Ringtone", selectedRingtone);
                etMusicFileName.setText(selectedRingtone);
                freezMediaPlayer();
            }
        });

        alertdialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                freezMediaPlayer();
            }
        });

        AlertDialog dialog = alertdialogbuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                freezMediaPlayer();
            }
        });

    }

    private void playSound(String fileName) {
        try {

            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            AssetFileDescriptor afd = getAssets().openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayers) {
                    mediaPlayer = mediaPlayers;
                    mediaPlayer.start();
                    mediaPlayer.setLooping(false);
                }
            });

            afd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound(Context context, Uri musicUri) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(context, musicUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayers) {
                mediaPlayer = mediaPlayers;
                mediaPlayer.start();
                mediaPlayer.setLooping(false);
            }
        });


    }

    public void freezMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            try {
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void saveMusicFile(String key, String selectedRingtone) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, selectedRingtone);
        editor.commit();
    }

    private void saveScheduledTime(String key, String selectedTime) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, selectedTime);
        editor.commit();
    }

    private void saveEveryHour(String key, Boolean selectedTime) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }

    private void saveFixedTime(String key, Boolean selectedTime) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }

    private void saveHourFlag(String key, Boolean selectedTime) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            if (requestCode == 1 && resultCode == RESULT_OK) {

                Uri data1 = data.getData();
                saveMusicFile("Ringtone", data1 + "");
                etMusicFileName.setText(data1 + ".mp3");
                saveFixedTime("playsound", true);
                playSound(getApplicationContext(), data1);

            }
        }
    }

    private void requestToPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(MusicActivity.this)) {
                        Intent intent = new Intent();
                        intent.setType("audio/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(
                                intent, "Open Audio (mp3) file"), 1);
                    } else {
                        Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivity(myIntent);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(
                            intent, "Open Audio (mp3) file"), 1);
                }
            }

        } else {
            Toast.makeText(this, "permission is denied", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.ivInfo)
    public void onClick()
    {
        Intent infoIntent = new Intent(MusicActivity.this,InformationActivity.class);
        startActivity(infoIntent);
    }
}