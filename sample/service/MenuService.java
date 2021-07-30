package sample.service;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.field.Field;
import sample.game.Game;
import sample.observer.FieldObserver;

public class MenuService {
    private final FieldService fieldService;

    public MenuService(FieldService fieldService) {
        this.fieldService = fieldService;
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
        Group root = new Group();
        Scene scene=new Scene(root, 608, 653);
        Stage stage = new Stage();
        Game game=new Game(field);
        fieldService.initField(field,root,game);
        game.setObserver(new FieldObserver(field,game));
        root.getChildren().add(field.getTileGrid());
        root.getChildren().add(field.getScoreboard());
        field.getScoreboard().getChildren().add(field.getCountGrid());
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.setResizable(false);
        game.start();
        stage.show();
    }
}
