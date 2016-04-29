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

        Text question = new Text("Please select Square");

        Text response = new Text();
        response.setVisible(false);

        Label correct = new Label("Correct");
        correct.setVisible(false);

        for (Integer i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setText(Integer.toString(i));
            buttons[i].setOnAction(e -> {

                correct.setText(null);
                game.selectQuestionAndAnswers();
                question.setText(game.getQuestion());
                response.setText(game.getCelebrityAnswer());
                response.setVisible(true);

                trueBTN.setDisable(false);
                falseBTN.setDisable(false);
                for (int j = 0; j < buttons.length; j++) {
                    buttons[j].setDisable(true);
                }
            });
            buttons[i].setPrefSize(100, 100);
        }

        trueBTN = new Button();
        trueBTN.setText("True");
        trueBTN.setOnAction(e -> {
            correct.setVisible(true);
            if (game.checkAnswer())
                correct.setText("Correct");
            else
                correct.setText("Wrong");

            trueBTN.setDisable(true);
            falseBTN.setDisable(true);
            for (int j = 0; j < buttons.length; j++) {
                buttons[j].setDisable(false);
            }
        });
        trueBTN.setPrefSize(100, 20);

        falseBTN = new Button();
        falseBTN.setText("False");
        falseBTN.setOnAction(e -> {
            correct.setVisible(true);
            if (!game.checkAnswer())
                correct.setText("Correct");
            else
                correct.setText("Wrong");

            trueBTN.setDisable(true);
            falseBTN.setDisable(true);
            for (int j = 0; j < buttons.length; j++) {
                buttons[j].setDisable(false);
            }
        });

        falseBTN.setPrefSize(100, 20);

        VBox root_box = new VBox(20);
        root_box.setAlignment(Pos.CENTER); // default TOP_LEFT

        HBox top = new HBox(20);
        top.setAlignment(Pos.CENTER);

        HBox top2 = new HBox(20);
        top.setAlignment(Pos.CENTER);

        HBox first_row = new HBox(20);
        first_row.setAlignment(Pos.CENTER);

        HBox second_row = new HBox(20);
        second_row.setAlignment(Pos.CENTER);

        HBox third_row = new HBox(20);
        third_row.setAlignment(Pos.CENTER);

        HBox bottom = new HBox(20);
        bottom.setAlignment(Pos.TOP_CENTER);

        int j = 3;
        int k = 6;
        for (int i = 0; i < 3; i++) {
            first_row.getChildren().add(buttons[i]);
            second_row.getChildren().add(buttons[j++]);
            third_row.getChildren().add(buttons[k++]);
        }

        top.getChildren().addAll(question);
        top2.getChildren().addAll(response, correct);
        bottom.getChildren().addAll(trueBTN, falseBTN);
        root_box.getChildren().addAll(top, top2, first_row, second_row, third_row, bottom);

        Scene scene = new Scene(root_box, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playGame(DataFile data, Player[] players) {
        this.game = new Game(data, players);
    }

    public void show(String[] args) {
        launch(args);
    }
}