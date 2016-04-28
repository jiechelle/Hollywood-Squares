package com.queens;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameGUI extends Application {

    Stage window;
    Button[] buttons;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title of Window");
        buttons = new Button[9];

        for (Integer i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setText(Integer.toString(i));
            buttons[i].setOnAction(e -> System.out.println("Lambda expressions are awesome!"));
            buttons[i].setPrefSize(100, 20);
        }

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.TOP_CENTER); // default TOP_LEFT

        VBox vbox1 = new VBox(20);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox(20);
        vbox2.setAlignment(Pos.CENTER);

        VBox vbox3 = new VBox(20);
        vbox3.setAlignment(Pos.CENTER);

        int j = 3;
        int k = 6;
        for (int i = 0; i < 3; i++) {
            vbox1.getChildren().add(buttons[i]);
            vbox2.getChildren().add(buttons[j++]);
            vbox3.getChildren().add(buttons[k++]);
        }

        hbox.getChildren().addAll(vbox1, vbox2, vbox3);
        Scene scene = new Scene(hbox, 500, 300); // the hbox is the root node

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void show(String[] args) {
        launch(args);
    }
}