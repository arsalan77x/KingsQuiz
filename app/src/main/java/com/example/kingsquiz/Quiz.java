package com.example.kingsquiz;

import java.util.ArrayList;

public class Quiz {
    public ArrayList<Question> questions = new ArrayList<>();

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
