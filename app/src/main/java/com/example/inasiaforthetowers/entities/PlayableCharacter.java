package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class PlayableCharacter {
    private Bitmap _image1, _image2;
    public int x,y;
    public int width, height;

    public float velocityX = 0; // px/ms
    public float velocityY = 0; // px/ms

    public static final float MAX_SPEED = 5; // px/ms
    public static final float TERMINAL_VELOCITY = 3; // px/ms
    public static final double BREAKING = 0.1; // px/ms^2

    public int wingCounter = 0;
    private int maxFloor;

    public PlayableCharacter(Bitmap image1, Bitmap image2, int sizeX, int sizeY) {

        _image1 = Bitmap.createScaledBitmap(image1, 300, 150, true);
        _image2 = Bitmap.createScaledBitmap(image2, 300, 150, true);
        width = _image1.getWidth();
        height = _image1.getHeight();

        x = sizeX/2 - 150;
        y = sizeY-150-30;
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

    public void move(float time, float gravity) {
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

        velocityY += gravity;

        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
    }

    public void draw(Canvas canvas) {
        if (wingCounter == 0) {
            wingCounter += 50 ;
            canvas.drawBitmap(_image1, x, y, null);
        } else {
            wingCounter -= 50 ;
            canvas.drawBitmap(_image2, x, y, null);
        }

    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int retMaxFloor() {
        return maxFloor;
    }

    public Rect retRect(){
        return new Rect(x + 100, y, x + width - 100, y + height);
    }

}
