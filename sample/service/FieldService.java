package sample.service;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.enums.Difficulty;
import sample.enums.Sound;
import sample.field.Field;
import sample.field.Tile;
import sample.game.Game;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class FieldService {


    public GridPane initTileGrid(Field field) {
        int size = field.getSize();
        Tile[][] tiles = field.getTiles();
        GridPane grid = new GridPane();
        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(30);
            row.setMinHeight(30);
            row.setMaxHeight(30);
            row.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(row);
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(30);
            column.setMinWidth(30);
            column.setMaxWidth(30);
            column.setHgrow(Priority.NEVER);
            grid.getColumnConstraints().add(column);
            for (int j = 0; j < size; j++) {
                Tile tile = tiles[i][j];
                GridPane.setRowIndex(tile, i);
                GridPane.setColumnIndex(tile, j);
                grid.add(tile, i, j);
            }
        }
        return grid;
    }

    public Tile[][] initTiles(Field field, Group root, Game game) {
        int size = field.getSize();
        Tile[][] tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile(i, j);
                Tile tile = tiles[i][j];
                tile.setFocusTraversable(false);
                EventHandler<MouseEvent> eventHandler = e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        field.getTileGrid().getChildren().remove(tile);
                        int x = tile.getX(), y = tile.getY();
                        drawAround(field, root, x, y);
                        field.getSoundDispatcher().playSound(Sound.OPEN);
                        if (field.getBombs()[x][y]) {
                            game.getObserver().loose();
                            game.interrupt();
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        if (field.getFlags().getCount() == 0) {
                            return;
                        }
                        game.getObserver().update(tile);
                        field.getFlags().decrement();
                        field.getSoundDispatcher().playSound(Sound.FLAG);
                        String url = "resources/images/flag.png";
                        Image image = new Image(url);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(30);
                        imageView.setPreserveRatio(true);
                        GridPane.setRowIndex(imageView, tile.getY());
                        GridPane.setColumnIndex(imageView, tile.getX());
                        EventHandler<MouseEvent> imageEventHandler = event -> {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                field.getFlags().increment();
                                field.getTileGrid().getChildren().remove(imageView);
                            }
                        };
                        imageView.setOnMousePressed(imageEventHandler);
                        field.getTileGrid().getChildren().add(imageView);
                    }
                };
                tile.setOnMousePressed(eventHandler);
                tile.setAlignment(Pos.CENTER);
                tile.setMaxWidth(Double.MAX_VALUE);
                tile.setMaxHeight(Double.MAX_VALUE);
            }
        }
        return tiles;
    }


    private boolean[] lookAround(Field field, boolean[][] bombLocations, int x, int y) {
        int size = field.getSize();
        boolean[] bombsNear = new boolean[8];
        if (x - 1 >= 0) {
            if (y - 1 >= 0) {
                if (bombLocations[x - 1][y - 1]) bombsNear[6] = true;
            }
            if (y + 1 < size) {
                if (bombLocations[x - 1][y + 1]) bombsNear[0] = true;
            }
            if (bombLocations[x - 1][y]) bombsNear[7] = true;
        }
        if (x + 1 < size) {
            if (y + 1 < size) {
                if (bombLocations[x + 1][y + 1]) bombsNear[2] = true;
                if (bombLocations[x][y + 1]) bombsNear[1] = true;
            }
            if (y - 1 >= 0) {
                if (bombLocations[x + 1][y - 1]) bombsNear[4] = true;
                if (bombLocations[x][y - 1]) bombsNear[5] = true;
            }
            if (bombLocations[x + 1][y]) bombsNear[3] = true;
        }
        return bombsNear;
    }

    private void drawAround(Field field, Group root, int x, int y) {
        int size = field.getSize();
        boolean[][] bombs = field.getBombs();
        boolean[][] openedTiles = field.getOpenedTiles();
        drawNode(field, root, x, y);
        openedTiles[x][y] = true;
        for (int j = 1; j > -2; j--) {
            for (int i = -1; i < 2; i++) {
                if (x + i < size && x + i >= 0 &&
                        y + j < size && y + j >= 0 &&
                        !openedTiles[x + i][y + j] &&
                        !bombs[x + i][y + j]) {
                    boolean[] around = lookAround(field, bombs, x, y);
                    if (IntStream.range(0, around.length)
                            .mapToObj(t -> around[t])
                            .noneMatch(t -> t)) {
                        drawAround(field, root, x + i, y + j);
                    }
                }
            }
        }
    }

    private boolean[][] initBombs(Field field) {
        int size = field.getSize();
        boolean[][] bombLocations = new boolean[size][size];
        int bombs = field.getBombsCount();
        Random coordinate = new Random();
        while (bombs > 0) {
            int x = coordinate.nextInt(size - 1);
            int y = coordinate.nextInt(size - 1);
            if (!bombLocations[x][y]) {
                bombLocations[x][y] = true;
                bombs--;
            }
        }
        return bombLocations;
    }

    private int[][] initNumbers(Field field) {
        int size = field.getSize();
        boolean[][] bombs = field.getBombs();
        int[][] numbers = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!bombs[i][j]) {
                    int counter = 0;
                    boolean[] bombsNear = lookAround(field, field.getBombs(), i, j);
                    for (boolean b : bombsNear) {
                        if (b) counter++;
                    }
                    numbers[i][j] = counter;
                }
            }
        }
        return numbers;
    }

    public void initField(Field field, Group root, Game game) {
        field.setTiles(initTiles(field, root, game));
        field.setTileGrid(initTileGrid(field));
        field.setOpenedTiles(initOpenedTiles(field));
        field.setBombs(initBombs(field));
        field.setNumbers(initNumbers(field));
        field.setScoreboard(initScoreboard(field));
        field.setCountGrid(initCountGrid(field));
    }

    private GridPane initCountGrid(Field field) {
        GridPane grid = new GridPane();
        Text clockText = field.getClock().getText();
        Text flagsText = field.getFlags().getText();
        grid.setPrefHeight(45);
        grid.setPrefWidth(246);
        grid.setLayoutX(175);
        grid.setAlignment(Pos.BASELINE_CENTER);
        ColumnConstraints flagColumn = new ColumnConstraints();
        ColumnConstraints timerColumn = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        flagColumn.setPrefWidth(100);
        timerColumn.setPrefWidth(100);
        grid.getColumnConstraints().add(flagColumn);
        grid.getColumnConstraints().add(timerColumn);
        grid.getRowConstraints().add(row);
        GridPane.setConstraints(clockText, 0, 0);
        GridPane.setHalignment(clockText, HPos.CENTER);
        GridPane.setConstraints(flagsText, 1, 0);
        GridPane.setHalignment(flagsText, HPos.CENTER);
        clockText.setFont(Font.font("Stencil", 45));
        flagsText.setFont(Font.font("Stencil", 45));
        grid.getChildren().add(clockText);
        field.getClock().start();
        grid.getChildren().add(flagsText);
        grid.setStyle("-fx-background-color: #cd3232;");
        return grid;
    }

    private Pane initScoreboard(Field field) {
        Pane pane = new Pane();
        pane.setPrefHeight(45);
        pane.setPrefWidth(600);
        pane.setLayoutY(600);
        Image image = new Image("resources/images/scoreboard.png");
        ImageView imageView = new ImageView(image);
        pane.getChildren().add(imageView);
        return pane;
    }

    private boolean[][] initOpenedTiles(Field field) {
        int size = field.getSize();
        boolean[][] openedTiles = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(openedTiles[i], false);
        }
        return openedTiles;
    }

    private void drawImage(Field field, Group root, int i, int j, String url) {
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        GridPane.setRowIndex(imageView, j);
        GridPane.setColumnIndex(imageView, i);
        field.getTileGrid().getChildren().add(imageView);
    }

    private void drawNode(Field field, Group root, int x, int y) {
        int size = field.getSize();
        int[][] numbers = field.getNumbers();
        boolean[][] bombs = field.getBombs();
        if (bombs[x][y]) {
            drawImage(field, root, x, y, "resources/images/bomb.png");
            return;
        }
        switch (numbers[x][y]) {
            case 0: {
                drawImage(field, root, x, y, "resources/images/tile_empty.png");
                break;
            }
            case 1: {
                drawImage(field, root, x, y, "resources/images/tile1.png");
                break;
            }
            case 2: {
                drawImage(field, root, x, y, "resources/images/tile2.png");
                break;
            }
            case 3: {
                drawImage(field, root, x, y, "resources/images/tile3.png");
                break;
            }
            case 4: {
                drawImage(field, root, x, y, "resources/images/tile4.png");
                break;
            }
            case 5: {
                drawImage(field, root, x, y, "resources/images/tile5.png");
                break;
            }
            case 6: {
                drawImage(field, root, x, y, "resources/images/tile6.png");
                break;
            }
            case 7: {
                drawImage(field, root, x, y, "resources/images/tile7.png");
                break;
            }
            case 8: {
                drawImage(field, root, x, y, "resources/images/tile8.png");
                break;
            }
        }
    }

    public int calculateScore(Field field) {
        int seconds = Integer.parseInt(field.getClock().getText().getText());
        switch (field.getDifficulty().toString().toUpperCase()) {
            case "NOVICE": {
                return 999 - seconds;
            }
            case "SOLIDER": {
                return (int) (999 - (seconds * 1.5));
            }
            case "COMMANDER": {
                return 999 - (seconds * 3);
            }
            case "DOOMSLAYER": {
                return 999 - (seconds * 4);
            }
            default: {
                return 999 - seconds;
            }
        }
    }
}