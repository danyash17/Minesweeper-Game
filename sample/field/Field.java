package sample.field;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.enums.Difficulty;
import sample.sound.SoundDispatcher;
import sample.factory.SoundDispatcherFactory;

public class Field {
    private static Field instance;
    private int size;
    private int bombCount;
    private Difficulty difficulty;
    private Tile[][] tiles;
    private int[][] numbers;
    private boolean[][] bombs;
    private boolean[][] openedTiles;
    private GridPane tileGrid, countGrid;
    private Pane scoreboard;
    private Flags flags;
    private Clock clock;
    private final SoundDispatcher soundDispatcher;
    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.valueOf("NOVICE");
    private static final int DEFAULT_SIZE = 20;
    private static final int DEFAULT_BOMB_COUNT = DEFAULT_SIZE - 1;

    private Field() {
        difficulty = DEFAULT_DIFFICULTY;
        size = DEFAULT_SIZE;
        bombCount = DEFAULT_BOMB_COUNT;
        flags = new Flags(new Text(String.valueOf(bombCount)), bombCount);
        clock = new Clock();
        soundDispatcher = new SoundDispatcher(new SoundDispatcherFactory());
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

    public int[][] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public int getBombsCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
        flags.setCount(bombCount);
        flags.setText(new Text(String.valueOf(bombCount)));
    }

    public boolean[][] getBombs() {
        return bombs;
    }

    public void setBombs(boolean[][] bombs) {
        this.bombs = bombs;
    }

    public boolean[][] getOpenedTiles() {
        return openedTiles;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setOpenedTiles(boolean[][] openedTiles) {
        this.openedTiles = openedTiles;
    }

    public Pane getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Pane scoreboard) {
        this.scoreboard = scoreboard;
    }

    public GridPane getCountGrid() {
        return countGrid;
    }

    public void setCountGrid(GridPane countGrid) {
        this.countGrid = countGrid;
    }

    public SoundDispatcher getSoundDispatcher() {
        return soundDispatcher;
    }
}
