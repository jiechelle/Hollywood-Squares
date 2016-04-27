package com.queens;

public class Game {

    private Board   board;
    private Player  player1;
    private Player  player2;

    public Game(Player[] players) {
        board = new Board();
        player1 = players[0];
        player2 = players[1];
    }

    public int CheckWinner() {
        return board.isWinner();
    }

    public void pickFirstPlayer() {

    }
}
