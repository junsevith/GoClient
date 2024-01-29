package com.goclient.goclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ServerConnection {
    ArrayList<String> messages = new ArrayList<>(Arrays.asList(
            "CHS_graj wczytaj oeo oao"
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
