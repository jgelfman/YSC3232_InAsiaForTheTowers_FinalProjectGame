package com.example.inasiaforthetowers.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class PlayableCharacter extends Entities {
    private Bitmap _image1, _image2;
    public int x,y;
    private int _sizeX, _sizeY;
    public int width, height;
    public int wingCounter = 0;
    private boolean canWallJump;
    private int wallJumpFrame;
    private int floorJumpFrame;
    private int borderWidth;
    private int totalY;
    private int maxFloor;

    public PlayableCharacter(Bitmap image1, Bitmap image2, int sizeX, int sizeY) {

        _image1 = Bitmap.createScaledBitmap(image1, 400, 200, true);
        _image2 = Bitmap.createScaledBitmap(image2, 400, 200, true);

        width = _image1.getWidth();
        height = _image1.getHeight();

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
