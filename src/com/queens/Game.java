package com.queens;

import java.util.Random;

public class Game {

    private DataFile data;
    private Board board;
    private Player player1;
    private Player player2;

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
            return player1;
        } else {
            return player2;
        }
    }
}
