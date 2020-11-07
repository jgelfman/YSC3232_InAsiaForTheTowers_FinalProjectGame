package com.example.inasiaforthetowers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.inasiaforthetowers.View.screenRatioX;
import static com.example.inasiaforthetowers.View.screenRatioY;

public class Platform {

    Bitmap platform;
    int width, height, platcoordx, platcoordy, platformsInWindow;
    public Character player;
    int platlocy = 1;
    public int index = 0;
    public static int getIndex;


    //creates the platform image
    public Platform (int screenX, int screenY, Character player, Resources res) {

        //builds the platform as a bitmap
        platform = BitmapFactory.decodeResource(res, R.drawable.platformskygarden);

        //scales based on the picture dimensions and screen ratio
        width = platform.getWidth();
        height = platform.getHeight();

        width /= 15;
        height /= 53;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        this.platcoordy = screenY;
        this.platcoordx = screenX;

        //creates the bitmap using the scaled height/width
        platform = Bitmap.createScaledBitmap(platform, width, height, false);

    }

    public void Update () {
        //Check if player lands on platform
        if (player.charlocy > platlocy && player.charcoordy + player.height >= platcoordy && player.charcoordy + player.height <= platcoordy + height
                && player.charcoordx + player.width / 2 >= platcoordx && player.charcoordx + player.width / 2 <= platcoordx + width) {
            player.charcoordx = platlocy;
            getIndex = index;
            player.charcoordy = platcoordy - player.height + 2;
            player.jump = false;
            player.jumpdone = true;
        } else if (player.charcoordy == platlocy && player.charcoordy + player.height >= platlocy && player.charcoordy + player.height <= platcoordy + height
                && (player.charcoordx + player.width / 2 <= platcoordx || player.charcoordx + player.width / 2 >= platcoordx + width)) {

            // Faster falling
            player.charcoordy = platlocy + 4;

            // Mark as falling
            player.jump = false;
            player.jumpdone = false;
        }
        //the first space the player will press will make the game began
        //if (input.keyIs(KeyEvent.VK_SPACE)) {
        //    //make block move down
        //    dy = stored_dy;
        //}
        //y += dy;
    }

}