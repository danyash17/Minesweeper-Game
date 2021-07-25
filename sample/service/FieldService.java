package sample.service;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import sample.field.Field;
import sample.field.Tile;

import java.util.Random;

public class FieldService {


    public GridPane initTileGrid(Field field) {
        int size = field.getSize();
        Tile[][] tiles = field.getTiles();
        GridPane grid = new GridPane();
        grid.minHeight(600);
        grid.minWidth(600);
        grid.setAlignment(Pos.CENTER);
        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(30);
            row.setMinHeight(30);
            grid.getRowConstraints().add(row);
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(30);
            column.setMinWidth(30);
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
                        if (field.isNotStarted()) {
                            initBombs(field);
                            field.setStarted(true);
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        String url = "flag.png";
                        Image image = new Image(url);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(30);
                        imageView.setPreserveRatio(true);
                        GridPane.setRowIndex(imageView, tile.getY());
                        GridPane.setColumnIndex(imageView, tile.getX());
                        field.getTileGrid().getChildren().add(imageView);
                    }
                };
                tile.setOnMousePressed(eventHandler);
                tile.setAlignment(Pos.CENTER);
                tile.minHeight(Region.USE_COMPUTED_SIZE);
                tile.minWidth(Region.USE_COMPUTED_SIZE);
                tile.setMaxWidth(Double.MAX_VALUE);
                tile.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHgrow(tile, Priority.ALWAYS);
                GridPane.setVgrow(tile, Priority.ALWAYS);
            }
        }
        return tiles;
    }


    private boolean[] lookAround(Field field, int x, int y) {
        int size = field.getSize();
        boolean[] emptiesNear = new boolean[8];
        boolean[][] bombLocations = field.getBombs();
        if (x - 1 >= 0) {
            if (y - 1 >= 0) {
                if (bombLocations[x - 1][y - 1]) emptiesNear[6] = true;
            }
            if (y + 1 < size) {
                if (bombLocations[x - 1][y + 1]) emptiesNear[0] = true;
            }
            if (bombLocations[x - 1][y]) emptiesNear[7] = true;
        }
        if (x + 1 < size) {
            if (y + 1 < size) {
                if (bombLocations[x + 1][y + 1]) emptiesNear[2] = true;
                if (bombLocations[x][y + 1]) emptiesNear[1] = true;
            }
            if (y - 1 >= 0) {
                if (bombLocations[x + 1][y - 1]) emptiesNear[4] = true;
                if (bombLocations[x][y - 1]) emptiesNear[5] = true;
            }
            if (bombLocations[x + 1][y]) emptiesNear[3] = true;
        }
        return emptiesNear;
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
                    boolean[] bombsNear = lookAround(field, i, j);
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
        field.setBombGrid(new GridPane());
        field.setBombs(initBombs(field));
        field.setNumbers(initNumbers(field));
        drawField(field);
    }

    private void drawNode(Field field, int i, int j, String url) {
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        GridPane.setRowIndex(imageView, j);
        GridPane.setColumnIndex(imageView, i);
        field.getBombGrid().getChildren().add(imageView);
    }

    private void drawField(Field field) {
        int size = field.getSize();
        int[][] numbers = field.getNumbers();
        boolean[][] bombs = field.getBombs();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bombs[i][j]) {
                    drawNode(field, i, j, "bomb.png");
                    continue;
                }
                switch (numbers[i][j]) {
                    case 0: {
                        drawNode(field, i, j, "tile_empty.png");
                        break;
                    }
                    case 1: {
                        drawNode(field, i, j, "tile1.png");
                        break;
                    }
                    case 2: {
                        drawNode(field, i, j, "tile2.png");
                        break;
                    }
                    case 3: {
                        drawNode(field, i, j, "tile3.png");
                        break;
                    }
                    case 4: {
                        drawNode(field, i, j, "tile4.png");
                        break;
                    }
                    case 5: {
                        drawNode(field, i, j, "tile5.png");
                        break;
                    }
                    case 6: {
                        drawNode(field, i, j, "tile6.png");
                        break;
                    }
                    case 7: {
                        drawNode(field, i, j, "tile7.png");
                        break;
                    }
                    case 8: {
                        drawNode(field, i, j, "tile8.png");
                        break;
                    }
                }
            }
        }
    }
}
