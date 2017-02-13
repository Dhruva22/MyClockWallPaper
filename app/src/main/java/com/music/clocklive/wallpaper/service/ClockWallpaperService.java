package com.music.clocklive.wallpaper.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.analogclockclass.AnalogClock;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Clock wallpaper service
 *
 * @author sylsau - sylvain.saurel@gmail.com - http://www.ssaurel.com
 */
public class ClockWallpaperService extends WallpaperService {
    private Bitmap clockDialScaled3,galleryBitmap;

    @Override
    public Engine onCreateEngine() {
        return new ClockWallpaperEngine();
    }

    private class ClockWallpaperEngine extends Engine {

        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                displayHandSec=getSharedPreferences("CLOCK_WALLPAPER",0).getBoolean("showsecondsflag",true);
                draw(ClockWallpaperService.this);
            }

        };
        private int width;
        private int height;
        private boolean visible = true;
        private boolean displayHandSec;
        private AnalogClock clock;
        private int backGroundImage;
        private Uri uri;
        private boolean galleyflag;
        private SharedPreferences prefs;

        public ClockWallpaperEngine() {
            CustomAnalogClock customAnalogClock = new CustomAnalogClock(getApplicationContext());
            customAnalogClock.setAutoUpdate(true);
            clock = new AnalogClock(getApplicationContext());
            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        private void draw(Context context) {
            /**
             * firstly get data from sharedpreference:
             * getting : backgroundimage,gallaryflag,if galleryflag true then user selected its personal image and
             * if galleryflag false then user seleced custom app background to set clockbackground.
             */
            prefs= getSharedPreferences("CLOCK_WALLPAPER", 0);
            backGroundImage = prefs.getInt("clockbackground", R.drawable.back_1);
            galleyflag=prefs.getBoolean("gallaryflag",false);
            if (galleyflag){
                uri= Uri.parse(prefs.getString("filepath",""));
                try {
                    if (galleryBitmap!=null){
                        galleryBitmap.recycle();
                        galleryBitmap=null;
                    }
                    galleryBitmap= BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                    if (clockDialScaled3!=null){
                        clockDialScaled3.recycle();
                        clockDialScaled3=null;
                    }
                    clockDialScaled3= Bitmap.createScaledBitmap(galleryBitmap,width,height,false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (clockDialScaled3!=null){
                    clockDialScaled3.recycle();
                    clockDialScaled3=null;
                }
                clockDialScaled3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                        backGroundImage), width, height, false);
            }
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    draw(canvas,context);
                }
            }
            finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }

            handler.removeCallbacks(drawRunner);

            if (visible) {
                handler.postDelayed(drawRunner, 200);
            }
        }

        private void draw(Canvas canvas, Context context) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(clockDialScaled3,0,0,null);
            clock.config(width / 2, height / 2, (int) (width * 0.6f),
                            new Date(), displayHandSec,context);
            clock.draw(canvas);

        }


    }

}
