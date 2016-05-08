package com.queens;

import java.util.ArrayList;
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

        if (players[1] == null) {
            player2 = new Player("the computer", "", new ArrayList<>());
        } else {
            player2 = players[1];
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void pickFirstPlayer() {
        Random randomNum = new Random();
        int headsOrTails = randomNum.nextInt(2);

        // computer always goes first
        if (player2.getUsername().equals("the computer")) {
            headsOrTails = 0;
        }

        if (headsOrTails == 0) {
            currentPlayer = player1;
            player1.setMarker(1);

            player2.setMarker(2);
        } else {
            currentPlayer = player2;
            player2.setMarker(1);

            player1.setMarker(2);
        }
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
        for (String key : randomQuestion.keySet()) {
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

    public boolean checkCelebAnswer() {
        return celebrityAnswer.equals(answers[0]);
    }

    public int setSquare(boolean playerAnswer, int index) {

        // if the current player has the correct answer, set the square
        if (playerAnswer && checkCelebAnswer() || !playerAnswer && !checkCelebAnswer()) {
            board.setSquare(index, currentPlayer);
            currentPlayer.incCurrentScore(1);
            currentPlayer.incMarkerCount();
            return currentPlayer.getMarker();

            // else set the square for the otherPlayer but if the otherPlayer
            // wins then reset the square
        } else {
            Player otherPlayer = player1;
            if (currentPlayer == otherPlayer) {
                otherPlayer = player2;
            }

            board.setSquare(index, otherPlayer);
            if (board.checkCurrentPlayerIsWinner(otherPlayer)) {
                board.resetSquare(index);
            } else {
                otherPlayer.incCurrentScore(1);
                otherPlayer.incMarkerCount();
                return otherPlayer.getMarker();
            }
        }

        // Nobody gets the marker on the square square
        return 0;
    }

    public boolean checkCurrentPlayerIsWinner() {
        if (board.checkCurrentPlayerIsWinner(currentPlayer)) {
            currentPlayer.addHighScore();
            data.writePlayers();
            return true;
        }

        return false;
    }

    public int computerSelectSquare() {
        int index = new Random().nextInt(board.getAvailableSquares().size());
        System.out.println(board.getAvailableSquares().get(index));

        return board.getAvailableSquares().get(index);
    }

    public boolean computerResponse() {
        Random randomNum = new Random();
        int result = randomNum.nextInt(2);

        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    // todo finish method
    public void restartGame() {
        board.resetBoard();
        player1.resetMarkerCount();
        player1.resetCurrentScore();
        player2.resetMarkerCount();
        player2.resetCurrentScore();
    }

    // todo finish method
    public void endGame() {
        board.resetBoard();

    }
}
