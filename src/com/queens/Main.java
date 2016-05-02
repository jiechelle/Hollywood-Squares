package com.queens;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DataFile data = new DataFile();
        data.getData();

       // data.checkPlayerCredentials

        // Login login = new Login(data);
        // Player[] players = login.getPlayers();
        //Player[] players = new Player[2];
        //players[0] = new Player("ciao", "goodbye", new ArrayList<>());
        //players[1] = new Player("ciao", "goodbye", new ArrayList<>());

        GameGUI gameGUI = new GameGUI();
        gameGUI.getPlayers();
        gameGUI.playGame(data, args);

        // try {
        //    data.addPlayer("john", "doe");
        // } catch (ValidationException e) {
        //    System.err.println(String.format("ValidationException, %s", e.getMessage()));
        // } catch (Exception e) {
        //    System.err.println(String.format("Exception, %s", e.getMessage()));
        // }
    }
}
