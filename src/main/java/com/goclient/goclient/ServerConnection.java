package com.goclient.goclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    public ServerConnection() {
        try {
            clientSocket = new Socket("127.0.0.1", 6666);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //łączenie z serwerem
    }

    public String getMessage() throws IOException {
        return in.readLine();
    }

    public void reply(String move) {
        out.println(move);
        //wysyłanie odpowiedzi do serwera
    }
}
