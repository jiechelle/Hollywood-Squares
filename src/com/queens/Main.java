package com.queens;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static DataFile data;
    private static Player[] players = new Player[2];

    public void start(Stage primaryStage) throws Exception {
        LoginGUI loginGUI = new LoginGUI(data,players);
        loginGUI.launchLogin(primaryStage);
    }

    public static void main(String[] args) {
        System.out.println("To get in to game board stage, enter the game with two players, registration and login should be working");
        System.out.println("Single player currently disabled until Alex tells me how to handle computer game with player[1] being null");

        data = new DataFile();
        data.getData();
        launch(args);
    }
}
