package com.queens;

import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import javax.xml.bind.ValidationException;

public class Main {

    public static void main(String[] args) {
        DataFile data = new DataFile();

        try {
            data.addPlayer("john", "doe");
        } catch (ValidationException e) {
            System.err.println(String.format("ValidationException, %s", e.getMessage()));
        } catch (Exception e) {
            System.err.println(String.format("Exception, %s", e.getMessage()));
        }

        data.writeFile();
    //    Login login = new Login(data);
    //    Game game = new Game(data, login.getUsers());
    //    GameGUI gameGUI = new GameGUI(game)
    }
}
