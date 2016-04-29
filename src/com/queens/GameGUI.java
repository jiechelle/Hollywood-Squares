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

    private Game game;
    private Button[] buttons;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title of Window");
        buttons = new Button[9];

        // System.out.println(game.setSquare());
        for (Integer i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setText(Integer.toString(i));
            buttons[i].setOnAction(e -> {
                // String t = game.setSquare();
                buttons[0].setText("x");
                System.out.println("body button");
            });
            buttons[i].setPrefSize(100, 100);
        }

        Label correct = new Label("Correct");
        correct.setVisible(false);

        Button accept = new Button();
        accept.setText("Yes");
        accept.setOnAction(e -> {
            correct.setVisible(true);
            correct.setText("Correct");
        });
        accept.setPrefSize(100, 20);

        Button decline = new Button();
        decline.setText("No");
        decline.setOnAction(e -> {
            correct.setVisible(true);
            correct.setText("Wrong");
        });
        decline.setPrefSize(100, 20);

        Text question = new Text();
        question.setText("QUESTION GOES HERE");

        VBox root_box = new VBox(20);
        root_box.setAlignment(Pos.CENTER); // default TOP_LEFT

        HBox top = new HBox(20);
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

        top.getChildren().addAll(question, correct);
        bottom.getChildren().addAll(accept, decline);
        root_box.getChildren().addAll(top, first_row, second_row, third_row, bottom);

        Scene scene = new Scene(root_box, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playGame(Game game, String[] args) {
        this.game = game;
        launch(args);
    }
}