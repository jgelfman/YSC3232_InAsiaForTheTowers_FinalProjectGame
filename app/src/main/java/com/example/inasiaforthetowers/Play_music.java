package com.example.inasiaforthetowers;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Play_music extends Service {

    private MediaPlayer playing;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playing = MediaPlayer.create(this, R.raw.playing_music);
        playing.setLooping(true);
        playing.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        playing.stop();
    }
}