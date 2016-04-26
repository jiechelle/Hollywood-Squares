package com.queens;

import java.util.ArrayList;

public class Player {

    private String username;
    private String password;
    private int    currentScore;

    private ArrayList<Integer> highScores;

    public Player(String username, String password, ArrayList<Integer> highScores) {

        this.username = username;
        this.password = password;

        currentScore = 0;
        this.highScores = highScores;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {

        return password;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public ArrayList<Integer> getHighScores() {
        return highScores;
    }

    public void addHighScore(int score) {
        highScores.add(score);
    }
}
