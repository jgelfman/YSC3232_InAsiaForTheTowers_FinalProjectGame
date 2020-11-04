package com.example.inasiaforthetowers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.inasiaforthetowers.View.screenRatioX;
import static com.example.inasiaforthetowers.View.screenRatioY;


public class Character {

    //wingcounter used to move the wings by alterating between jump/still pictures
    int width, height, wingCounter = 0;
    int charposy = -1;
    int chary = 5;
    int jumpheight = 50;

    //builds the character as two bitmaps
    Bitmap char1, char2;

    int charlocx = 3;
    int charlocy = 0;
    int charcoordx, charcoordy;

    public boolean dead = false;
    public boolean jump = false;
    public boolean jumpdone = true;

    Character (int screenY, Resources res) {

        //borrows the two character positions picture elements
        char1 = BitmapFactory.decodeResource(res, R.drawable.halcyon_mascot1);
        char2 = BitmapFactory.decodeResource(res, R.drawable.halcyon_mascot2);

        //sets the width by the photo
        width = char1.getWidth();
        height = char2.getHeight();

        //scales based on the screen ratio
        width /= 5;
        height /= 5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        this.charcoordx = (500 - 5) / 2 - width / 2;
        this.charcoordy = 500 - 25 - height;

        //creates the bitmaps using the scaled height/width
        char1 = Bitmap.createScaledBitmap(char1, width, height, false);
        char2 = Bitmap.createScaledBitmap(char2, width, height, false);

    }

        //bitmap that animates the character alternating between the two
        Bitmap getJump () {

            if (wingCounter == 0) {
                wingCounter ++ ;
                return char1;
            } else {
                wingCounter -- ;
                return char2;
            }
        }

    public void update() {
        // Check for sideways movement
        if (charlocx < 60) {
            charlocx = 60;
        }
        if (charlocx > 500 - width - 5 - 60) {
            charlocx = 500 - width - 5 - 60;
        }

        // Check for horizontal movement
        if (charlocy > 500 - height - 25) {
            dead = true;
            charlocy = -charlocy;
        }
        // Check character jump
        if (jump) {
            //make him go up
            charlocy = -chary;
            getJump();
        }
        // Check for jump height or screen ceiling reach
        if (charlocy <= charposy - jumpheight) {
            //Reset
            charposy = -1;
            // Fall
            charlocy = chary;
            // Make jump false
            jump = false;
            jumpdone = false;
        } else if (charlocy < 0) {
            charposy = -1;
            charlocy = chary;
            jump = false;
            jumpdone = false;
        }



    }

}

