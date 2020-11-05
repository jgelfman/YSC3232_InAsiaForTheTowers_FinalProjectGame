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

    public float velocityX = 0; // px/s
    public float velocityY = 0; // px/s

    public static final float MAX_SPEED = 5; // px/s
    public static final float TERMINAL_VELOCITY = 3; // px/s

    public static final double GRAVITY = 0.05; // px/s^2
    public static final double BREAKING = 0.1; // px/s^2

    public PlayableCharacter(Bitmap image, int sizeX, int sizeY){

        _image = Bitmap.createScaledBitmap(image, 400, 200, true);
        width = _image.getWidth();
        height = _image.getHeight();

        x = sizeX/2 - 200;
        y = sizeY-200-20;;
        _sizeX = sizeX;
        _sizeY = sizeY;
    }

    public void changeSpeed(float dx, float dy) {
        if (dx > MAX_SPEED) {
            velocityX = MAX_SPEED;
        } else if (dx < -MAX_SPEED) {
            velocityX = -MAX_SPEED;
        } else {
            velocityX = dx;
        }

        if (dy < 0) {
            if (dy > MAX_SPEED) {
                velocityY = MAX_SPEED;
            } else {
                velocityY = dy / 4;
            }
        }
    }

    public void move(float time) {
        x += velocityX * time;
        y += velocityY * time;
        if (velocityX < 0) {
            velocityX += BREAKING * time;
            if (velocityX > 0) {
                velocityX = 0;
            }
        } else {
            velocityX -= BREAKING * time;
            if (velocityX < 0) {
                velocityX = 0;
            }
        }

        velocityY += GRAVITY * time;
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_image, x, y, null);
    }
}
