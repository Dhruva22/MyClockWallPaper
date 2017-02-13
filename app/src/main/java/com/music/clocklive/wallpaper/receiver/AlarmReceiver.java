package com.music.clocklive.wallpaper.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.music.clocklive.wallpaper.service.MusicService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Mishtiii on 28-01-2017.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver
{
    private String time;
    private Boolean isEveryHour,isScheduledTime,playSoundFlag,hourFlag,hourSoundFlag;
    MediaPlayer mediaPlayer;
    private String musicString;
    private  String sysHour,sysMin,hour,min;
    private Long currentTime;
    private boolean flag = false;
    String[] alarmtone = {"bell.mp3", "Cuckoo_bird_sound.mp3","Ringing_clock.mp3","Short_ringtone.mp3"};


    // The app's AlarmManager, which provides access to the system alarm services.

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, MusicService.class);
        startWakefulService(context,service);
        musicString = context.getSharedPreferences("CLOCK_WALLPAPER",0).getString("Ringtone","");

        time = context.getSharedPreferences("CLOCK_WALLPAPER",0).getString("ScheduledTime","00:00");
        isEveryHour = context.getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("HourChecked", false);
        isScheduledTime =context.getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("FixedChecked", false);
        playSoundFlag =context.getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("playsound",true);
        hourFlag =context.getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("hourflag",true);
        hourSoundFlag=context.getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("hoursound",true);

        hour = time.substring(0,2);
        min = time.substring(3,5);

        currentTime = System.currentTimeMillis();
        sysHour=new SimpleDateFormat("HH").format(new Date(currentTime));
        sysMin=new SimpleDateFormat("mm").format(new Date(currentTime));

        /**
         * set sound on scheduledTime:
         */
        if (isScheduledTime==true){
            if (isEveryHour==false){
                if (sysHour.equals(hour) && sysMin.equals(min)){
                    if (playSoundFlag){
                        if(Arrays.asList(alarmtone).contains(musicString)){
                            playSound(context,musicString,1);
                            playSoundFlag=false;
                            saveFixedTime("playsound",playSoundFlag,context);
                        }
                        else{
                            playSound(context,Uri.parse(musicString));
                            playSoundFlag=false;
                            saveFixedTime("playsound",playSoundFlag,context);
                        }

                    }
                    else
                    {
                        freezMediaPlayer();
                    }

                }

            }

        }
        /**
         * set sound on everyhour time:
         */
        if (isEveryHour==true){
            if (isScheduledTime==false) {
                if (sysMin.equals("00")){
                    checkHours(context,musicString);
                }
                else
                {
                    hourFlag = true;
                    saveHourFlag(context,"hourflag",hourFlag);
                }
            }

        }
        /**
         * set sound on Both Condition:
         */
        if(isEveryHour==true) {
            if (isScheduledTime==true) {
                if(sysHour.equals(hour) && sysMin.equals("00") && min.equals("00")) {
                    if (hourFlag){
                        if (Arrays.asList(alarmtone).contains(musicString)){
                            playSound(context,musicString,1);
                            hourFlag=false;
                            saveEveryHour(context,"hourflag",hourFlag);
                        }
                        else {
                            playSound(context,Uri.parse(musicString));
                            hourFlag=false;
                            saveEveryHour(context,"hourflag",hourFlag);
                        }

                    }else {
                        hourFlag=false;
                        saveEveryHour(context,"hourflag",hourFlag);
                        freezMediaPlayer();
                    }

                } else if (sysHour.equals(hour) && sysMin.equals(min)) {
                    if (playSoundFlag){
                        if (Arrays.asList(alarmtone).contains(musicString)){
                            playSound(context,musicString,1);
                            playSoundFlag=false;
                            saveFixedTime("playsound",playSoundFlag,context);

                        }
                        else {
                            playSound(context,Uri.parse(musicString));
                            playSoundFlag=false;
                            saveFixedTime("playsound",playSoundFlag,context);
                        }

                    }
                    else {
                        freezMediaPlayer();
                    }

                }

            }
        }

    }
    private void checkHours(Context context,String music)
    {
        switch (sysHour) {
                case "01":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,1);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "13":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,1);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "02":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,2);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "14":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,2);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "03":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,3);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

            case "15":
                    if (hourFlag){
                        Log.e("hourflagsecond",hourFlag+"");
                        playSound(context,music,3);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

               case "04":
                   if (hourFlag){
                       playSound(context,music,4);

                       //freezMediaPlayer();
                       hourFlag=false;
                       saveHourFlag(context,"hourflag",hourFlag);
                   }
                   break;
                case "16":
                    if (hourFlag){
                        playSound(context,music,4);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "05":
                    if (hourFlag){
                        playSound(context,music,5);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "17":
                if (hourFlag){
                    Log.e("hourflagsecond",hourFlag+"");
                    playSound(context,music,5);

                    //freezMediaPlayer();
                    hourFlag=false;
                    saveHourFlag(context,"hourflag",hourFlag);
                }
                    break;

                case "06":
                    if (hourFlag){
                        playSound(context,music,6);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "18":
                    if (hourFlag){
                        playSound(context,music,6);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "07":

                    if (hourFlag){
                        playSound(context,music,7);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "19":

                    if (hourFlag){
                        playSound(context,music,7);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "08":

                    if (hourFlag){
                        playSound(context,music,8);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

            case "20":

                    if (hourFlag){
                        playSound(context,music,8);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "09":
                    if (hourFlag){
                        playSound(context,music,9);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "21":

                    if (hourFlag){
                        playSound(context,music,9);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

                case "10":
                    if (hourFlag){
                        playSound(context,music,10);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "22":
               if (hourFlag){
                    playSound(context,music,10);

                    //freezMediaPlayer();
                    hourFlag=false;
                    saveHourFlag(context,"hourflag",hourFlag);
                }
                    break;
                case "11":
                    if (hourFlag){
                        playSound(context,music,11);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;
                case "23":
                   if (hourFlag){
                    playSound(context,music,11);

                    //freezMediaPlayer();
                    hourFlag=false;
                    saveHourFlag(context,"hourflag",hourFlag);
                }
                    break;

                case "12":

                    if (hourFlag){
                        playSound(context,music,12);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

            case "00":

                    if (hourFlag){
                        playSound(context,music,12);

                        //freezMediaPlayer();
                        hourFlag=false;
                        saveHourFlag(context,"hourflag",hourFlag);
                    }
                    break;

            }


    }

    private void playSound(final Context context, String fileName, final int count)
    {
        try
        {

                MediaPlayer mediaPlayer = new MediaPlayer();
                final AssetFileDescriptor afd = context.getAssets().openFd(fileName);
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepareAsync();
                mediaPlayer.setLooping(false);


            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayers)
                    {
                        mediaPlayers.start();

                    }

                });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                int n=1;
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                  if (n<count){
                      mediaPlayer.start();
                      n++;
                  }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound(Context context,Uri musicUri)
    {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(context,musicUri);
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

    public void freezMediaPlayer()
    {
        try
        {
            if (mediaPlayer != null)
            {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                }
            }
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();

        }
    }
    private void saveFixedTime(String key, Boolean selectedTime, Context context) {
        SharedPreferences preferences =context.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }
    private void saveHourFlag(Context context,String key, Boolean selectedTime) {
        SharedPreferences preferences =context.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }
    private void saveEveryHour(Context context,String key, Boolean selectedTime) {
        SharedPreferences preferences =context.getSharedPreferences("CLOCK_WALLPAPER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, selectedTime);
        editor.commit();
    }



}
