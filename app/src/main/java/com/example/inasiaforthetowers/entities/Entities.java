package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class Entities {

    int x,y,dy,width,height;
    Bitmap image;

    public int retY() {
        return y;
    }
    public int retX() {
        return x;
    }
    public int retDY() {
        return dy;
    }
    public int retWidth() {
        return width;
    }
    public int retHeight() {
        return height;
    }
    public Rect retRect() {
        return new Rect(x, y, x+width, y+height);
    }
}