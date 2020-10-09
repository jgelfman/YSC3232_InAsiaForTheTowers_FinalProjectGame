package com.example.inasiaforthetowers;

public class Score {
    private int score;

    public String getScore() {
        return String.valueOf(score);
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public void plus_score(int score) {
        this.score += score;
    }

    public interface scores {
        String getScore();

        void setScore(int newScore);

        void plus_score(int score);
    }


}
