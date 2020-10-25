package inasiaforthetowers;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.inasiaforthetowers.R;

public class Menu_music extends Service {

    private MediaPlayer background;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        background = MediaPlayer.create(this, R.raw.menu_music);
        background.setVolume(150, 150);
        background.setLooping(true);
        background.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        background.stop();
    }
}