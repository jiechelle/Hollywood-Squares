package com.queens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameGUI {

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

    private Stage boardStage;
    private Scene gameScene;
    static DataFile data;
    private Player[] players = new Player[2];

    public GameGUI(DataFile data,Player[] players) {
        this.data = data;
        this.players=players;
    }

    public void launchGame(Stage stage) {

        boardStage = stage;
        this.game = new Game(data, players);
        // System.out.println("Player 1: "+players[0].getUsername()+" Player 2: "+players[1].getUsername());

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
            currentPlayer = new Text("Player 1 Mr. X");
        else
            currentPlayer = new Text("Player 2 Mrs. O");

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
            endTurn.setVisible(false);
            game.nextPlayer();

            if (game.getCurrentPlayer().getMarker() == 1)
                currentPlayer.setText("Player 1 Mr. X");
            else
                currentPlayer.setText("Player 2 Mrs. O");

            for (int j = 0; j < guiBoard.length; j++) {
                if (guiBoard[j].getText().equals("")) {
                    guiBoard[j].setDisable(false);
                }
            }
            ((Control) e.getSource()).setVisible(false);
        });


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
        gameScene = new Scene(gameBox, 600, 500);

        boardStage.setTitle("Home");
        boardStage.setScene(gameScene);
        boardStage.setResizable(true);
        boardStage.show();
    }

    public void playerFeedback(int answer) {
        if (answer == game.getCurrentPlayer().getMarker())
            isCorrect.setText("Correct");
        else
            isCorrect.setText("Wrong");

        if (game.checkCurrentPlayerIsWinner()) {
            question.setText("Player " + Integer.toString(game.getCurrentPlayer().getMarker()) + " has won");
            endTurn.setVisible(false);
        } else {
            endTurn.setVisible(true);
        }
        isCorrect.setVisible(true);
        agree.setVisible(false);
        disagree.setVisible(false);

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