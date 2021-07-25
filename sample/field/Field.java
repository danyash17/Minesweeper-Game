package sample.field;

import sample.controller.MenuController;
import sample.enums.Difficulty;

import java.util.Objects;

public class Field {
    private static Field instance;
    private int size;
    private int bombs;
    private Difficulty difficulty;
    private static final Difficulty DEFAULT_DIFFICULTY=Difficulty.valueOf("SOLIDER");
    private static final int DEFAULT_SIZE = 20;
    public static final double BETCHEL_BOARD_BENCHMARK_VALUE = 4.29;

    private Field() {
        difficulty=DEFAULT_DIFFICULTY;
        size = DEFAULT_SIZE;
        bombs = 0;
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

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
