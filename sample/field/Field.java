package sample.field;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sample.enums.Difficulty;

public class Field {
    private static Field instance;
    private int size;
    private int bombCount;
    private boolean notStarted;
    private Difficulty difficulty;
    private Tile[][] tiles;
    private int[][] numbers;
    private boolean[][] bombs;
    @FXML
    private GridPane tileGrid, bombGrid;
    private static final Difficulty DEFAULT_DIFFICULTY=Difficulty.valueOf("SOLIDER");
    private static final int DEFAULT_SIZE = 20;
    public static final double BETCHEL_BOARD_BENCHMARK_VALUE = 4.29;

    private Field() {
        difficulty=DEFAULT_DIFFICULTY;
        size = DEFAULT_SIZE;
        bombCount=size-1;
        notStarted=true;
    }

    public static Field getInstance() {
        if (instance == null) {
            return new Field();
        } else return instance;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public GridPane getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(GridPane tileGrid) {
        this.tileGrid = tileGrid;
    }

    public boolean isNotStarted() {
        return notStarted;
    }

    public void setStarted(boolean notStarted) {
        this.notStarted = notStarted;
    }

    public int[][] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public GridPane getBombGrid() {
        return bombGrid;
    }

    public void setBombGrid(GridPane bombGrid) {
        this.bombGrid = bombGrid;
    }

    public int getBombsCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public boolean[][] getBombs() {
        return bombs;
    }

    public void setBombs(boolean[][] bombs) {
        this.bombs = bombs;
    }
}
