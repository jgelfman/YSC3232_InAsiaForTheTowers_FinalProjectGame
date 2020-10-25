package com.example.inasiaforthetowers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.inasiaforthetowers.View.screenRatioX;
import static com.example.inasiaforthetowers.View.screenRatioY;

public class Platform {

    Bitmap platform;
    int width, height, x, y, platformsInWindow;

    Platform (int screenX, int screenY, Resources res) {

        platform = BitmapFactory.decodeResource(res, R.drawable.platformskygarden);

        width = platform.getWidth();
        height = platform.getHeight();

        width /= 5;
        height /= 53;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        platform = Bitmap.createScaledBitmap(platform, width, height, false);

        x = screenX / 2;
        y = (int) (64 * screenRatioY);

    }

    Bitmap generatePlatform () {

        platformsInWindow = 3;

        if (platformsInWindow <= 3) {
            return platform;
        } else {
            return platform;
        }

    }



}