package com.example.kingsquiz;

import java.util.ArrayList;
import java.util.Date;

public class Quiz {
    public String quizID;
    public int questionsCount;
    public String difficulty;
    public Date date = new Date();

    public Quiz(String quizID, int questionsCount, String difficulty) {
        this.quizID = quizID;
        this.questionsCount = questionsCount;
        this.difficulty = difficulty;
    }
}
