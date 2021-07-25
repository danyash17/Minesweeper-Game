package sample.service;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class MenuService {
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
                return (int)(size+Math.pow((double)size*0.25,2)-1);
        }
    }

    @FXML
    public void startGame() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/game.fxml"));
            Stage game = new Stage();
            game.setTitle("Minesweeper");
            game.setScene(new Scene(root, 600, 600));
            game.setResizable(false);
            game.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
