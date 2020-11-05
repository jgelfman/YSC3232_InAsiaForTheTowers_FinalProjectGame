package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class PlayableCharacter {
    private Bitmap _image;
    public int x;
    public int y;
    private int _sizeX;
    private int _sizeY;
    public int width;
    public int height;

    public PlayableCharacter(Bitmap image, int sizeX, int sizeY){

        _image = Bitmap.createScaledBitmap(image, 400, 200, true);
        width = _image.getWidth();
        height = _image.getHeight();

        x = sizeX/2 - 200;
        y = sizeY-200-20;;
        _sizeX = sizeX;
        _sizeY = sizeY;

    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_image, x, y, null);
    }
}
