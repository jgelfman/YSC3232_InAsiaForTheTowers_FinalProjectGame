package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Border {

    Bitmap _border;
    public int x;
    public int y;

    public int width;

    public Border(Bitmap image, int sizeX, int sizeY){
        this._border = image;
        this.x = sizeX;
        this.y = sizeY;
        width = 100;
    }

    public void update(int dy){
        y = y + dy;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_border, x, y, null);
    }
}
