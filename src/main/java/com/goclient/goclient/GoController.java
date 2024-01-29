package com.goclient.goclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        return false;
    }

    @Override
    protected boolean askYesNo(String s) {
        return false;
    }

    @Override
    protected boolean askChoose(String s) {
        List<String> choices = Arrays.asList(s.split(" "));
        List<Button> buttons = new ArrayList<>();
        for (String choice : choices) {
            Button button = new Button(choice);
            buttons.add(button);
            button.setOnMouseClicked(event -> {
                sendMessage(choice);
            });
            container.getChildren().add(button);
        }


        return false;
    }

    @Override
    protected boolean askValue(String s) {
        return false;
    }

    @Override
    protected boolean takeMove(String move) {
        return false;
    }

    @Override
    protected boolean displayMessage(String message) {
        welcomeText.setText(message);
        return false;
    }

    @Override
    protected boolean displayBoard(String board) {
        return false;
    }

    @Override
    protected boolean displayScore(String score) {
        return false;
    }

    @Override
    protected boolean displayText(String text) {
        return false;
    }

    @Override
    protected boolean displayError(String message) {
        return false;
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
}