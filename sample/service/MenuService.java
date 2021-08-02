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
    public void startGame(Field field) {
        Game game=new Game(field);
        Stage stage=StageFactory.getInstance().createGameStage(game,field);
        game.start();
        stage.show();
    }
}
