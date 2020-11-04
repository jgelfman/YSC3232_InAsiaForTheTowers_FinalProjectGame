package com.example.inasiaforthetowers.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.inasiaforthetowers.GameActivity;
import com.example.inasiaforthetowers.R;
import com.example.inasiaforthetowers.entities.Background;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";

    public GameActivity activity;
    public GameThread thread;

    private RelativeLayout _layout;

    private Bitmap[] images;

    private Background _background;

    public GameView(GameActivity gameActivity) {
        super(gameActivity);

        this.activity = gameActivity;
        _layout=(RelativeLayout)activity.findViewById(R.id.game_activity);

        Log.i(TAG, "View created!");
        _layout.addView(this);
        getHolder().addCallback(this);
        setFocusable(true);

//        images = new Bitmap[4];
//        images[0] =


//        this.gameLoop = new GameLoopThread(getHolder(), this);

    }

    private void init() {

    }

    void newGame() {
        this.thread = new GameThread(getHolder(), this);

        this._background = new Background(
                BitmapFactory.decodeResource(getResources(), R.drawable.backgroundsky),
                this.activity.displaySize.x,
                this.activity.displaySize.y);

        this.thread.setRunning(true);
        this.thread.start();
//        while (true) {
//            Canvas canvas = getHolder().lockCanvas();
//            if (canvas == null) {
//                Log.e(TAG, "Cannot draw onto the canvas as it's null");
//            } else {
//                draw(canvas);
//                getHolder().unlockCanvasAndPost(canvas);
//            }
//            try{
//                Thread.sleep(1000);
//            } catch(Exception e) {}
//        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        newGame();
//        invalidate();
//        this.gameLoop.setRunning(true);
//        this.gameLoop.run();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

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
//            canvas.drawRGB(120, 120, 120);
            _background.draw(canvas);
        }
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
                    this._view._background.shiftDown(100);
                } finally {
                    if (canvas != null) {
                        this._holder.unlockCanvasAndPost(canvas);
                    }
                }

                long timeElapsed = (System.currentTimeMillis() - timeStarted);

//                this.setRunning(false);

                try{
                    sleep(timePerTick - timeElapsed);
                } catch(Exception e) {}
            }
        }
    }
}
