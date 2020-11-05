package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import com.example.inasiaforthetowers.GameActivity;
import com.example.inasiaforthetowers.MenuActivity;
import com.example.inasiaforthetowers.views.GameView;

public class Platform extends Entities {

    private Bitmap _platformImage;
    private int platformIndex;
    private int platY, platX;
    private int platformWidth, platformHeight;

    public Platform(Bitmap image, int platformY, int indx, int borderWidth){
        this.platformIndex=indx;
        this.platY=platformY;
        this._platformImage = Bitmap.createScaledBitmap(image, scale(image, borderWidth), image.getHeight() / 2, true);

        platX = _platformImage.getWidth();
        platformHeight = _platformImage.getHeight();

        if(platformIndex == 0 || platformIndex % 50 == 0){
            platX = borderWidth;
        }
        else{
            Random random = new Random();
            platX = random.nextInt(platX - 2 * borderWidth - platX)
                    + borderWidth;
        }
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
            return _platformImage.getWidth() / (3 + platformIndex / 100);
        }

    }

    @Override
    public Rect retRect(){
        return new Rect(platX, platY, platX + platformWidth, platY);
    }

    public int retIndex(){
        return platformIndex;
    }
}
