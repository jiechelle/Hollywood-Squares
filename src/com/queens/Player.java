package com.queens;

import java.util.ArrayList;

public class Player {

    private int marker;
    private int currentScore;
    private String username;
    private String password;

    private ArrayList<Integer> highScores;

    public Player(String username, String password, ArrayList<Integer> highScores) {

        currentScore = 0;
        this.username = username;
        this.password = password;
        this.highScores = highScores;
    }

    public int getMarker() {
        return marker;
    }

    public void setMarker(int marker) {
        this.marker = marker;
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

    public void incCurrentScore() {
        this.currentScore++;
    }

    public ArrayList<Integer> getHighScores() {
        return highScores;
    }

    public void addHighScore(int score) {
        highScores.add(score);
    }
}
