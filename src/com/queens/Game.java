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
        player1.setMarker(1);
        player2.setMarker(2);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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

    public int nextPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            if (currentPlayer == null && setSquare(computerRasponse(), computerSelectSquare()) != 0) {
                return computerSelectSquare();
            }
        } else {
            currentPlayer = player1;
        }

        return -1;
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

    public boolean checkCelebAnswer() {
        return celebrityAnswer.equals(answers[0]);
    }

    public int setSquare(boolean playerAnswer, int index) {
        if (playerAnswer && checkCelebAnswer()) {
            board.setSquare(index, currentPlayer);
            currentPlayer.incCurrentScore(1);
            currentPlayer.incMarkerCount();
            return currentPlayer.getMarker();
        } else {
            if (currentPlayer == player1) {
                System.out.println("here");
                board.setSquare(index, player2);
                if (board.checkCurrentPlayerIsWinner(player2)) {
                    board.resetSquare(index);
                } else {
                    player2.incCurrentScore(1);
                    player2.incMarkerCount();
                    return player2.getMarker();
                }
            } else {
                board.setSquare(index, player1);
                player1.incCurrentScore(1);
                player1.incMarkerCount();
                if (board.checkCurrentPlayerIsWinner(player1)) {
                    board.resetSquare(index);
                } else {
                    player1.incCurrentScore(1);
                    player1.incMarkerCount();
                    return player1.getMarker();
                }
            }
        }

        // if (checkCelebAnswer()) { // fix this shit
        //     board.setSquare(index, currentPlayer);
        //     currentPlayer.incCurrentScore(1);
        //     currentPlayer.incMarkerCount();
        //     return true;
        // } else {
        //     nextPlayer();
        //     board.setSquare(index, currentPlayer);
        //     if (board.checkCurrentPlayerIsWinner(currentPlayer)) {
        //         board.resetSquare(index);
        //         return false;
        //     } else {
        //         currentPlayer.incCurrentScore(1);
        //         currentPlayer.incMarkerCount();
        //         return true;
        //     }
        // }

        return 0;
    }

    public boolean checkCurrentPlayerIsWinner() {
        if (board.checkCurrentPlayerIsWinner(currentPlayer)) {
            player1.addHighScore();
            player2.addHighScore();
            data.writePlayers();
            return true;
        }

        return false;
    }

    public int computerSelectSquare() {
        int index = new Random().nextInt(board.getAvailableSquares().size());
        return board.getAvailableSquares().get(index);
    }

    public boolean computerRasponse() {
        Random randomNum = new Random();
        int result = randomNum.nextInt(2);

        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    // public void restartGame() {
    //
    // }

    // public void endGame() {
    //
    // }
}
