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

    /**
     * Check if player has won the game
     *
     * @return if Mr. X or Mrs. O is winner return 1 or 2 respectively else return 0
     */
    public int checkWinner() {

        // convert board of players marks into a string then check if it is a
        // winning config or if the xCount is greater than 5.
        if (winConfig.containsKey(convertToString(1)) || xCount > 5) {
            return 1;
        }

        // convert board of players marks into a string then check if it is a
        // winning config or if the oCount is greater than 5.
        if (winConfig.containsKey(convertToString(2)) || oCount > 5) {
            return 2;
        }

        return 0;
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
     * @param player's mark to convert string
     * @return a string of the board eg. "10110010"
     */
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

}
