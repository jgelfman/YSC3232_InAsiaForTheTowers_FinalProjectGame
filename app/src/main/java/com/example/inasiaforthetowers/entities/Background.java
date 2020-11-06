package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private Bitmap _image;
    private int _position = 0;
    private int _x;
    private int _y;

    public Background(Bitmap image, int x, int y){
        _image = Bitmap.createScaledBitmap(image, x, y, true);
        _x = x;
        _y = y;
    }

    public void shiftDown(int amount) {
        _position += amount;

//      repeat the bg
//      could yank the bg sometimes??? <- not sure
        if (_position > _y) {
            _position = 0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_image, 0, _position, null);
        if(_position > 0){
//          repeat the bg
            canvas.drawBitmap(_image, 0, _position - _y, null);
        }
    }
}
