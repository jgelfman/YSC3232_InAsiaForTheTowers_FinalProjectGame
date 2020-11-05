package com.example.inasiaforthetowers.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.inasiaforthetowers.GameActivity;
import com.example.inasiaforthetowers.R;
import com.example.inasiaforthetowers.entities.Background;
import com.example.inasiaforthetowers.entities.PlayableCharacter;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";

    public GameActivity activity;
    public GameThread thread;

    private RelativeLayout _layout;

    private Background _background;
    private PlayableCharacter _character;

    private VelocityTracker velocityTracker = null;

    private float xVelocity = 0;
    private float yVelocity = 0;

    public GameView(GameActivity gameActivity) {
        super(gameActivity);

        this.activity = gameActivity;
        _layout=(RelativeLayout)activity.findViewById(R.id.game_activity);

        Log.i(TAG, "View created!");
        _layout.addView(this);
        getHolder().addCallback(this);
        setFocusable(true);

    }

    void newGame() {
        this._background = new Background(
                BitmapFactory.decodeResource(getResources(), R.drawable.backgroundsky),
                this.activity.displaySize.x,
                this.activity.displaySize.y
        );
        this._character = new PlayableCharacter(
                BitmapFactory.decodeResource(getResources(), R.drawable.halcyon_right),
                this.activity.displaySize.x,
                this.activity.displaySize.y
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                }
                else {
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(3);
                xVelocity = velocityTracker.getXVelocity();
                yVelocity = velocityTracker.getYVelocity();
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.thread = new GameThread(getHolder(), this);
        newGame();
        this.thread.setRunning(true);
        this.thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        this.thread.setRunning(false);
        while(retry)
        {
            try{
                this.thread.join();
                retry = false;
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas!=null){
            _background.draw(canvas);
            _character.draw(canvas);
        }
    }

    public void update() {
        int gravity = 15;

        this._background.shiftDown(gravity);
        this._character.changeSpeed(xVelocity, yVelocity);
        this._character.move(1000 / 30);
        if (this._character.x < 0)
            this._character.x = 0;
        else if (this._character.x > this.activity.displaySize.x  -this._character.width)
            this._character.x = this.activity.displaySize.x - this._character.width;

        if (this._character.y > this.activity.displaySize.y - this._character.height)
            this._character.y = this.activity.displaySize.y - this._character.height;
        xVelocity = 0;
        yVelocity = 0;
    }

    class GameThread extends Thread {
        private static final String TAG = "GameThread";

        private SurfaceHolder _holder;
        private GameView _view;
        private boolean _running = false;

        public GameThread(SurfaceHolder holder, GameView view) {
            Log.i(TAG, "Thread created!");
            this._holder = holder;
            this._view = view;
        }

        public void setRunning(boolean run) {
            this._running = run;
        }

        @Override
        public void run() {
            Log.i(TAG, "Thread running!");

            while(this._holder.isCreating()) {}
            int fps = 30;
            int timePerTick = 1000 / fps;

            while (this._running) {

                long timeStarted = System.currentTimeMillis();

                Canvas canvas = null;
                try {
                    canvas = this._holder.lockCanvas();
                    synchronized (this._holder) {
                        this._view.draw(canvas);
                    }
                    this._view.update();
                } finally {
                    if (canvas != null) {
                        this._holder.unlockCanvasAndPost(canvas);
                    }
                }

                long timeElapsed = (System.currentTimeMillis() - timeStarted);

                try{
                    sleep(timePerTick - timeElapsed);
                } catch(Exception e) {}
            }
        }
    }
}
