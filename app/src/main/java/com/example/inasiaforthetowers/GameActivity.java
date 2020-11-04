package com.example.inasiaforthetowers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.inasiaforthetowers.views.GameView;

public class GameActivity extends Activity {
    private static final String TAG = "GameActivity";

    public Point displaySize;
    public RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.game_activity);

        displaySize=new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        GameView game = new GameView(this);

        Log.i(TAG, "Activity initialized!");
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
