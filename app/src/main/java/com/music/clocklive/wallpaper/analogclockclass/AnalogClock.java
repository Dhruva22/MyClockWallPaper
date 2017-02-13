package com.music.clocklive.wallpaper.analogclockclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;


import com.music.clocklive.wallpaper.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Analog Clock view
 *
 * @author sylsau - sylvain.saurel@gmail.com - http://www.ssaurel.com
 */
public class AnalogClock extends View {
    private int sizeScaled = -1;
    /**
     * center X.
     */
    private float x;
    /**
     * center Y.
     */
    private float y;
    private int clockSecstyleImage;
    private Calendar cal;
    private SharedPreferences preferences;
    private Bitmap clockDial;
    private Bitmap clockNumberDial;
    private int radius, clockDialImage, clockdigitalDialImage, clockHourStyleImage, clockMinStyleImage;
    private Bitmap clockDialScaled, clockNumberScaled, hourBitmap, minBitmap, secBitmap;
    private boolean displayHandSec;
    BitmapFactory.Options options = new BitmapFactory.Options();


    public AnalogClock(Context context) {
        super(context);
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));


    }

    public void config(float x, float y, int size, Date date,
                       boolean displayHandSec, Context context) {
        this.x = x;
        this.y = y;
        this.displayHandSec = displayHandSec;

        cal.setTime(date);
        if (clockDial != null) {
            clockDial.recycle();
            clockDial = null;
        }
        if (clockNumberDial != null) {
            clockNumberDial.recycle();
            clockNumberDial = null;
        }
        if (clockNumberScaled != null) {
            clockNumberScaled.recycle();
            clockNumberScaled = null;
        }
        if (clockDialScaled != null) {
            clockDialScaled.recycle();
            clockDialScaled = null;
        }


        /**
         * getting data from sharedpreference:
         */

        preferences = context.getSharedPreferences("CLOCK_WALLPAPER", 0);
        clockDialImage = preferences.getInt("clockdial", R.drawable.clock_dial_1);
        clockdigitalDialImage = preferences.getInt("digitalstyle", R.drawable.digital_style_1);
        clockHourStyleImage = preferences.getInt("hourstyleimage", R.drawable.hour_1);
        clockMinStyleImage = preferences.getInt("minstyleimage", R.drawable.min_1);
        clockSecstyleImage = preferences.getInt("secStyleimage", R.drawable.sec_1);

        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        clockDial = BitmapFactory.decodeResource(getResources(),
                clockDialImage, options);
        clockNumberDial = BitmapFactory.decodeResource(getResources(), clockdigitalDialImage, options);

        // scale bitmap if needed
        if (size != sizeScaled) {
            clockNumberScaled = Bitmap.createScaledBitmap(clockNumberDial, size, size, false);
            clockDialScaled = Bitmap.createScaledBitmap(clockDial, size, size, false);
            radius = size / 2;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw clock img
        if (clockDialImage != R.drawable.no_dial) {
            canvas.drawBitmap(clockDialScaled, x - radius, y - radius, null);
        }
        if (clockdigitalDialImage != R.drawable.no_digits) {
            canvas.drawBitmap(clockNumberScaled, x - radius, y - radius, null);
        }
        float sec = cal.get(Calendar.SECOND);
        float min = cal.get(Calendar.MINUTE);
        float hour = cal.get(Calendar.HOUR_OF_DAY);

        /**
         * draw min hand on canvas:
         */
        if (minBitmap != null) {
            minBitmap.recycle();
            minBitmap = null;
        }

        minBitmap = BitmapFactory.decodeResource(getResources(), clockMinStyleImage, options);
        Matrix matrixMinute = new Matrix();
        matrixMinute.postTranslate(-minBitmap.getWidth() / 2,0);
        matrixMinute.postRotate((min / 60.0f * 360.0f));
        matrixMinute.postTranslate(x,y);
        canvas.drawBitmap(minBitmap, matrixMinute, null);
        canvas.save();



        /**
         * draw hour hand on canvas:
         */
        if (hourBitmap != null) {
            hourBitmap.recycle();
            hourBitmap = null;
        }
        hourBitmap = BitmapFactory.decodeResource(getResources(), clockHourStyleImage, options);
        Matrix matrixHour = new Matrix();
        matrixHour.postTranslate(-hourBitmap.getWidth() / 2,0);
        matrixHour.postRotate((hour / 12.0f * 360.0f));
        matrixHour.postTranslate(x,y);
        canvas.drawBitmap(hourBitmap, matrixHour, null);
        canvas.save();
        /**
         * draw second hand on canvas:
         */
        if (secBitmap != null) {
            secBitmap.recycle();
            secBitmap = null;
        }
        if (displayHandSec) {
            secBitmap = BitmapFactory.decodeResource(getResources(), clockSecstyleImage, options);
            Matrix matrixSec = new Matrix();
            matrixSec.postTranslate(-secBitmap.getWidth() / 2,0);
            matrixSec.postRotate((sec / 60.0f * 360.0f) + 180);
            matrixSec.postTranslate(x,y);
            canvas.drawBitmap(secBitmap, matrixSec, null);
            canvas.save();
        }


    }


}