package com.example.inasiaforthetowers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScore extends AppCompatActivity implements View.OnClickListener, Score.scores {
    public Score highScore = new Score();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        TextView showScore = findViewById(R.id.showScore);
        showScore.setText("High Score: " + highScore.getScore());

        Button returnMain = findViewById(R.id.returnMain);
        returnMain.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.returnMain) {
            finish();
        }
    }

    @Override
    public String getScore() {
        return highScore.getScore();
    }

    @Override
    public void setScore(int newScore) {
        highScore.setScore(newScore);
    }

    @Override
    public void plus_score(int score) {
        highScore.plus_score(score);
    }
}