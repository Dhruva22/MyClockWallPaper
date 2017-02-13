package com.music.clocklive.wallpaper.modelclass;

/**
 * Created by sellnews on 25/1/17.
 */

public class HandStyleImageData {
    public int ivHandStyleImage;
    public int ivHourImage;
    public int ivMinImage;
    public int ivSecImage;

    public HandStyleImageData(int ivHandStyleImage, int ivHourImage, int ivMinImage, int ivSecImage) {
        this.ivHandStyleImage = ivHandStyleImage;
        this.ivHourImage = ivHourImage;
        this.ivMinImage = ivMinImage;
        this.ivSecImage = ivSecImage;
    }

    public int getIvHandStyleImage() {
        return ivHandStyleImage;
    }

    public void setIvHandStyleImage(int ivHandStyleImage) {
        this.ivHandStyleImage = ivHandStyleImage;
    }

    public int getIvHourImage() {
        return ivHourImage;
    }

    public void setIvHourImage(int ivHourImage) {
        this.ivHourImage = ivHourImage;
    }

    public int getIvMinImage() {
        return ivMinImage;
    }

    public void setIvMinImage(int ivMinImage) {
        this.ivMinImage = ivMinImage;
    }

    public int getIvSecImage() {
        return ivSecImage;
    }

    public void setIvSecImage(int ivSecImage) {
        this.ivSecImage = ivSecImage;
    }
}
