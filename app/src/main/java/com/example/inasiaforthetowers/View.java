package com.example.inasiaforthetowers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

public class View extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying = false;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Character character;
    public Background background1, background2;
    int platformGap = 15;
    int platformAmount = 150;
    int maxOffset, minOffset;
    int[] platformX = new int[platformAmount];
    int[] platformY = new int[platformAmount];
    private Platform platform;
    private Random random;
    Bitmap[] characters;
    int charFrame = 0;
    int speed = 0, gravity = 3;
    boolean gameState = false;

    public View(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background1.y = screenY;

        character = new Character(screenY, getResources());

        minOffset = 5;
        maxOffset = screenX - 5;

        random = new Random();

        int platformheight = screenY + platformGap;

        for (int i = 0; i < platformAmount; i++) {

            platformX[i] = screenX + i * platformGap;
            platformY[i] = minOffset + random.nextInt(maxOffset - minOffset + 1);
            platform = new Platform(platformX[i], platformY[i], getResources());

        }

        paint = new Paint();
    }

    @Override
    public void run() {

        isPlaying = true;

        while (isPlaying) {

            update();
            draw();

        }
    }

    private void update() {

        background1.y -= 10 * screenRatioY;
        background2.y -= 10 * screenRatioY;


        if (background1.y + background1.background.getHeight() < 0) {
            background1.y = screenY;
        }

        if (background2.y + background2.background.getHeight() < 0) {
            background2.y = screenY;
        }

        if (character.jump) {
            character.y -= 30 * screenRatioY;
        } else {
            character.y += 30 * screenRatioY;
        }

        if (character.y < 0) {
            character.y = 0;
        }

        if (character.y > screenRatioY - screenY - character.height) {
            character.y = screenY - character.height;
        }

    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(character.getJump(), character.x, character.y, paint);

            if (charFrame == 0) {
                charFrame = 1;
            } else {
                charFrame = 0;
            }

            speed = -30;
            gameState = true;

            if (gameState) {
                if (character.y < screenY - characters[0].getHeight() || speed < 0) {
                    speed += gravity;
                    character.y += speed;
                }
            }

            for (int i = 0; i < platformAmount; i++) {
                canvas.drawBitmap(platform.generatePlatform(), platformX[i], platformY[i], paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                character.jump = true;
                break;
            case MotionEvent.ACTION_DOWN:
                character.jump = false;
                break;
        }
        return true;

    }
}
