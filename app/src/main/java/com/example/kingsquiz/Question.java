package com.example.kingsquiz;

import com.google.gson.annotations.SerializedName;

public class Question {
    private String question;
    @SerializedName(value = "correctAnswer", alternate = {"correct_answer"})
    private String correctAnswer;

    @SerializedName(value = "wrongAnswers", alternate = {"incorrect_answers"})
    private String[] wrongAnswers;

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
}
