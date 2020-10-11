package com.example.inasiaforthetowers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PlaySingle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_single);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Menu.getMusicCounter() == 1) {
            stopService(new Intent(this, Play_music.class));
        }
    }

    protected void onResume() {
        if (Menu.getMusicCounter() == 1) {
            startService(new Intent(this, Play_music.class));
        }
        super.onResume();
    }
}