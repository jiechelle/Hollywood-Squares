package com.queens;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;

public class GameGUI extends Application {

    private Player[] players = new Player[2];

    private static Game game;
    private Button[] guiBoard;
    private Button agree;
    private Button disagree;
    private Button endTurn;
    private Text question;
    private Text celebrityResponse;
    private Text isCorrect;
    private Text currentPlayer;
    private boolean playerAnswer;
    private int selectedSquare;

    Stage theStage;
    static DataFile data;
    String[] args;

    public void start(Stage primaryStage) {

        theStage = primaryStage;
        game.pickFirstPlayer();
        guiBoard = new Button[9];
        agree = new Button("True");
        disagree = new Button("False");
        endTurn = new Button("End Turn");

        agree.setVisible(false);
        disagree.setVisible(false);
        endTurn.setVisible(false);

        question = new Text("Please select a square");
        celebrityResponse = new Text();
        celebrityResponse.setVisible(false);

        isCorrect = new Text();
        isCorrect.setVisible(false);

        if (game.getCurrentPlayer().getMarker() == 1)
            currentPlayer = new Text("Player 1 ");
        else
            currentPlayer = new Text("Player 2 ");

        for (Integer i = 0; i < guiBoard.length; i++) {
            guiBoard[i] = new Button();
            guiBoard[i].setPrefSize(100, 100);
            guiBoard[i].setId(Integer.toString(i));

            guiBoard[i].setOnAction(e -> {

                selectedSquare = Integer.parseInt(((Control) e.getSource()).getId());
                game.selectQuestionAndAnswers();
                question.setText(game.getQuestion());
                celebrityResponse.setText("Celebrity response: " + game.getCelebrityAnswer());
                celebrityResponse.setVisible(true);

                agree.setVisible(true);
                disagree.setVisible(true);
                for (int j = 0; j < guiBoard.length; j++) {
                    guiBoard[j].setDisable(true);
                }
            });
        }

        agree.setPrefSize(100, 20);
        agree.setOnAction(e -> {
            endTurn.setVisible(true);
            playerAnswer = true;
            playerFeedback(game.setSquare(playerAnswer, selectedSquare));
        });

        disagree.setPrefSize(100, 20);
        disagree.setOnAction(e -> {
            endTurn.setVisible(true);
            playerAnswer = false;
            playerFeedback(game.setSquare(playerAnswer, selectedSquare));
        });

        endTurn.setPrefSize(100, 20);
        endTurn.setOnAction(e -> {
            question.setText("Please select a square");
            celebrityResponse.setVisible(false);
            isCorrect.setVisible(false);
            agree.setVisible(true);
            disagree.setVisible(true);
            endTurn.setVisible(false);
            game.nextPlayer();

            if (game.getCurrentPlayer().getMarker() == 1)
                currentPlayer.setText("Player 1 ");
            else
                currentPlayer.setText("Player 2 ");

            for (int j = 0; j < guiBoard.length; j++) {
                if (guiBoard[j].getText().equals("")) {
                    guiBoard[j].setDisable(false);
                }
            }

            ((Control) e.getSource()).setVisible(false);
        });

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

        VBox gameBox = new VBox(20);
        gameBox.setAlignment(Pos.CENTER);

        HBox questionBox = new HBox(20);
        questionBox.setAlignment(Pos.CENTER);

        HBox responseBox = new HBox(20);
        questionBox.setAlignment(Pos.CENTER);

        HBox firstRowSquares = new HBox(20);
        firstRowSquares.setAlignment(Pos.CENTER);

        HBox secondRowSquares = new HBox(20);
        secondRowSquares.setAlignment(Pos.CENTER);

        HBox thirdRowSquares = new HBox(20);
        thirdRowSquares.setAlignment(Pos.CENTER);

        HBox trueOrFalseBox = new HBox(20);
        trueOrFalseBox.setAlignment(Pos.TOP_CENTER);

        firstRowSquares.getChildren().addAll(guiBoard[0], guiBoard[1], guiBoard[2]);
        secondRowSquares.getChildren().addAll(guiBoard[3], guiBoard[4], guiBoard[5]);
        thirdRowSquares.getChildren().addAll(guiBoard[6], guiBoard[7], guiBoard[8]);
        questionBox.getChildren().addAll(question);
        responseBox.getChildren().addAll(celebrityResponse, isCorrect);
        trueOrFalseBox.getChildren().addAll(currentPlayer, agree, disagree, endTurn);

        gameBox.getChildren().addAll(questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        Scene gameScene = new Scene(gameBox, 600, 500);
        Scene loginScene = new Scene(loginPane, 300, 270);

        primaryStage.setTitle("Hollywood Squares");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        //LOGIN button action
        logBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();

            if (data.checkPlayerCredentials(tempUser, tempPass)) {

                //assign players to array depending on case 1 or case 2
                if (players[0] == null)
                    players[0] = new Player("hey", "hello", new ArrayList<>());
                else if (players[1] == null && players[0] != null)
                    players[1] = new Player("test", "ting", new ArrayList<>());

                //TWO PLAYERS LOGGED IN
                if (players[1] != null && players[0] != null) {
                    loginAlert.setTitle("Game Start");
                    loginAlert.setHeaderText("Two player game ");
                    loginAlert.setContentText("Press OK to start!");
                    loginAlert.showAndWait();

                    theStage.setScene(gameScene);
                }

                //PLayer 1 logged in, BEGIN SELECTION SEQUENCE
                if (players[0] != null && players[1] == null) {
                    Optional<ButtonType> result = selectAlert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        loginAlert.setTitle("Game Start");
                        loginAlert.setHeaderText("One player game ");
                        loginAlert.setContentText("Press OK to start!");
                        loginAlert.showAndWait();
                        theStage.setScene(gameScene);
                        //START GAME WITH PLAYER 1 VS CPU
                    } else if (result.get() == buttonTypeTwo) {
                        userField.clear();
                        pwField.clear();
                        scenetitle.setText("Player 2 - Please Login");
                        //GO BACK TO LOGIN SCREEN AND REGISTER PLAYER 2
                    } else {
                        //USER PRESSED X OR CANCEL, reset player 0
                        players[0] = null;
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
    }

    public void playGame(DataFile data, String[] args) {


        this.data = data;
        this.args = args;

        //fix get players after login completed
        //***starting new game or caling game funcs before logging players in will result in big issues, need to fix***
        //need a way to delay game functions before login occurs and records player0 and player1

        this.getPlayers();
        this.game = new Game(data, players);

        launch(args);

    }

    public void getPlayers() {
        players[0] = new Player("ciao", "goodbye", new ArrayList<>());
        players[1] = new Player("abc", "efg", new ArrayList<>());
    }

    public void playerFeedback(int answer) {
        if (answer == game.getCurrentPlayer().getMarker())
            isCorrect.setText("Correct");
        else
            isCorrect.setText("Wrong");

        isCorrect.setVisible(true);
        agree.setVisible(false);
        disagree.setVisible(false);
        endTurn.setVisible(true);

        // Disable all buttons on guiBoard when agree or disagree is pressed
        for (int j = 0; j < guiBoard.length; j++) {
            guiBoard[j].setDisable(true);

            if (j == selectedSquare && answer != 0) {
                if (answer == 1)
                    guiBoard[j].setText("X");
                else
                    guiBoard[j].setText("O");
            }
        }
    }
}