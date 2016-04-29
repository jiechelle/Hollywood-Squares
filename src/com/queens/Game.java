package com.queens;

import java.util.HashMap;
import java.util.Random;

public class Game {

    private DataFile data;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private String question;
    private String[] answers;
    private String celebrityAnswer;

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
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }

        return currentPlayer;
    }

    public void nextPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public void selectQuestionAndAnswers() {
        HashMap<String, String[]> randomQuestion = data.getQuestion();
        for (String key: randomQuestion.keySet()) {
            question = key;
            answers = randomQuestion.get(key);
        }

        int index = new Random().nextInt(answers.length);
        celebrityAnswer = answers[index];
    }

    public String getQuestion() {
        return question;
    }

    public String getCelebrityAnswer() {
        return celebrityAnswer;
    }

    public boolean checkAnswer() {
        return celebrityAnswer.equals(answers[0]);
    }

    public int computerSelectSquare() {
        int index = new Random().nextInt(board.getAvailableSquares().size());
        return board.getAvailableSquares().get(index);
    }

    public void playGame(int index) {
        pickFirstPlayer();

        while (board.checkWinner(currentPlayer)) {
            selectQuestionAndAnswers();

            if (checkAnswer()) { // fix this shit
                board.setSquare(index, currentPlayer);
                currentPlayer.incCurrentScore(1);
                currentPlayer.incMarkerCount();
            } else {
                nextPlayer();
                board.setSquare(index, currentPlayer);
                if (board.checkWinner(currentPlayer)) {
                    board.resetSquare(index);
                } else {
                    currentPlayer.incCurrentScore(1);
                    currentPlayer.incMarkerCount();
                }
            }

        }
    }

    // public void restartGame() {
    //
    // }
    //
    // public void endGame() {
    //
    // }





}
