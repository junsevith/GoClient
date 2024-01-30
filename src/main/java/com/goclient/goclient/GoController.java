package com.goclient.goclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoController extends ServerCommunicator {
    public Label welcomeText;

    public VBox container;

    @FXML
    protected void initialize() {
        recieveMessage();
    }

    @Override
    protected boolean askQuestion(String question) {
        System.out.println(question);
        return false;
    }

    @Override
    protected boolean askYesNo(String s) {
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean askChoose(String s) {
        ArrayList<String> choices = new ArrayList<>(List.of(s.split("%")));
        container.getChildren().add(new Label(choices.getFirst()));
        choices.removeFirst(); // tutaj jest pytanie do wyświetlenia
        choices.removeLast(); // tutaj są informacje do konsoli, cała reszta do dostępne opcje
        HBox buttonContainer = new HBox();
        List<Button> buttons = new ArrayList<>();
        for (String choice : choices) {
            Button button = new Button(choice);
            buttons.add(button);
            button.setOnMouseClicked(event -> {
                sendMessage(choice);
                recieveMessage();
            });
            buttonContainer.getChildren().add(button);
        }
        container.getChildren().add(buttonContainer);
        return false;
    }

    @Override
    protected boolean askValue(String s) {
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean takeMove(String move) {
        System.out.println(move);
        return false;
    }

    @Override
    protected boolean displayMessage(String message) {
        System.out.println(message);
        return true;
    }

    @Override
    protected boolean displayBoard(String board) {
        System.out.println(board);
        return true;
    }

    @Override
    protected boolean displayScore(String score) {
        System.out.println(score);
        return true;
    }

    @Override
    protected boolean displayText(String text) {
        System.out.println(text);
        return true;
    }

    @Override
    protected boolean displayError(String message) {
        System.out.println(message);
        return true;
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
        recieveMessage();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(FirstFrame.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> sendMessage("exit"));
    }
}