package com.queens;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameGUI extends Application {

    private static Game game;
    private Button[] buttons;
    private Button trueBTN;
    private Button falseBTN;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hollywood Squares");
        buttons = new Button[9];
        trueBTN = new Button();
        falseBTN = new Button();

        Text question = new Text("Please select a square");

        Text celebrityResponse = new Text();
        celebrityResponse.setVisible(false);

        Label isTrueLabel = new Label();
        isTrueLabel.setVisible(false);

        for (Integer i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setText(Integer.toString(i));
            buttons[i].setOnAction(e -> {

                isTrueLabel.setText(null);
                game.selectQuestionAndAnswers();
                question.setText(game.getQuestion());
                celebrityResponse.setText(game.getCelebrityAnswer());
                celebrityResponse.setVisible(true);

                trueBTN.setDisable(false);
                falseBTN.setDisable(false);
                for (int j = 0; j < buttons.length; j++) {
                    buttons[j].setDisable(true);
                }
            });
            buttons[i].setPrefSize(100, 100);
        }

        trueBTN.setText("True");
        trueBTN.setPrefSize(100, 20);
        trueBTN.setOnAction(e -> {
            isTrueLabel.setVisible(true);
            if (game.checkAnswer())
                isTrueLabel.setText("Correct");
            else
                isTrueLabel.setText("Wrong");

            trueBTN.setDisable(true);
            falseBTN.setDisable(true);
            for (int j = 0; j < buttons.length; j++) {
                buttons[j].setDisable(false);
            }
        });

        falseBTN.setText("False");
        falseBTN.setPrefSize(100, 20);
        falseBTN.setOnAction(e -> {
            isTrueLabel.setVisible(true);
            if (!game.checkAnswer())
                isTrueLabel.setText("Correct");
            else
                isTrueLabel.setText("Wrong");

            trueBTN.setDisable(true);
            falseBTN.setDisable(true);
            for (int j = 0; j < buttons.length; j++) {
                buttons[j].setDisable(false);
            }
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

        firstRowSquares.getChildren().addAll(buttons[0], buttons[1], buttons[2]);
        secondRowSquares.getChildren().addAll(buttons[3], buttons[4], buttons[5]);
        thirdRowSquares.getChildren().addAll(buttons[6], buttons[7], buttons[8]);
        questionBox.getChildren().addAll(question);
        responseBox.getChildren().addAll(celebrityResponse, isTrueLabel);
        trueOrFalseBox.getChildren().addAll(trueBTN, falseBTN);

        rootBox.getChildren().addAll(questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        Scene scene = new Scene(rootBox, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playGame(DataFile data, Player[] players, String[] args) {
        this.game = new Game(data, players);
        launch(args);
    }
}