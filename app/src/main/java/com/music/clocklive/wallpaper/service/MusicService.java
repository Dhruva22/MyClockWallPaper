package com.music.clocklive.wallpaper.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.music.clocklive.wallpaper.receiver.AlarmReceiver;
/**
 * Created by sellnews on 27/1/17.
 */

public class MusicService extends Service
{

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private boolean hourFlag=true;
    public static final String TAG = "MusicServiceTag";

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarmMgr= (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent receiverintent=new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent=PendingIntent.getBroadcast(getApplicationContext(),0,receiverintent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),alarmIntent);


        return super.onStartCommand(intent, flags, startId);

    }
    private void saveHourFlag(String key, Boolean selectedTime) {
        SharedPreferences preferences = this.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }




}