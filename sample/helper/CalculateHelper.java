package sample.helper;

import sample.field.Field;

public class CalculateHelper {
    public int calculateBombs(String difficulty, int size) {
        switch (difficulty) {
            case "NOVICE": {
                return size - 1;
            }
            case "SOLIDER": {
                return (int) (size + Math.pow((double) size * 0.25, 2) - 1);
            }
            case "COMMANDER": {
                return (int) (size + Math.pow((double) size * 0.45, 2) - 1);
            }
            case "DOOMSLAYER": {
                return (int) (size + Math.pow((double) size * 0.7, 2) - 1);
            }
            default:
                return size - 1;
        }
    }
    public int calculateScore(Field field) {
        int seconds = Integer.parseInt(field.getClock().getText().getText());
        switch (field.getDifficulty().toString().toUpperCase()) {
            case "NOVICE": {
                return (int) ((999 - seconds)*0.2);
            }
            case "SOLIDER": {
                return 999 - seconds;
            }
            case "COMMANDER": {
                return (999 - seconds)*2;
            }
            case "DOOMSLAYER": {
                return (999 - seconds)*4;
            }
            default: {
                return (int) ((999 - seconds)*0.2);
            }
        }
    }
}
