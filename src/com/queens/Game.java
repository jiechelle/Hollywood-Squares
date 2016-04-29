package com.queens;

import java.util.Random;

public class Game {

    private DataFile data;
    private Board board;
    private Player player1;
    private Player player2;
    private boolean playerTurn;

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
            playerTurn = true;
            return player1;
        } else {
            playerTurn = false;
            return player2;
        }
    }

    public String setSquare() {
        if (playerTurn) {
            player1.incCurrentScore();
            return "x";
        } else {
            player2.incCurrentScore();
            return "o";
        }
    }

    public void nextPlayer() {
        playerTurn = !playerTurn;
    }

}
