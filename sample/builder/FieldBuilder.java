package sample.builder;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.field.Field;
import sample.field.Tile;
import sample.game.Game;
import sample.handler.TileLogicHandler;
import sample.helper.DrawHelper;
import sample.helper.SearchHelper;

import java.util.Arrays;
import java.util.Random;

public class FieldBuilder {
    private final DrawHelper drawHelper;
    private final SearchHelper searchHelper;

    public FieldBuilder(DrawHelper drawHelper, SearchHelper searchHelper) {
        this.drawHelper = drawHelper;
        this.searchHelper = searchHelper;
    }

    public void buildField(Field field, Group root, Game game) {
        field.setTiles(buildTiles(field, root, game));
        field.setTileGrid(buildTileGrid(field));
        field.setOpenedTiles(buildOpenedTiles(field));
        field.setBombs(buildBombs(field));
        field.setNumbers(buildNumbers(field));
        field.setScoreboard(buildScoreboard(field));
        field.setCountGrid(buildCountGrid(field));
    }

    public GridPane buildTileGrid(Field field) {
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

    public Tile[][] buildTiles(Field field, Group root, Game game) {
        int size = field.getSize();
        Tile[][] tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile(i, j);
                Tile tile = tiles[i][j];
                tile.setFocusTraversable(false);
                EventHandler<MouseEvent> handler = new TileLogicHandler(field, root, game, tile, drawHelper);
                tile.setOnMousePressed(handler);
                tile.setAlignment(Pos.CENTER);
                tile.setMaxWidth(Double.MAX_VALUE);
                tile.setMaxHeight(Double.MAX_VALUE);
            }
        }
        return tiles;
    }


    private boolean[][] buildBombs(Field field) {
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

    private int[][] buildNumbers(Field field) {
        int size = field.getSize();
        boolean[][] bombs = field.getBombs();
        int[][] numbers = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!bombs[i][j]) {
                    int counter = 0;
                    boolean[] bombsNear = searchHelper.searchBombsAround(field, field.getBombs(), i, j);
                    for (boolean b : bombsNear) {
                        if (b) counter++;
                    }
                    numbers[i][j] = counter;
                }
            }
        }
        return numbers;
    }


    private GridPane buildCountGrid(Field field) {
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

    private Pane buildScoreboard(Field field) {
        Pane pane = new Pane();
        pane.setPrefHeight(45);
        pane.setPrefWidth(600);
        pane.setLayoutY(600);
        Image image = new Image("resources/images/scoreboard.png");
        ImageView imageView = new ImageView(image);
        pane.getChildren().add(imageView);
        return pane;
    }

    private boolean[][] buildOpenedTiles(Field field) {
        int size = field.getSize();
        boolean[][] openedTiles = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(openedTiles[i], false);
        }
        return openedTiles;
    }
}