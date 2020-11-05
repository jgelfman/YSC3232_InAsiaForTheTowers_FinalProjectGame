package com.example.inasiaforthetowers.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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
import com.example.inasiaforthetowers.entities.Border;
import com.example.inasiaforthetowers.entities.Platform;
import com.example.inasiaforthetowers.entities.PlayableCharacter;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";

    public GameActivity activity;
    public GameThread thread;

    private RelativeLayout _layout;

    private Background _background;
    private PlayableCharacter _character;
    private Platform _platform;

    private VelocityTracker velocityTracker = null;

    private int xVelocity = 0;
    private int yVelocity = 0;

    private ArrayList<Platform> _platforms;
    private ArrayList<Border> _leftBorder;
    private ArrayList<Border> _rightBorder;

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
                BitmapFactory.decodeResource(getResources(), R.drawable.backgroundskygarden),
                this.activity.displaySize.x,
                this.activity.displaySize.y
        );
        this._character = new PlayableCharacter(
                BitmapFactory.decodeResource(getResources(), R.drawable.halcyon_mascot1),
                BitmapFactory.decodeResource(getResources(), R.drawable.halcyon_mascot2),
                this.activity.displaySize.x,
                this.activity.displaySize.y
        );
        _leftBorder = new ArrayList<>();
        _rightBorder = new ArrayList<>();
        _platforms = new ArrayList<>();
        for(int i = 0; i < this.activity.displaySize.y / 100 + 1; i++) {

            _platforms.add(
                    new Platform(BitmapFactory.decodeResource(getResources(), R.drawable.platformskygarden),
                    this.activity.displaySize.y - this.activity.displaySize.y / 4 - i * 100,
                            this.activity.displaySize.x,
                            i,
                            30));
        }
        for(int i = this.activity.displaySize.y / BitmapFactory.decodeResource(getResources(), R.drawable.borderplants).getHeight(); i > -2; i--) {
            _leftBorder.add(new Border(BitmapFactory.decodeResource(getResources(), R.drawable.borderplants), 0, i * BitmapFactory.decodeResource(getResources(), R.drawable.borderplants).getHeight()));
            _rightBorder.add(new Border(BitmapFactory.decodeResource(getResources(), R.drawable.borderplants), this.activity.displaySize.x - BitmapFactory.decodeResource(getResources(), R.drawable.borderplants).getWidth(), i * BitmapFactory.decodeResource(getResources(), R.drawable.borderplants).getHeight()));
        }
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
                velocityTracker.computeCurrentVelocity(25);
                xVelocity = (int) velocityTracker.getXVelocity();
                yVelocity = (int) velocityTracker.getYVelocity();
//                velocityTracker.recycle();
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
//            canvas.drawRGB(120, 120, 120);
            _background.draw(canvas);
            _character.draw(canvas);

            for(Border box: _leftBorder) {
                box.draw(canvas);
            }
            for(Border box: _rightBorder) {
                box.draw(canvas);
            }
            for(Platform _platform: _platforms) {
                _platform.draw(canvas);
            }
        }
    }

    private boolean levelColission(PlayableCharacter character, Platform platform){
        return Rect.intersects(character.retRect(), platform.retRect())
                && character.retY() + character.retHeight() < platform.retY() + 20
                && character.retWallJumpIndex() == 0 && character.retFloorJumpIndex() == 0;
    }

    private boolean borderCollistion(PlayableCharacter character, Border border){
        return Rect.intersects(character.retRect(), border.retRect())
                && character.retWallJumpIndex() == 0 && character.retCanWallJump();
    }

    public void update() {
        this._background.shiftDown(10);
        this._character.move(xVelocity, yVelocity);
        xVelocity = 0;
        yVelocity = 0;

        for (int i = 0; i < _platforms.size(); i++) {
            _platforms.get(i).update(_character.retDY());
            if (levelColission(_character, _platforms.get(i))) {
                _character.setFloorJumpIndex(1);
                if (_character.retMaxFloor() < _platforms.get(i).retIndex()) {
                    _character.setMaxFloor(_platforms.get(i).retIndex());
                }
            }
            if (_platforms.get(i).retY() > this.activity.displaySize.y + 20) {
                _platforms.remove(i);
                if(_platforms.get(_platforms.size() - 1).retIndex() + 1<=1000){
                    _platforms.add(
                            new Platform (BitmapFactory.decodeResource(getResources(),
                                    R.drawable.borderplants),
                                    _platforms.get(_platforms.size() - 1).retY() - 53 - _platforms.get(_platforms.size() - 1).retHeight(),
                                    _platforms.get(_platforms.size() - 1).retX() - _platforms.get(_platforms.size() - 1).retHeight(),
                                    _platforms.get(_platforms.size() - 1).retIndex() + 1, BitmapFactory.decodeResource(getResources(), R.drawable.borderplants).getWidth()));
                }
            }
        }

        for (int i = 0; i < _leftBorder.size(); i++) {
            _leftBorder.get(i).update(_character.retDY());
            _rightBorder.get(i).update(_character.retDY());
            if (borderCollistion(_character, _leftBorder.get(i))
                    || borderCollistion(_character, _rightBorder.get(i)))
                _character.setWallJumpIndex(1);

            if (_leftBorder.get(i).retY() > this.activity.displaySize.y) {
                _leftBorder.remove(i);
                _rightBorder.remove(i);
                _leftBorder.add(new Border(BitmapFactory.decodeResource(getResources(), R.drawable.borderplants), 0,
                        _leftBorder.get(_leftBorder.size() - 1).retY() - _leftBorder.get(_leftBorder.size() - 1).retHeight()));
                _rightBorder.add(new Border(BitmapFactory.decodeResource(getResources(), R.drawable.borderplants),
                        this.activity.displaySize.x - _rightBorder.get(_rightBorder.size() - 1).retWidth(),
                        _rightBorder.get(_rightBorder.size() - 1).retY() - _rightBorder.get(_rightBorder.size() - 1).retHeight()));
            }
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
                    this._view.update();
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
