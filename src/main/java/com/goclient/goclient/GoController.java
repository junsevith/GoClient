package com.goclient.goclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;
import java.util.function.Function;

public class GoController {
    private final GoConnect connection = new GoConnect();

    private Map<String, Function<String, Boolean>> actionMap = Map.of(
            "MSG", this::displayMessage,
            "MOV", this::takeMove,
            "BRD", this::updateBoard,
            "CNF", this::askYesNo,
            "CHO", this::choseValue,
            "SET", this::setValue
    );

    @FXML
    protected void initialize() {

        recieveMessage();
    }

    private void recieveMessage() {
        boolean continueChecking = true;

        while (continueChecking) {
            String[] msg = connection.getMessage().split("_");
            continueChecking = actionMap.get(msg[0]).apply(msg[1]);
        }

    }


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("oeo");
        recieveMessage();
    }

    protected boolean displayMessage(String message) {
        welcomeText.setText(message.split("#")[0]);
        return false;
    }

    protected boolean takeMove(String move){
        return false;
    }

    protected boolean updateBoard(String board){
        return true;
    }

    private Boolean askYesNo(String s) {
        return false;
    }

    private Boolean setValue(String s) {
        return false;
    }

    private Boolean choseValue(String s) {
        return false;
    }
}