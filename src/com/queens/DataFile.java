package com.queens;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFile {
    private File    playersfilePath;
    private Scanner playersData;
    private Scanner questionsData;

    private PrintWriter outFile;

    private HashMap<String, String> questions;
    private HashMap<String, Player> players;

    public DataFile() {
        playersfilePath = new File("players_data.txt");
        players = new HashMap<>();

        try {
            playersData = new Scanner(playersfilePath);
            questionsData = new Scanner(new File("questions.txt"));
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File not found, Exception %s", e.getMessage()));
            System.exit(1);
        } catch (Exception e) {
            System.err.println(String.format("Unexpected error %s", e.getMessage()));
            System.exit(1);
        }

        while (playersData.hasNext()) {
            String username = playersData.next();
            String password = playersData.next();

            int                numberOfHighScores = playersData.nextInt();
            ArrayList<Integer> highScores         = new ArrayList<>();

            for (int i = 0; i < numberOfHighScores; i++) {
                highScores.add(playersData.nextInt());
            }

            players.put(username, (new Player(username, password, highScores)));
        }

        String question = "";
        String answers  = "";

        String line = questionsData.useDelimiter("\\Z").next().replace("\n", " ");

        // "(.*\\?)(\\s*)(.*);"
        Pattern p = Pattern.compile("\\s*(.*?)\\?\\s*(.*?)>");
        Matcher m = p.matcher(line);

        while (m.find()) {
            System.out.println("Q " + m.group(1));
            System.out.println("A " + m.group(2));
            //questions.put(m.group(1), m.group(2));
        }

        //for (String key: questions.keySet()) {
        //    System.out.println(key + " " + questions.get(key));
        //}

        playersData.close();
        questionsData.close();
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
            throw new Exception("Username already in data playersfilePath");
        } else {
            players.put(username, new Player(username, password, new ArrayList<>()));
            return true;
        }
    }

    public void writePlayers() {
        try {
            outFile = new PrintWriter(playersfilePath);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File not found, Exception %s", e.getMessage()));
        } catch (Exception e) {
            System.err.println(String.format("Unexpected error %s", e.getMessage()));
        }

        for (Player player : players.values()) {
            String scores = "";

            // go through players high scores and create one string
            // with spaces between scores
            for (Integer i : player.getHighScores()) {
                scores += Integer.toString(i) + " ";
            }

            // write username password num_of_scores <all scores separated by spaces>
            outFile.write(String.format("%s %s %d %s\n", player.getUsername(),
                    player.getPassword(), player.getHighScores().size(), scores));
        }

        outFile.close();
    }

}
