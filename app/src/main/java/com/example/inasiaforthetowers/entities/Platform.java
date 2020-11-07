package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Platform {

    private Bitmap _platformImage;
    private int platformIndex;
    public int platY, platX;
    private int platformWidth, platformHeight;

    public Platform(Bitmap image, int platformY, int platformX, int index, int borderWidth){
        this.platformIndex=index;
        this.platY=platformY;
        this._platformImage = Bitmap.createScaledBitmap(image, 400, 30, true);

        platformHeight = this._platformImage.getHeight();
        platformWidth = this._platformImage.getWidth();

//        if(platformIndex == 0 || platformIndex % 50 == 0){
//            platX = borderWidth;
//        }
//        else{
            Random random = new Random();
            platX = random.nextInt(platformX - 2 * borderWidth - platformWidth)
                    + borderWidth;
//        }
    }

    public void update(int newY){
        platY = platY + newY;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_platformImage, platX, platY, null);

    }

    private int scale(Bitmap image, int borderWidth){

        if(platformIndex == 0 || platformIndex % 50 == 0) {
            return 300 - 2 * borderWidth;
        }
        else{
            return platformWidth / (3 + platformIndex / 100);
        }

    }

    public Rect retRect(){
        return new Rect(platX, platY, platX + platformWidth, platY);
    }

    public int retIndex(){
        return platformIndex;
    }
}
