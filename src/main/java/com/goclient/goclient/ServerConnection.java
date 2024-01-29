package com.goclient.goclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ServerConnection {
    ArrayList<String> messages = new ArrayList<>(Arrays.asList(
            "MSG_Wiadomość przsyłana z GoConnect#cała reszta rzeczy",
            "MSG_oeoaoeo",
            "MSG_wiadomosc"
    ));

    public ServerConnection() {
        //łączenie z serwerem
    }

    public String getMessage(){
        return messages.get(new Random().nextInt(messages.size()));
    }

    public void reply(String move){
        //wysyłanie odpowiedzi do serwera
    }
}
