package com.goclient.goclient;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public abstract class ServerCommunicator {
    private final ServerConnection connection = new ServerConnection();

    private final Map<String, Function<String, Boolean>> actionMap = Map.of(
            "ASK", this::askQuestion,
            "CNF", this::askYesNo,
            "CHS", this::askChoose,
            "SET", this::askValue,
            "MOV", this::takeMove,
            "MSG", this::displayMessage,
            "BRD", this::displayBoard,
            "SCR", this::displayScore,
            "DSP", this::displayText,
            "ERR", this::displayError
    );



    protected void recieveMessage() {
        boolean continueChecking = true;

        while (continueChecking) {
            String input;
            try {
                input = connection.getMessage();
            } catch (IOException e) {
                input = "ERR_Unable to read message from server";
            }
            if (input == null) {
                System.out.println("Server disconnected");
                System.exit(0);
            }
            String[] msg = input.split("_");
            if (msg.length > 1) {
                input = msg[1];
            }
//            System.out.println(msg[0] + "_" + input);
            continueChecking = actionMap.get(msg[0]).apply(input);
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
    protected abstract boolean displayError(String message);

    protected void sendMessage(String message) {
        System.out.println(message);
        connection.reply(message);
    }
}
