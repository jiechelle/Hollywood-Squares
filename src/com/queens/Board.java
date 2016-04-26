package com.queens;

import java.util.HashMap;

public class Board {
    private int[] board;
    private int   xCount;
    private int   oCount;

    private HashMap<String, Integer> winConfig;

    public Board() {
        board = new int[9];
        xCount = 0;
        oCount = 0;
        winConfig = new HashMap<>();

        populateWinConfig();
    }

    public int getxCount() {
        return xCount;
    }

    public int getoCount() {
        return oCount;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int index, int value) {
        this.board[index] = value;

        if (value == 1) {
            xCount++;
        } else {
            oCount++;
        }
    }

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

    private String convertToString(int player) {
        String playerMarks = "";

        for (int i : board) {
            if (i == player) {
                playerMarks += "1";
            } else {
                playerMarks += "0";
            }
        }

        return playerMarks;
    }

    public boolean isWinner() {
        return winConfig.containsKey(convertToString(1)) ||
                winConfig.containsKey(convertToString(2)) ||
                xCount > 5 || oCount > 5;
    }

}
