package com.goclient.goclient;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoController extends ServerCommunicator {

    public Label welcomeText;

    public VBox container;

    int black;

    int white;

    String communicat;

    @FXML
    protected void initialize() {
        communicatorThread.start();
        container.setAlignment(Pos.CENTER);
//        recieveMessage();
    }

    @Override
    protected boolean askQuestion(String question) {
        Label ask = new Label(question);
        ask.setStyle("-fx-font-size: 15;");
        container.getChildren().add(new Label(question));
        TextField textField = new TextField();
        container.getChildren().add(textField);
        Button confirm = new Button("Potwierdź");
        container.getChildren().add(confirm);
        confirm.setOnMouseClicked(event -> {
            sendMessage(textField.getText());
//            recieveMessage();
        });

        System.out.println(question);
        return false;
    }

    @Override
    protected boolean askYesNo(String s) {
        container.setSpacing(5);
        Label question = new Label(s);
        question.setStyle("-fx-font-size: 15;");
        container.getChildren().add(question);
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        Button yes = new Button("Tak");
        yes.setOnMouseClicked(event -> {
            sendMessage("yes");
//            recieveMessage();
        });
        Button no = new Button("Nie");
        no.setOnMouseClicked(event -> {
            sendMessage("no");
//            recieveMessage();
        });
        buttonContainer.getChildren().add(yes);
        buttonContainer.getChildren().add(no);
        container.getChildren().add(buttonContainer);
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean askChoose(String s) {
        container.setSpacing(5);
        ArrayList<String> choices = new ArrayList<>(List.of(s.split("%")));
        Label question = new Label(choices.getFirst());
        question.setStyle("-fx-font-size: 15;");
        container.getChildren().add(question);
        choices.removeFirst(); // tutaj jest pytanie do wyświetlenia
//        choices.removeLast(); // tutaj są informacje do konsoli, cała reszta do dostępne opcje
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
//        buttonContainer.setPadding(new javafx.geometry.Insets(5, 0, 0, 0));
//        question.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
        List<Button> buttons = new ArrayList<>();
        for (String choice : choices) {
            Button button = new Button(choice);
            buttons.add(button);
            button.setOnMouseClicked(event -> {
                sendMessage(choice);
//                recieveMessage();
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
//            recieveMessage();
        });
        System.out.println(s);
        return false;
    }

    @Override
    protected boolean takeMove(String move) {
        waitingForAnswer = true;
        Button pass = new Button("Pas");
        pass.setOnMouseClicked(event -> {
                    sendMessage("pas");
                });
        Button resign = new Button("Zrezygnuj");
        resign.setOnMouseClicked(event -> {
            sendMessage("resign");
        });
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(pass);
        buttonContainer.getChildren().add(resign);
        buttonContainer.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
        container.getChildren().add(buttonContainer);
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
        container.setSpacing(0);
        //container.setStyle("-fx-background-color: lightblue;");
        container.setAlignment(Pos.TOP_CENTER);
        ArrayList<String> lines = new ArrayList<>(List.of(board.split("\\|")));
        ArrayList<ArrayList<String>> tiles = new ArrayList<>();
        for (String line : lines) {
            tiles.add(new ArrayList<>(List.of(line.split(" "))));
//            System.out.println(Arrays.toString(line.split(" ")));
        }
        int boardSize = lines.size();
        int cellSize = 600 / boardSize;
        Canvas canvas = new Canvas(600, 600);

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
        Label bS = new Label("Wynik czarnego: " + white);
        bS.setStyle("-fx-font-size: 15;");
        Label wS = new Label("Wynik białego: " + black);
        wS.setStyle("-fx-font-size: 15;");
        container.getChildren().add(bS);
        container.getChildren().add(wS);
        System.out.println(board);
        return true;
    }

    @Override
    protected boolean displayScore(String score) {
        ArrayList<String> scores = new ArrayList<>(List.of(score.split(" ")));
        black = Integer.parseInt(scores.getFirst());
        white = Integer.parseInt(scores.getLast());
        System.out.println(score);
        return true;
    }

    @Override
    protected boolean displayText(String text) {
        reset();
        ArrayList<String> lines = new ArrayList<>(List.of(text.split("\\|")));
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().add(vbox);
        for (String line : lines) {
            Label label = new Label(line);
            label.setStyle("-fx-font-size: 15;");
            vbox.getChildren().add(label);
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

    @Override
    protected void reset() {
        container.setAlignment(Pos.CENTER);
        container.getChildren().clear();
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
//                        recieveMessage();
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