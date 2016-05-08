package com.queens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Board {
    private int[] board;
    private ArrayList<Integer> availableSquares; // list available squares
    private int secretSquare;

    public Board() {
        board = new int[9];
        secretSquare = new Random().nextInt(9);
        availableSquares = new ArrayList<>();

        checkAvailableSquares();
    }

    public ArrayList<Integer> getAvailableSquares() {
        return availableSquares;
    }

    public void checkAvailableSquares() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                availableSquares.add(i);
            } else if (availableSquares.contains(i)) {
                availableSquares.remove(i);
            }
        }
    }

    public int[] getBoard() {
        return board;
    }

    public void resetBoard() {

        for (int i = 0; i < board.length; i++) {
            board[i] = 0;
        }
    }

    public void setSquare(int index, Player currentPlayer) {
        checkAvailableSquares();

        if (board[index] != 0) {
            System.out.println("Error, spot on board is already filled");
        }

        this.board[index] = currentPlayer.getMarker();
    }

    public void resetSquare(int index) {
        this.board[index] = 0;
    }

    /**
     * Check if current player has won the game
     *
     * @return true or false depending on if currentPlayer has one
     */
    public boolean checkPlayerIsWinner(Player incomingPlayer) {
        // convert board of players marks into a string then check if it is a
        // winning config or if the xCount is greater than 5.
        String bo = convertPlayerMarkers(incomingPlayer);

                // horizontal wins
        return  (bo.charAt(0) == bo.charAt(1) && bo.charAt(0) == bo.charAt(2) && bo.charAt(0) == 1) ||
                (bo.charAt(3) == bo.charAt(4) && bo.charAt(3) == bo.charAt(5) && bo.charAt(3) == 1) ||
                (bo.charAt(6) == bo.charAt(7) && bo.charAt(6) == bo.charAt(8) && bo.charAt(6) == 1) ||

                // vertical wins
                (bo.charAt(0) == bo.charAt(3) && bo.charAt(0) == bo.charAt(5) && bo.charAt(0) == 1) ||
                (bo.charAt(1) == bo.charAt(4) && bo.charAt(1) == bo.charAt(7) && bo.charAt(1) == 1) ||
                (bo.charAt(2) == bo.charAt(5) && bo.charAt(2) == bo.charAt(8) && bo.charAt(2) == 1) ||

                // diagonal wins
                (bo.charAt(0) == bo.charAt(4) && bo.charAt(0) == bo.charAt(8) && bo.charAt(0) == 1) ||
                (bo.charAt(2) == bo.charAt(2) && bo.charAt(2) == bo.charAt(6) && bo.charAt(2) == 1) ||

                (incomingPlayer.getMarkerCount() >= 5);

    }

    /**
     * Convert board into a string of 1's and 0's
     *
     * @param incomingPlayer mark to convert string
     * @return a string of the board eg. "10110010"
     */
    private String convertPlayerMarkers(Player incomingPlayer) {
        String playerMarks = "";

        for (int i : board) {
            if (i == incomingPlayer.getMarker()) {
                playerMarks += "1";
            } else {
                playerMarks += "0";
            }
        }

        System.out.print("Curren Board ");
        for (int i: board) {
            System.out.print(i);
        }
        System.out.println("\nPlayer marks " + playerMarks + "\n");
        return playerMarks;
    }

}
