package com.queens;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameGUI extends Application {

    private static Game game;
    private Button[] guiBoard;
    private Button agree;
    private Button disagree;
    private Button endTurn;
    private Text question;
    private Text celebrityResponse;
    private Text isCorrect;
    private boolean playerAnswer;
    private int selectedSqaure;

    public void start(Stage primaryStage) {
        game.pickFirstPlayer();
        guiBoard = new Button[9];
        agree = new Button("True");
        disagree = new Button("False");
        endTurn = new Button("End Turn");

        question = new Text("Please select a square");

        celebrityResponse = new Text();
        celebrityResponse.setVisible(false);

        isCorrect = new Text();
        isCorrect.setVisible(false);

        for (Integer i = 0; i < guiBoard.length; i++) {
            guiBoard[i] = new Button();
            guiBoard[i].setText(Integer.toString(i));
            guiBoard[i].setPrefSize(100, 100);
            guiBoard[i].setOnAction(e -> {

                // selectedSqaure = FIGURE THIS SHIT OUT
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
            playerAnswer = true;
            playerFeedback(game.setSquare(playerAnswer, selectedSqaure));
        });

        disagree.setPrefSize(100, 20);
        disagree.setOnAction(e -> {
             playerAnswer = false;
            playerFeedback(!game.setSquare(playerAnswer, selectedSqaure));
        });

        endTurn.setPrefSize(100, 20);
        endTurn.setOnAction(e -> {
            agree.setVisible(true);
            disagree.setVisible(true);
            endTurn.setVisible(false);
            game.nextPlayer();
        });

        VBox rootBox = new VBox(20);
        rootBox.setAlignment(Pos.CENTER); // default TOP_LEFT

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

        rootBox.getChildren().addAll(questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        Scene scene = new Scene(rootBox, 600, 500);
        primaryStage.setTitle("Hollywood Squares");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playGame(DataFile data, Player[] players, String[] args) {
        this.game = new Game(data, players);
        launch(args);
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
}