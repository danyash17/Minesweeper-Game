package sample.service;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.factory.StageFactory;
import sample.field.Field;
import sample.game.Game;

public class MenuService {

    public MenuService() {
    }

    @FXML
    public int calculateBombs(String difficulty, int size) {
        return switch (difficulty) {
            case "NOVICE" -> size - 1;
            case "SOLIDER" -> (int) (size + Math.pow((double) size * 0.25, 2) - 1);
            case "COMMANDER" -> (int) (size + Math.pow((double) size * 0.45, 2) - 1);
            case "DOOMSLAYER" -> (int) (size + Math.pow((double) size * 0.7, 2) - 1);
            default -> size - 1;
        };
    }

    @FXML
    public void startGame(Field field) {
        Game game=new Game(field);
        Stage stage=StageFactory.getInstance().createGameStage(game,field);
        game.start();
        stage.show();
    }
}
