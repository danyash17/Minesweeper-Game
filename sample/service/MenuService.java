package sample.service;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.field.Field;

public class MenuService {
    private final FieldService fieldService;

    public MenuService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @FXML
    public int calculateBombs(String difficulty, int size) {
        switch (difficulty) {
            case "NOVICE":
                return size - 1;
            case "SOLIDER":
                return (int)(size+Math.pow((double)size*0.25,2)-1);
            case "COMMANDER":
                return (int)(size+Math.pow((double)size*0.45,2)-1);
            case "DOOMSLAYER":
                return (int)(size+Math.pow((double)size*0.7,2)-1);
            default:
                return size-1;
        }
    }

    @FXML
    public void startGame(Field field) {
        Group root = new Group();
        Scene scene=new Scene(root, 608, 608);
        Stage game = new Stage();
        fieldService.initField(field,root);
        root.getChildren().add(field.getTileGrid());
        game.setTitle("Minesweeper");
        game.setScene(scene);
        game.setResizable(false);
        game.show();
    }
}
