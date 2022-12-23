package com.example.kingsquiz;

import android.util.Log;

public class Settings {
    public int questionAmount;
    public int category;
    public String difficulty;

    public Settings(int questionAmount, int category, String difficulty) {
        this.questionAmount = questionAmount;
        this.category = category;
        this.difficulty = difficulty;
    }

    public static int getDifficultyValue(String difficulty) {
        Log.i("bbbbbbbbbbbbbbb", difficulty);
        switch (difficulty) {
            case "Medium":
                return 2;
            case "Hard":
                return 3;
            default:
                return 1;
        }
    }
}
