package com.goclient.goclient;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstFrame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("GO GAME");

        VBox root = new VBox(10);
        Button newGameButton = new Button("NEW GAME");
        newGameButton.setId("newGameButton");
        Button loadGameButton = new Button("LOAD GAME");
        loadGameButton.setId("loadGameButton");

        newGameButton.setOnAction(e -> {

        });

        loadGameButton.setOnAction(e -> {

        });

        root.getChildren().addAll(newGameButton, loadGameButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}