package com.queens;

import java.util.HashMap;

public class Game {
    private Player  player1;
    private Player  player2;
    private Board   board;
    private boolean winner;
    private HashMap questions;

    public Game(Player[] players) {
        winner = false;

    }//constructor

    public boolean CheckWinner() {
        return board.isWinner();
    }//Check Winner

    public void pickFirstPlayer() {

    }
}//Game
