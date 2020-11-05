package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Border extends Entities {

    Bitmap _border;

    public Border(Bitmap image, int x, int y){
        this._border = image;
        this.x = x;
        this.y = y;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void update(int dy){
        y = y + dy;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_border, x, y, null);
    }
}
