package com.goclient.goclient;

import java.util.Map;
import java.util.function.Function;

public abstract class ServerCommunicator {
    private final ServerConnection connection = new ServerConnection();

    private Map<String, Function<String, Boolean>> actionMap = Map.of(
            "ASK", this::askQuestion,
            "CNF", this::askYesNo,
            "CHS", this::askChoose,
            "SET", this::askValue,
            "MOV", this::takeMove,
            "MSG", this::displayMessage,
            "BRD", this::displayBoard,
            "SCR", this::displayScore,
            "DSP", this::displayText
    );



    protected void recieveMessage() {
        boolean continueChecking = true;

        while (continueChecking) {
            String[] msg = connection.getMessage().split("_");
            continueChecking = actionMap.get(msg[0]).apply(msg[1]);
        }

    }
    protected abstract boolean askQuestion(String question);
    protected abstract boolean askYesNo(String s);
    protected abstract boolean askChoose(String s);
    protected abstract boolean askValue(String s);

    protected abstract boolean takeMove(String move);

    protected abstract boolean displayMessage(String message);
    protected abstract boolean displayBoard(String board);
    protected abstract boolean displayScore(String score);
    protected abstract boolean displayText(String text);
}
