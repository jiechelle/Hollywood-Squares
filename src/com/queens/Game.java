package com.queens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {

    private DataFile data;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currenPlayer;
    private String question;
    private String[] answers;

    public Game(DataFile data, Player[] players) {
        this.data = data;
        board = new Board();
        player1 = players[0];
        player2 = players[1];
    }

    public Player pickFirstPlayer() {
        Random randomNum = new Random();
        int result = randomNum.nextInt(2);

        if (result == 0) {
            currenPlayer = player1;
        } else {
            currenPlayer = player2;
        }

        return currenPlayer;
    }

    public void nextPlayer() {
        if (currenPlayer == player1) {
            currenPlayer = player2;
        } else {
            currenPlayer = player1;
        }
    }

    public void getQuestion() {
        HashMap<String, String[]> randomQuestion = data.getQuestion();
        for (String key: randomQuestion.keySet()) {
            question = key;
            answers = randomQuestion.get(key);
        }
    }

    public void playGame(String player_answer, int index) {

        while (board.checkWinner() == 0) {
            if (player_answer.equals(answers[0])) {
                board.setSquare(index, currenPlayer);
                currenPlayer.incCurrentScore();
            } else {
                nextPlayer();
                board.setSquare(index, currenPlayer);
                if (board.checkWinner() == 1 || board.checkWinner() == 2) {
                    board.resetSquare(index);
                }
            }

        }
    }

}
