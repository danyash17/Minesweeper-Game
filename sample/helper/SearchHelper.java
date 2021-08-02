package sample.helper;

import sample.field.Field;

public class SearchHelper {
    public boolean[] searchBombsAround(Field field, boolean[][] bombLocations, int x, int y) {
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
}
