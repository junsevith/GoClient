package com.goclient.goclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoController extends ServerCommunicator {
    public Label welcomeText;

    public VBox container;

    int blackScore;

    int whiteScore;

    String communicat;

    @FXML
    protected void initialize() {
        recieveMessage();
    }

    @Override
    protected boolean askQuestion(String question) {
        container.getChildren().add(new Label(question));
        TextField textField = new TextField();
        container.getChildren().add(textField);
        Button confirm = new Button("Potwierdź");
        container.getChildren().add(confirm);
        confirm.setOnMouseClicked(event -> {
            sendMessage(textField.getText());
            recieveMessage();
        });

        System.out.println(question);
        return false;
    }

    @Override
    protected boolean askYesNo(String s) {
        Label question = new Label(s);
        question.setStyle("-fx-font-size: 15;");
        container.getChildren().add(question);
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        Button yes = new Button("Tak");
        yes.setOnMouseClicked(event -> {
            sendMessage("yes");
            recieveMessage();
        });
        Button no = new Button("Nie");
        no.setOnMouseClicked(event -> {
            sendMessage("no");
            recieveMessage();
        });
        buttonContainer.getChildren().add(yes);
        buttonContainer.getChildren().add(no);
        container.getChildren().add(buttonContainer);
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean askChoose(String s) {
        container.setSpacing(10);
        ArrayList<String> choices = new ArrayList<>(List.of(s.split("%")));
        Label question = new Label(choices.getFirst());
        question.setStyle("-fx-font-size: 15;");
        container.getChildren().add(question);
        choices.removeFirst(); // tutaj jest pytanie do wyświetlenia
        choices.removeLast(); // tutaj są informacje do konsoli, cała reszta do dostępne opcje
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        List<Button> buttons = new ArrayList<>();
        for (String choice : choices) {
            Button button = new Button(choice);
            buttons.add(button);
            button.setOnMouseClicked(event -> {
                sendMessage(choice);
                recieveMessage();
            });
            buttonContainer.getChildren().add(button);
        }
        container.getChildren().add(buttonContainer);
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean askValue(String s) {
        container.getChildren().add(new Label(s));
        TextField textField = new TextField();
        container.getChildren().add(textField);
        Button confirm = new Button("Potwierdź");
        container.getChildren().add(confirm);
        confirm.setOnMouseClicked(event -> {
            sendMessage(textField.getText());
            recieveMessage();
        });
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean takeMove(String move) {
        waitingForAnswer = true;
        System.out.println(move);
        return false;
    }

    @Override
    protected boolean displayMessage(String message) {
        communicat = message;
        System.out.println(message);
        return true;
    }

    @Override
    protected boolean displayBoard(String board) {
        ArrayList<String> lines = new ArrayList<>(List.of(board.split("\\|")));
        ArrayList<ArrayList<String>> tiles = new ArrayList<>();
        for (String line : lines) {
            tiles.add(new ArrayList<>(List.of(line.split(" "))));
//            System.out.println(Arrays.toString(line.split(" ")));
        }
        int boardSize = lines.size();
        int cellSize = 600 / boardSize;
        Canvas canvas = new Canvas(640, 600);

        container.getChildren().clear();
        Label comm = new Label(communicat);
        comm.setStyle("-fx-font-size: 20;");
        container.getChildren().add(comm);
        Group group = new Group();
        container.getChildren().add(group);
        group.getChildren().add(canvas);
        group.getChildren().add(drawStones(tiles, boardSize, cellSize));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBoard(gc, boardSize, cellSize);
        Label bS = new Label("Wynik czarnego: " + Integer.toString(blackScore));
        bS.setStyle("-fx-font-size: 15;");
        Label wS = new Label("Wynik białego: " + Integer.toString(whiteScore));
        wS.setStyle("-fx-font-size: 15;");
        container.getChildren().add(bS);
        container.getChildren().add(wS);
        System.out.println(board);
        return true;
    }

    @Override
    protected boolean displayScore(String score) {
        ArrayList<String> scores = new ArrayList<>(List.of(score.split(" ")));
        blackScore = Integer.parseInt(scores.getFirst());
        whiteScore = Integer.parseInt(scores.getLast());
        System.out.println(score);
        return true;
    }

    @Override
    protected boolean displayText(String text) {
        ArrayList<String> lines = new ArrayList<>(List.of(text.split("\\|")));
        for (String line : lines) {
            container.getChildren().add(new Label(line));
//            System.out.println(Arrays.toString(line.split(" ")));
        }
        System.out.println(text);
        return true;
    }

    @Override
    protected boolean displayError(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        System.out.println(message);
        return true;
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
        recieveMessage();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(GoApplication.class.getResource("container.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> sendMessage("exit"));
    }

    public void drawBoard(GraphicsContext gc, int boardSize, int cellSize) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, boardSize * cellSize, boardSize * cellSize);

        gc.setStroke(Color.BLACK);
        for (int k = 0; k < boardSize; k++) {
            double xLine = k * cellSize + cellSize / 2.0;
            double yLine = k * cellSize + cellSize / 2.0;

            gc.strokeLine(xLine, 0, xLine, (boardSize - 1) * cellSize + cellSize);
            gc.strokeLine(0, yLine, (boardSize - 1) * cellSize + cellSize, yLine);
        }
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);
        gc.strokeRect(0, 0, boardSize * cellSize, boardSize * cellSize);

    }

    public Group drawStones(ArrayList<ArrayList<String>> board, int boardSize, int cellSize) {
        Group group = new Group();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                double radius = cellSize / 4.0;
                double x = i * cellSize + cellSize / 2.0;
                double y = j * cellSize + cellSize / 2.0;
                String stoneString = board.get(j).get(i).strip();
                Map<String, Color> colorMap = Map.of(
                        "N", Color.TRANSPARENT,
                        "B", Color.BLACK,
                        "W", Color.WHITE
                );
                StoneGUI stone = new StoneGUI(colorMap.get(stoneString), x, y, radius, j, i);
                stone.setOnMouseClicked(event -> {
                    if (waitingForAnswer()) {
                        sendMessage((stone.getX() + 1) + " " + (stone.getY() + 1));
                        recieveMessage();
                    }
                });
                group.getChildren().add(stone);
//                    board[i][j] = stone;
            }
        }
        return group;

    }

    boolean waitingForAnswer = false;

    private boolean waitingForAnswer() {
        if (waitingForAnswer) {
            waitingForAnswer = false;
            return true;
        }
        return false;
    }
}