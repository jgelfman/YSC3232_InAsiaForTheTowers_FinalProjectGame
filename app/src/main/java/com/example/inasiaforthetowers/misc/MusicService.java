package com.example.inasiaforthetowers.misc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.inasiaforthetowers.R;

public class MusicService extends Service {

    private MediaPlayer _background;
    private int _musicResId;
    private boolean _setVolume;

    public MusicService(int musicResId, boolean setVolume) {
        _musicResId = musicResId;
        _setVolume = setVolume;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _background = MediaPlayer.create(this, _musicResId);
        if (_setVolume) {
            _background.setVolume(150, 150);
        }
        _background.setLooping(true);
        _background.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        _background.stop();
    }
}