package com.example.inasiaforthetowers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    public int y, x;

    Bitmap background;

    //builds the background as a bitmap using the dimensions of the screen
    Background(int screenX, int screenY, Resources res) {

        y = screenY;
        x = screenX;

        //borrows the background picture element
        background = BitmapFactory.decodeResource(res, R.drawable.backgroundskygarden);

        //scales the background to fit the screen size
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }

}
