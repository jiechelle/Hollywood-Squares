package com.queens;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataFile {
    private File        playersFile;
    private Scanner     inputFile;
    private PrintWriter outFile;

    private HashMap<String, String> questions;
    private HashMap<String, Player> players;

    public DataFile() {
        System.out.println(System.getProperty("user.dir"));
        playersFile = new File("input_file.txt");
        players = new HashMap<>();

        try {
            inputFile = new Scanner(playersFile);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File not found, Exception %s", e.getMessage()));
            System.exit(1);
        } catch (Exception e) {
            System.err.println(String.format("Unexpected error %s", e.getMessage()));
            System.exit(1);
        }

        while (inputFile.hasNext()) {
            String username = inputFile.next();
            String password = inputFile.next();

            int numberOfHighScores = inputFile.nextInt();
            ArrayList<Integer> highScores = new ArrayList<>();

            for (int i = 0; i < numberOfHighScores; i++) {
                highScores.add(inputFile.nextInt());
            }

            players.put(username, (new Player(username, password, highScores)));
        }

        inputFile.close();
    }

    public boolean checkPlayerCredentials(String username, String password) {
        if (players.containsKey(username)) {
            return players.get(username).getPassword().equals(password);
        } else {
            return false;
        }
    }

    public boolean addPlayer(String username, String password) throws Exception {
        // check if username or password has whitespaces in it
        if (username.matches(".*\\s+.*") || password.matches(".*\\s+.*")) {
            throw new ValidationException("Username or password has whitespaces");
        }

        // check if username is in players if not add him
        if (players.containsKey(username)) {
            throw new Exception("Username already in data playersFile");
        } else {
            players.put(username, new Player(username, password, new ArrayList<>()));
            return true;
        }
    }

    public void writePlayers() {
        try {
            outFile = new PrintWriter(playersFile);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File not found, Exception %s", e.getMessage()));
        } catch (Exception e) {
            System.err.println(String.format("Unexpected error %s", e.getMessage()));
        }

        for (Player player: players.values()) {
            String scores = "";

            // go through players high scores and create one string
            // with spaces between scores
            for (Integer i: player.getHighScores()) {
                scores += Integer.toString(i) + " ";
            }

            // write username password num_of_scores <all scores separated by spaces>
            outFile.write(String.format("%s %s %d %s\n", player.getUsername(),
                    player.getPassword(), player.getHighScores().size(), scores));
        }

        outFile.close();
    }

}
