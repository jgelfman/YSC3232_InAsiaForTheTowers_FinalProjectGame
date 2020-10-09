package com.example.inasiaforthetowers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(this);


        Button checkScore = findViewById(R.id.checkScore);
        checkScore.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startGame) {
            Intent intent = new Intent(this, PlayGame.class);
            startActivity(intent);
        } else if (view.getId() == R.id.checkScore) {
            Intent intent = new Intent(this, HighScore.class);
            startActivity(intent);
        }
    }

}