package com.queens;

import java.util.HashMap;

public class Board {
    private int[]   board;
    private int     xCount;
    private int     oCount;
    private HashMap winConfig;

    public Board() {
        board = new int[9];
        xCount = 0;
        oCount = 0;
        winConfig = new HashMap<String, Integer>();

        populateWinConfig();
    }

    private void populateWinConfig() {
        // horizontal wins
        winConfig.put("111000000", 1);
        winConfig.put("000111000", 1);
        winConfig.put("000000111", 1);

        // vertical wins
        winConfig.put("100100100", 1);
        winConfig.put("010010010", 1);
        winConfig.put("001001001", 1);

        // disgonal wins
        winConfig.put("100010001", 1);
        winConfig.put("001010100", 1);
    }

    private String convertToString(int player) {
        String playerMarks = "";

        for (int i: board) {
            if (board[i] == player) {
                playerMarks += "1";
            } else {
                playerMarks += "0";
            }
        }

        return playerMarks;
    }

}
