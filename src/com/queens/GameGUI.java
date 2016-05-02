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
    private boolean playerAnswer;
    public int selectedSqaure;

    Stage theStage;
    static DataFile data;

    public void start(Stage primaryStage) {
        theStage = primaryStage;
        game.pickFirstPlayer();
        guiBoard = new Button[9];
        agree = new Button("True");
        disagree = new Button("False");
        endTurn = new Button("End Turn");
        endTurn.setVisible(false);

        question = new Text("Please select a square");

        celebrityResponse = new Text();
        celebrityResponse.setVisible(false);

        isCorrect = new Text();
        isCorrect.setVisible(false);

        for (Integer i = 0; i < guiBoard.length; i++) {
            guiBoard[i] = new Button();
            guiBoard[i].setText(Integer.toString(i));
            guiBoard[i].setPrefSize(100, 100);
            // guiBoard[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
            guiBoard[i].setId(Integer.toString(i));

            guiBoard[i].setOnAction(e -> {

                selectedSqaure = Integer.parseInt(( (Control) e.getSource()).getId());
                System.out.println(selectedSqaure);
                isCorrect.setText(null);
                game.selectQuestionAndAnswers();
                question.setText(game.getQuestion());
                celebrityResponse.setText("Celebrity response: " + game.getCelebrityAnswer());
                celebrityResponse.setVisible(true);

                agree.setDisable(false);
                disagree.setDisable(false);
                for (int j = 0; j < guiBoard.length; j++) {
                    guiBoard[j].setDisable(true);
                }
            });
        }

        agree.setPrefSize(100, 20);
        agree.setOnAction(e -> {
            endTurn.setVisible(true);
            playerAnswer = true;
            playerFeedback(game.setSquare(playerAnswer, selectedSqaure));
        });

        disagree.setPrefSize(100, 20);
        disagree.setOnAction(e -> {
            endTurn.setVisible(true);
            playerAnswer = false;
            playerFeedback(!game.setSquare(playerAnswer, selectedSqaure));
        });

        endTurn.setPrefSize(100, 20);
        endTurn.setOnAction(e -> {
            question.setText("Select Square");
            celebrityResponse.setVisible(false);
            isCorrect.setVisible(false);
            agree.setVisible(true);
            disagree.setVisible(true);
            endTurn.setVisible(false);
            selectedSqaure = game.nextPlayer();
            ((Control) e.getSource()).setVisible(false);
        });

        ///LOGIN SCENE CREATION (PANEL)
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
        trueOrFalseBox.getChildren().addAll(agree, disagree, endTurn);

        gameBox.getChildren().addAll(questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        Scene gameScene = new Scene(gameBox, 600, 500);
        Scene loginScene = new Scene(loginPane, 300,300);

        primaryStage.setTitle("Hollywood Squares");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        //TO DO: First Player Login-> Select 1 or 2 Players -> if(1 player) start game else if(2 p) second player login-> login
        //login button press action

        logBtn.setOnAction(e -> {
            String tempUser = userField.getText();
            String tempPass = pwField.getText();

            if(data.checkPlayerCredentials(tempUser,tempPass)) {
                System.out.println("Logged in!");
            }
            else System.out.println("Either username or password is incorrect. Are you registered?");

            //userField.clear();
            pwField.clear();
        });

        //register button press action
        rBtn.setOnAction(e -> {
            //if login already exists, notify user
            //else create new user with login and pw
            theStage.setScene(gameScene);
            System.out.println("Hello World!");
        });

    }

    public void playGame(DataFile data, String[] args) {

        this.data = data;


        this.getPlayers();
        this.game = new Game(data, players);
        launch(args);
    }

    public void getPlayers() {
        players[0] = new Player("ciao", "goodbye", new ArrayList<>());
        players[1] = new Player("abc", "efg", new ArrayList<>());
    }

    public void playerFeedback(boolean answer) {
        if (answer)
            isCorrect.setText("Correct");
        else
            isCorrect.setText("Wrong");
        isCorrect.setVisible(true);

        agree.setVisible(false);
        disagree.setVisible(false);
        endTurn.setVisible(true);
        for (int j = 0; j < guiBoard.length; j++) {
            guiBoard[j].setDisable(false);
        }
    }

    // private class MyEventHandler implements EventHandler<Event> {
    //     @Override
    //     public void handle(Event evt) {
    //         selectedSqaure = Integer.parseInt(((Control)evt.getSource()).getId());
    //         System.out.println(selectedSqaure);
    //     }
    // }
}