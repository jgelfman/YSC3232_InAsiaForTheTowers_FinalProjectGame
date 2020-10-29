package com.example.inasiaforthetowers;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    private View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_single);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new View(this, 1920, 1080);

        setContentView(gameView);
        //gameView.run();

    }

}

// fix background movement
// fix actual character render
// impl. character swipe left/right move
// fix platform render loop per window
// imp. character falling down physics
// imp. game when character fall loss