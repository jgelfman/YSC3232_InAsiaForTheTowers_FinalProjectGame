package inasiaforthetowers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inasiaforthetowers.Game;
import com.example.inasiaforthetowers.R;
import com.example.inasiaforthetowers.View;

public class PlaySingle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_single);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.startGame1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(PlaySingle.this, Game.class));
            }

        });

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