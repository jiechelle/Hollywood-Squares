package com.queens;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DataFile data = new DataFile();
        // Login login = new Login(data);
        // Player[] players = login.getPlayers();
        Player[] players = new Player[2];
        players[0] = new Player("ciao", "goodbye", new ArrayList<>());
        GameGUI a = new GameGUI();
        a.playGame(data, players);
        a.show(args);

        // try {
        //    data.addPlayer("john", "doe");
        // } catch (ValidationException e) {
        //    System.err.println(String.format("ValidationException, %s", e.getMessage()));
        // } catch (Exception e) {
        //    System.err.println(String.format("Exception, %s", e.getMessage()));
        // }
    }
}
