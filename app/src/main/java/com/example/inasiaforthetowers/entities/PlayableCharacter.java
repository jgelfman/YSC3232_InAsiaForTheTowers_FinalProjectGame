package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class PlayableCharacter extends Entities {
    private Bitmap _image1, _image2;
    public int x,y;
    private int _sizeX, _sizeY;
    public int width, height;
    private int totalY;

    public float velocityX = 0; // px/s
    public float velocityY = 0; // px/s

    public static final float MAX_SPEED = 5; // px/s
    public static final float TERMINAL_VELOCITY = 3; // px/s

    public static final double GRAVITY = 0.05; // px/s^2
    public static final double BREAKING = 0.1; // px/s^2

    public int wingCounter = 0;
    private boolean canWallJump;
    private int wallJumpFrame;
    private int floorJumpFrame;
    private int maxFloor;

    public PlayableCharacter(Bitmap image1, Bitmap image2, int sizeX, int sizeY) {

        _image1 = Bitmap.createScaledBitmap(image1, 400, 200, true);
        _image2 = Bitmap.createScaledBitmap(image2, 400, 200, true);

        width = _image1.getWidth();
        height = _image1.getHeight();

        x = sizeX/2 - 200;
        y = sizeY-200-20;;

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

    public void draw(Canvas canvas) {
        if (wingCounter == 0) {
            wingCounter += 50 ;
            canvas.drawBitmap(_image1, x, y, null);
        } else {
            wingCounter -= 50 ;
            canvas.drawBitmap(_image2, x, y, null);
        }

    }

    public void setWallJumpIndex(int wallJumpFrame){
        this.wallJumpFrame = wallJumpFrame;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public void setFloorJumpIndex(int floorJumpFrame){
        this.floorJumpFrame=floorJumpFrame;
    }

    public boolean retCanWallJump(){
        return canWallJump;
    }

    public int retWallJumpIndex(){
        return wallJumpFrame;
    }

    public int retFloorJumpIndex(){
        return floorJumpFrame;
    }

    public int retTotalY() {
        return totalY;
    }

    public int retMaxFloor() {
        return maxFloor;
    }


}
