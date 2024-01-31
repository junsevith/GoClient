package com.goclient.goclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GoApplication.class.getResource("container.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 830);
        stage.setTitle("GO");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();

        GoController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}