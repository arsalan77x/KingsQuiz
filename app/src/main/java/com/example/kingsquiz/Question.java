package com.example.kingsquiz;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Question {
    private String question;
    @SerializedName(value = "correctAnswer", alternate = {"correct_answer"})
    private String correctAnswer;

    @SerializedName(value = "wrongAnswers", alternate = {"incorrect_answers"})
    private String[] wrongAnswers;
    public boolean answered = false;
    public String userAnswer;
    public boolean isUserAnswerCorrect;
    public String[] options;

    public Question(String question, String correctAnswer, String[] wrongAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void checkUserAnswer() {
        if(Arrays.asList(this.getWrongAnswers()).contains(userAnswer)) {
            this.isUserAnswerCorrect = false;
        } else {
            this.isUserAnswerCorrect = true;
        }
    }
}
