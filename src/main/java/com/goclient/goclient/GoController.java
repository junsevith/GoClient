package com.goclient.goclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GoController extends ServerCommunicator {


    public Label welcomeText;

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

    public void onHelloButtonClick(ActionEvent actionEvent) {
        recieveMessage();
    }
}