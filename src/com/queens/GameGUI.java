package com.queens;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameGUI {

    private Button[] boardButtons;
    private Button agree;
    private Button disagree;
    private Button endTurn;
    private Text scores;
    private Text question;
    private Text celebrityResponse;
    private Text isCorrect;
    private Text currentPlayer;
    private int selectedSquare;

    private Stage boardStage;
    private Scene gameScene;

    private static Game game;
    private static DataFile data;
    private static LoginGUI loginGUI;
    private Player[] players = new Player[2];

    public GameGUI(DataFile iData,Player[] iPlayers, LoginGUI iloginGUI) {
        data = iData;
        players = iPlayers;
        loginGUI = iloginGUI;
    }

    public void launchGame(Stage stage) {
        boardStage = stage;

        // Initialize game
        game = new Game(data, players);

        initializeButtonsAndText();

        // Initialize boardButtons
        for (Integer i = 0; i < boardButtons.length; i++) {
            boardButtons[i] = new Button();
            boardButtons[i].setPrefSize(100, 100);
            boardButtons[i].setId(Integer.toString(i));

            boardButtons[i].setOnAction(e -> {

                selectedSquare = Integer.parseInt(((Control) e.getSource()).getId());

                // When square is clicked, pick a question from the file
                game.selectQuestion();

                // set the Text for question and celebrityResponse
                question.setText(game.getQuestion());
                celebrityResponse.setText("Celebrity response: " + game.getCelebrityAnswer());

                // set Text and Buttons visible
                agree.setVisible(true);
                disagree.setVisible(true);
                celebrityResponse.setVisible(true);

                // Disable all buttons after one button is clicked
                for (Button guiBoardSquare : boardButtons) {
                    guiBoardSquare.setDisable(true);
                }
            });
        }

        agree.setOnAction(e -> {
            playerFeedback(game.determineSquareFate(true, selectedSquare));
        });

        disagree.setOnAction(e -> {
            playerFeedback(game.determineSquareFate(false, selectedSquare));
        });

        endTurn.setOnAction(e -> {
            game.nextPlayer();

            // if the next player is "the computer" then select a square and then call nextPlayer
            if (game.getCurrentPlayer().getUsername().equals("the computer")) {
                selectedSquare = game.computerSelectSquare();
                playerFeedback(game.determineSquareFate(game.computerResponse(), selectedSquare));
                game.nextPlayer();
            }

            setTextForPlayer();

            celebrityResponse.setVisible(false);
            isCorrect.setVisible(false);
            endTurn.setVisible(false);

            // Enable all buttons that are blank
            for (Button guiBoardSquare : boardButtons) {
                if (guiBoardSquare.getText().equals("")) {
                    guiBoardSquare.setDisable(false);
                }
            }

            // Disable end turn button
            ((Control) e.getSource()).setVisible(false);
        });

        gameScene = new Scene(createGameBox(), 700, 600);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        boardStage.setTitle("Hollywood Squares");
        boardStage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 3);
        boardStage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 5);
        boardStage.setScene(gameScene);
        boardStage.setResizable(true);
        boardStage.show();
    }

    private void playerFeedback(int squareMarker) {
        if (squareMarker == game.getCurrentPlayer().getMarker())
            isCorrect.setText("Correct");
        else
            isCorrect.setText("Wrong");

        setScoreText();

        System.out.println("GUI about to check if the current player (" +
                game.getCurrentPlayer().getUsername() + ") is winner");

        if (game.checkCurrentPlayerIsWinner()) {
            System.out.println("Current player (" + game.getCurrentPlayer().getUsername() + ") is winner!\n");
            question.setText("Player " + Integer.toString(game.getCurrentPlayer().getMarker()) + " has won");

            endTurn.setVisible(false);
            celebrityResponse.setVisible(false);
            currentPlayer.setVisible(false);
            isCorrect.setVisible(false);

            // todo: create dialog pop up box to ask user to choose one of two things ...
            // todo: replay the game with same players OR return to login
            // return to login, need to reset board state, player state, game state etc..

            // if game is restarted then call game.restartGame()

            // players[0] = null;
            // players[1] = null;
            // loginGUI.launchLogin(boardStage);

        } else {
            System.out.println("Current player (" + game.getCurrentPlayer().getUsername() + ") is not winner\n");
            endTurn.setVisible(true);
            isCorrect.setVisible(true);
        }

        agree.setVisible(false);
        disagree.setVisible(false);

        // Update marker on square
        if (squareMarker == 1)
            boardButtons[selectedSquare].setText("X");
        else if (squareMarker == 2)
            boardButtons[selectedSquare].setText("O");
    }

    private void initializeButtonsAndText() {
        // Initialize buttons
        boardButtons = new Button[9];
        agree = new Button("True");
        disagree = new Button("False");
        endTurn = new Button("End Turn");

        // set size of buttons
        agree.setPrefSize(100, 20);
        disagree.setPrefSize(100, 20);
        endTurn.setPrefSize(100, 20);

        // Hide agree, disagree and endTurn buttons
        agree.setVisible(false);
        disagree.setVisible(false);
        endTurn.setVisible(false);

        // Initialize text fields
        scores = new Text();
        question = new Text();
        celebrityResponse = new Text();
        isCorrect = new Text();
        currentPlayer = new Text();

        // Hide celebrityResponse and isCorrect text fields
        celebrityResponse.setVisible(false);
        isCorrect.setVisible(false);

        setTextForPlayer();
        setScoreText();
    }

    private VBox createGameBox() {
        VBox gameBox = new VBox(20);
        gameBox.setAlignment(Pos.CENTER);

        HBox scoreBox = new HBox(20);
        scoreBox.setAlignment(Pos.CENTER);

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
        trueOrFalseBox.setAlignment(Pos.CENTER);

        firstRowSquares.getChildren().addAll(boardButtons[0], boardButtons[1], boardButtons[2]);
        secondRowSquares.getChildren().addAll(boardButtons[3], boardButtons[4], boardButtons[5]);
        thirdRowSquares.getChildren().addAll(boardButtons[6], boardButtons[7], boardButtons[8]);

        scoreBox.getChildren().addAll(scores);
        questionBox.getChildren().addAll(question);
        responseBox.getChildren().addAll(celebrityResponse, isCorrect);
        trueOrFalseBox.getChildren().addAll(currentPlayer, agree, disagree, endTurn);

        gameBox.getChildren().addAll(scoreBox, questionBox, responseBox, firstRowSquares, secondRowSquares, thirdRowSquares, trueOrFalseBox);

        return gameBox;
    }

    private void setTextForPlayer() {
        question.setText("Please select a square");
        currentPlayer.setText(game.getCurrentPlayer().getUsername() + " turn");
    }

    private void setScoreText() {
        scores.setText("Scores:    " +
                game.getPlayer1().getUsername() + " (" + game.getPlayer1().getMarkerLetter() + "):   " + game.getPlayer1().getCurrentScore() + "        " +
                game.getPlayer2().getUsername() + " (" + game.getPlayer2().getMarkerLetter() + "):   "  + game.getPlayer2().getCurrentScore());
    }

}