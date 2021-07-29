package sample.service;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.field.Field;
import sample.field.Tile;

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

    public Tile[][] initTiles(Field field, Group root) {
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
                        if (field.getNumbers()[x][y] == 0 && !field.getBombs()[x][y]) {
                            drawAround(field, root, x, y);
                        } else drawNode(field, root, x, y);
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        if (field.getFlags().getCount() == 0) {
                            return;
                        }
                        field.getFlags().decrement();
                        String url = "flag.png";
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
        while (bombs != 0) {
            bombLocations[coordinate.nextInt(size - 1)][coordinate.nextInt(size - 1)] = true;
            bombs--;
        }
        return bombLocations;
    }

    private int[][] initNumbers(Field field) {
        int size = field.getSize();
        boolean[][] bombLocations = field.getBombs();
        int[][] numbers = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!bombLocations[i][j]) {
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

    public void initField(Field field, Group root) {
        field.setTiles(initTiles(field, root));
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
        pane.setLayoutY(608);
        Image image = new Image("scoreboard.png");
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
            drawImage(field, root, x, y, "bomb.png");
            return;
        }
        switch (numbers[x][y]) {
            case 0 -> {
                drawImage(field, root, x, y, "tile_empty.png");
            }
            case 1 -> {
                drawImage(field, root, x, y, "tile1.png");
            }
            case 2 -> {
                drawImage(field, root, x, y, "tile2.png");
            }
            case 3 -> {
                drawImage(field, root, x, y, "tile3.png");
            }
            case 4 -> {
                drawImage(field, root, x, y, "tile4.png");
            }
            case 5 -> {
                drawImage(field, root, x, y, "tile5.png");
            }
            case 6 -> {
                drawImage(field, root, x, y, "tile6.png");
            }
            case 7 -> {
                drawImage(field, root, x, y, "tile7.png");
            }
            case 8 -> {
                drawImage(field, root, x, y, "tile8.png");
            }
        }
    }
}