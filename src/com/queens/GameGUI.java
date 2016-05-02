package com.queens;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

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
    private Text currerntPlayer;
    private boolean playerAnswer;
    private int selectedSquare;

    Stage theStage;
    static DataFile data;

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
            currerntPlayer = new Text("Player 1 ");
        else
            currerntPlayer = new Text("Player 2 ");

        for (Integer i = 0; i < guiBoard.length; i++) {
            guiBoard[i] = new Button();
            guiBoard[i].setPrefSize(100, 100);
            guiBoard[i].setId(Integer.toString(i));

            guiBoard[i].setOnAction(e -> {

                selectedSquare = Integer.parseInt(( (Control) e.getSource()).getId());
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
                currerntPlayer.setText("Player 1 ");
            else
                currerntPlayer.setText("Player 2 ");

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

        //USER NAME BOX
        Label userName = new Label("User Name:");
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
        trueOrFalseBox.getChildren().addAll(currerntPlayer, agree, disagree, endTurn);

        gameBox.getChildren().addAll(questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        Scene gameScene = new Scene(gameBox, 600, 500);
        Scene loginScene = new Scene(loginPane, 300, 300);

        primaryStage.setTitle("Hollywood Squares");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        //LOGIN button action
        logBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();
            if (data.checkPlayerCredentials(tempUser, tempPass)) {
                System.out.println("Logged in!");
            } else System.out.println("Either username or password is incorrect. Are you registered?");

            pwField.clear();
        });

        //REGISTER button action
        rBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();

            if (data.checkPlayerName(tempUser)) {
                System.out.println("USER NAME ALREADY TAKEN");
                pwField.clear();
                userField.clear();
            } else if (tempUser.isEmpty() || tempPass.isEmpty()) System.out.println("Text fields cannot be empty!");

            else if (!data.checkPlayerCredentials(tempUser, tempPass) && !tempUser.isEmpty() && !tempPass.isEmpty()) {
                try {
                    data.addPlayer(tempUser, tempPass);
                    System.out.println("Account " + tempUser + " successfully created");
                    data.writePlayers();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        //use this to switch scene to game board
        theStage.setScene(gameScene);
    }

    public void playGame(DataFile data, String[] args) {

        this.data = data;

        //fix get players after login completed
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