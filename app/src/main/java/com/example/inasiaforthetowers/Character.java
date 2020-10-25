package com.example.inasiaforthetowers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.inasiaforthetowers.View.screenRatioX;
import static com.example.inasiaforthetowers.View.screenRatioY;


public class Character {

    public boolean jump = false;
    int x, y, width, height, wingcounter = 0;
    Bitmap char1, char2;

    Character (int screenX, Resources res) {

        char1 = BitmapFactory.decodeResource(res, R.drawable.halcyon_mascot1);
        char2 = BitmapFactory.decodeResource(res, R.drawable.halcyon_mascot2);

        width = char1.getWidth();
        height = char2.getHeight();

        width /= 5;
        height /= 5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        char1 = Bitmap.createScaledBitmap(char1, width, height, false);
        char2 = Bitmap.createScaledBitmap(char2, width, height, false);

        x = screenX;
        y = (int) (64 * screenRatioY);

    }

        Bitmap getJump () {

            if (wingcounter == 0) {
                wingcounter ++ ;
                return char1;
            } else {
                wingcounter -- ;
                return char2;
            }

        }

}

