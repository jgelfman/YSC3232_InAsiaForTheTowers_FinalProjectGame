package com.example.inasiaforthetowers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class Game extends AppCompatActivity {

    private View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new View(this, point.x, point.y);

        setContentView(gameView);
        gameView.run();

    }

}

// fix background movement
// fix actual character render
// impl. character swipe left/right move
// fix platform render loop per window
// imp. character falling down physics
// imp. game when character fall loss