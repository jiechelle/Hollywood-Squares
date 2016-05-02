package com.queens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class LoginGUI {

    private Scene loginScene;
    private Stage myStage;
    private Player[] players = new Player[2];
    static DataFile data;
    private GameGUI gameGUI;

    public LoginGUI(DataFile data, Player[] players) {
        this.data = data;
        this.players = players;
    }

    public void launchLogin(Stage stage) {
        myStage = stage;

        ///LOGIN PANEL FRAME CREATION
        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));

        //LOGIN TITLE
        Text scenetitle = new Text("Please Login or Register");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginPane.add(scenetitle, 0, 0, 2, 1);

        //POP UP MESSAGES
        Alert loginAlert = new Alert(Alert.AlertType.WARNING);
        Alert selectAlert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeOne = new ButtonType("One");
        ButtonType buttonTypeTwo = new ButtonType("Two");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        selectAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        selectAlert.setTitle("Hollywood Squares");
        selectAlert.setHeaderText("");
        selectAlert.setContentText("Select number of players: ");

        //USER NAME BOX
        Label userName = new Label("Username:");
        loginPane.add(userName, 0, 1);
        TextField userField = new TextField();
        loginPane.add(userField, 1, 1);

        //PASSWORD BOX
        Label pw = new Label("Password:");
        loginPane.add(pw, 0, 2);
        PasswordField pwField = new PasswordField();
        loginPane.add(pwField, 1, 2);

        //LOGIN BUTTON
        Button logBtn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(logBtn);
        loginPane.add(hbBtn, 1, 4);

        //REGISTER BUTTON
        Button rBtn = new Button("Register");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(rBtn);
        loginPane.add(hbBtn2, 1, 5);


        //LOGIN button action
        logBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();

            if (data.checkPlayerCredentials(tempUser, tempPass)) {

                //assign players to array depending on case 1 or case 2
                if (players[0] == null)
                    players[0] = data.getPlayer(tempUser);
                    //players[0] = new Player("hey", "hello", new ArrayList<>());
                else if (players[1] == null && players[0] != null)
                    players[1] = data.getPlayer(tempUser);

                //TWO PLAYERS LOGGED IN
                if (players[1] != null && players[0] != null) {
                    loginAlert.setTitle("Game Start");
                    loginAlert.setHeaderText("Two player game ");
                    loginAlert.setContentText("Press OK to start!");
                    loginAlert.showAndWait();
                    gameGUI = new GameGUI(data, players);
                    gameGUI.launchGame(myStage);
                }

                //PLayer 1 logged in successfully, BEGIN SELECTION SEQUENCE
                if (players[0] != null && players[1] == null) {
                    Optional<ButtonType> result = selectAlert.showAndWait();
                    //START GAME WITH PLAYER 1 VS CPU
                    if (result.get() == buttonTypeOne) {
                        loginAlert.setTitle("Game Start");
                        loginAlert.setHeaderText("One player game ");
                        loginAlert.setContentText("Press OK to start!");
                        loginAlert.showAndWait();
                        System.out.println("ONE PLAYER GAME WILL CRASH UNTIL AI LOGIC IMPLEMENTED (because only player[0] is filled and player[1] is empty");
                        //gameGUI = new GameGUI(data, players);
                        //gameGUI.launchGame(myStage);

                        //GO BACK TO LOGIN SCREEN AND REGISTER PLAYER 2
                    } else if (result.get() == buttonTypeTwo) {
                        userField.clear();
                        pwField.clear();
                        scenetitle.setText("Player 2 - Please Login");

                        //USER PRESSED X OR CANCEL, reset player 0
                    } else {
                        players[0] = null;
                        pwField.clear();
                    }
                }
            } else {
                loginAlert.setTitle("Login Error");
                loginAlert.setHeaderText("Username or password incorrect");
                loginAlert.setContentText("Are you registered?");
                loginAlert.showAndWait();
                pwField.clear();
            }
        });

        //REGISTER button action
        rBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();

            //USER NAME TAKEN
            if (data.checkPlayerName(tempUser)) {
                loginAlert.setTitle("Registration Error");
                loginAlert.setHeaderText("Username already taken");
                loginAlert.setContentText("Please try again");
                loginAlert.showAndWait();
                pwField.clear();
                userField.clear();
                //BLANK FIELDS
            } else if (tempUser.isEmpty() || tempPass.isEmpty()) {
                loginAlert.setTitle("Registration Error");
                loginAlert.setHeaderText("Text fields cannot be empty!");
                loginAlert.setContentText("Make sure to fill in a username and password");
                loginAlert.showAndWait();
                //SUCCESSFUL REGISTRATION
            } else if (!data.checkPlayerCredentials(tempUser, tempPass) && !tempUser.isEmpty() && !tempPass.isEmpty()) {
                try {
                    data.addPlayer(tempUser, tempPass);
                    System.out.println("Account " + tempUser + " successfully created");
                    data.writePlayers();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        loginScene = new Scene(loginPane, 300, 270);
        myStage.setTitle("Hollywood Squares");
        myStage.setScene(loginScene);
        myStage.setResizable(true);
        myStage.show();
    }
}