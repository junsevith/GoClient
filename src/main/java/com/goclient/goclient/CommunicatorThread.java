package com.goclient.goclient;

import javafx.application.Platform;

public class CommunicatorThread extends Thread{

    private ServerConnection serverConnection;
    private ServerCommunicator serverCommunicator;

    public CommunicatorThread(ServerConnection serverConnection, ServerCommunicator serverCommunicator){
        this.serverConnection = serverConnection;
        this.serverCommunicator = serverCommunicator;
    }

    @Override
    public void run(){
        while(true){
            try{
                String message = serverConnection.getMessage();
                Platform.runLater(() -> serverCommunicator.recieveMessage2(message));
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
