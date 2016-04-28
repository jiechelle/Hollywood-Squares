package com.queens;

import javax.xml.bind.ValidationException;

public class Main {

    public static void main(String[] args) {
        DataFile data = new DataFile();
        GameGUI a = new GameGUI();
        a.show(args);

        //try {
        //    data.addPlayer("john", "doe");
        //} catch (ValidationException e) {
        //    System.err.println(String.format("ValidationException, %s", e.getMessage()));
        //} catch (Exception e) {
        //    System.err.println(String.format("Exception, %s", e.getMessage()));
        //}
        //
        //data.writePlayers();

    //    Login login = new Login(data);
    //    Player[] players = login.getPlayers();
    //    Game game = new Game(data, players);
    //    GameGUI gameGUI = new GameGUI(game)
    }
}
