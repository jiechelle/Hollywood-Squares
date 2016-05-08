package com.queens;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int[] board;
    private ArrayList<Integer> availableSquares; // list available squares
    private int secretSquare;

    public Board() {
        board = new int[9];
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

    public void setSecretSquare() {
        secretSquare = new Random().nextInt(9);
    }

    public int getSecretSquare() {
        return secretSquare;
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
        int[] bo = getPlayerMarkers(incomingPlayer);

                // [0 1 2 3 4 5 6 7 8]

                //  0 1 2
                //  3 4 5
                //  6 7 8

                // horizontal wins
        return  (bo[0] == bo[1] && bo[0] == bo[2] && bo[0] == 1) ||
                (bo[3] == bo[4] && bo[3] == bo[5] && bo[3] == 1) ||
                (bo[6] == bo[7] && bo[6] == bo[8] && bo[6] == 1) ||

                // vertical wins
                (bo[0] == bo[3] && bo[0] == bo[5] && bo[0] == 1) ||
                (bo[1] == bo[4] && bo[1] == bo[7] && bo[1] == 1) ||
                (bo[2] == bo[5] && bo[2] == bo[8] && bo[2] == 1) ||

                // diagonal wins
                (bo[0] == bo[4] && bo[0] == bo[8] && bo[0] == 1) ||
                (bo[2] == bo[4] && bo[2] == bo[6] && bo[2] == 1) ||

                (incomingPlayer.getMarkerCount() >= 5);

    }

    /**
     * Convert board into a string of 1's and 0's
     *
     * @param incomingPlayer mark to convert string
     * @return a string of the board eg. "10110010"
     */
    private int[] getPlayerMarkers(Player incomingPlayer) {
        int[] playerMarks = new int[9];

        for (int i = 0; i < board.length; i++) {
            if (board[i] == incomingPlayer.getMarker()) {
                playerMarks[i] = 1;
            }
        }

        System.out.print("Curren Board ");
        for (int i: board) {
            System.out.print(i);
        }
        System.out.print("\nPlayer marks ");
        for (int i: playerMarks) {
            System.out.print(i);
        }
        System.out.println();

        return playerMarks;
    }

}
