package com.queens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    private int[] board;
    private ArrayList<Integer> availableSquares; // list available squares
    private int secretSquare;

    private HashMap<String, Integer> winConfig;

    public Board() {
        board = new int[9];
        winConfig = new HashMap<>();
        secretSquare = new Random().nextInt(9);

        populateWinConfig();
    }

    public ArrayList<Integer> getAvailableSquares() {
        return availableSquares;
    }

    public int[] getBoard() {
        return board;
    }

    public void setSquare(int index, Player currentPlayer) {
        this.board[index] = currentPlayer.getMarker();

        for (Integer i = 0; i < board.length; i++) {
            if (board[i] == 0 && !availableSquares.contains(i)) {
                availableSquares.add(i);
            } else if (availableSquares.contains(i)) {
                availableSquares.remove(i);
            }
        }
    }

    public void resetSquare(int index) {
        this.board[index] = 0;
    }

    /**
     * Check if player has won the game
     *
     * @return if Mr. X or Mrs. O is winner return 1 or 2 respectively else return 0
     */
    public boolean checkWinner(Player currentPlayer) {
        // convert board of players marks into a string then check if it is a
        // winning config or if the xCount is greater than 5.
        return winConfig.containsKey(convertToString(currentPlayer))
                || currentPlayer.getMarkerCount() >= 5;

    }

    /**
     * Add all winning configurations to winConfig
     */
    private void populateWinConfig() {
        // horizontal wins
        winConfig.put("111000000", null);
        winConfig.put("000111000", null);
        winConfig.put("000000111", null);

        // vertical wins
        winConfig.put("100100100", null);
        winConfig.put("010010010", null);
        winConfig.put("001001001", null);

        // diagonal wins
        winConfig.put("100010001", null);
        winConfig.put("001010100", null);
    }

    /**
     * Convert board into a string of 1's and 0's
     *
     * @param currentPlayer mark to convert string
     * @return a string of the board eg. "10110010"
     */
    private String convertToString(Player currentPlayer) {
        String playerMarks = "";

        for (int i : board) {
            if (i == currentPlayer.getMarker()) {
                playerMarks += "1";
            } else {
                playerMarks += "0";
            }
        }

        return playerMarks;
    }

}
