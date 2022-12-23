package com.example.kingsquiz;

import java.util.ArrayList;

public class User {


    private String password;
    private String email;
    private int score;
    private String username;
    public static User loggedInUser = null;



    public User(String email,String password, int score, String username){
        this.email = email;
        this.password = password;
        this.score = score;
        this.username= username;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
