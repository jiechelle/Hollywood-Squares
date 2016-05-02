package com.queens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    private int[] board;
    private ArrayList<Integer> availableSquares; // list available squares
    private int secretSquare;

    private HashMap<String, Boolean> winConfig;

    public Board() {
        board = new int[9];
        winConfig = new HashMap<>();
        secretSquare = new Random().nextInt(9);
        availableSquares = new ArrayList<>();

        populateWinConfig();
    }

    public ArrayList<Integer> getAvailableSquares() {
        return availableSquares;
    }

    public void checkAvailableSquares() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == -1) {
                availableSquares.add(i);
            }
        }
    }

    public int[] getBoard() {
        return board;
    }

    public void setSquare(int index, Player currentPlayer) {
        checkAvailableSquares();
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
     * Check if current player has won the game
     *
     * @return true or false depending on if currentPlayer has one
     */
    public boolean checkCurrentPlayerIsWinner(Player currentPlayer) {
        // convert board of players marks into a string then check if it is a
        // winning config or if the xCount is greater than 5.
        return winConfig.containsKey(convertPlayerMarkers(currentPlayer))
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
    private String convertPlayerMarkers(Player currentPlayer) {
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
