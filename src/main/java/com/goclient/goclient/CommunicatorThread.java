package com.goclient.goclient;

public class CommunicatorThread extends Thread{

    private ServerConnection serverConnection;
    private GoController goController;

    public CommunicatorThread(ServerConnection serverConnection, GoController goController){
        this.serverConnection = serverConnection;
        this.goController = goController;
    }

    @Override
    public void run(){
        while(true){
            try{
                String message = serverConnection.getMessage();
                goController.recieveMessage();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
