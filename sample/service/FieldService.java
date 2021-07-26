package sample.service;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import sample.field.Coordinates;
import sample.field.Field;
import sample.field.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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
                        if (field.getNumbers()[tile.getX()][tile.getY()] == 0) {
                            drawAround(field, tile.getX(), tile.getY());
                        } else drawNode(field, tile);
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

    private void drawAround(Field field, int x, int y) {
        int size = field.getSize();
        int[][] numbers = field.getNumbers();
        boolean[][] bombs = field.getBombs();
        boolean[][] openedTiles = field.getOpenedTiles();
        boolean[] emptiesNear = new boolean[8];
        drawImage(field, x, y, "tile_empty.png");
        openedTiles[x][y] = true;
        for (int j = 1; j > -2; j--) {
            for (int i = -1; i < 2; i++) {
                if (x + i < size && x + i >= 0 &&
                        y + j < size && y + j >= 0 &&
                        !openedTiles[x + i][y + j] &&
                        numbers[x + i][y + j] == 0 && !bombs[x + i][y + j]) {
                    drawAround(field, x + i, y + j);
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
    }

    private boolean[][] initOpenedTiles(Field field) {
        int size = field.getSize();
        boolean[][] openedTiles = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(openedTiles[i], false);
        }
        return openedTiles;
    }

    private void drawImage(Field field, int i, int j, String url) {
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        GridPane.setRowIndex(imageView, j);
        GridPane.setColumnIndex(imageView, i);
        field.getTileGrid().getChildren().add(imageView);
    }

    private void drawNode(Field field, Tile tile) {
        int size = field.getSize();
        int x = tile.getX(), y = tile.getY();
        int[][] numbers = field.getNumbers();
        boolean[][] bombs = field.getBombs();
        if (bombs[x][y]) {
            drawImage(field, x, y, "bomb.png");
            return;
        }
        switch (numbers[x][y]) {
            case 0: {
                drawImage(field, x, y, "tile_empty.png");
                break;
            }
            case 1: {
                drawImage(field, x, y, "tile1.png");
                break;
            }
            case 2: {
                drawImage(field, x, y, "tile2.png");
                break;
            }
            case 3: {
                drawImage(field, x, y, "tile3.png");
                break;
            }
            case 4: {
                drawImage(field, x, y, "tile4.png");
                break;
            }
            case 5: {
                drawImage(field, x, y, "tile5.png");
                break;
            }
            case 6: {
                drawImage(field, x, y, "tile6.png");
                break;
            }
            case 7: {
                drawImage(field, x, y, "tile7.png");
                break;
            }
            case 8: {
                drawImage(field, x, y, "tile8.png");
                break;
            }
        }
    }
}

