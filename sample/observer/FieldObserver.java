package sample.observer;

import javafx.stage.Stage;
import sample.enums.Sound;
import sample.factory.StageFactory;
import sample.field.Field;
import sample.field.Tile;
import sample.game.Game;
import sample.sound.SoundDispatcher;

public class FieldObserver {
    private static FieldObserver instance;
    private final StageFactory factory;
    private final SoundDispatcher soundDispatcher;
    private boolean success;
    private final Field field;
    private final Game game;
    private final boolean[][] bombs;
    private final boolean[][] defused;
    private int bombsLeft;

    public FieldObserver(StageFactory factory, Field field, Game game) {
        this.factory = factory;
        this.field = field;
        this.game = game;
        this.soundDispatcher = field.getSoundDispatcher();
        bombs = field.getBombs();
        bombsLeft = field.getBombsCount();
        defused = new boolean[field.getSize()][field.getSize()];
    }

    public void observeUpdate(Tile tile){
        int x = tile.getX(), y = tile.getY();
        if (bombs[x][y]) {
            if (!defused[x][y]) {
                defused[x][y] = true;
                bombsLeft--;
                if (bombsLeft == 0) {
                    success = true;
                    Stage stage = factory.createEndgameStage(field, success);
                    soundDispatcher.playSound(Sound.SUCCESS);
                    game.interrupt();
                    stage.show();
                }
            } else {
                defused[x][y] = false;
                bombsLeft++;
            }
        }
    }

    public void observeLoose(){
        Stage stage = factory.createEndgameStage(field, success);
        soundDispatcher.playSound(Sound.DETONATE);
        game.interrupt();
        stage.show();
    }

    public Field getField() {
        return field;
    }

    public Game getGame() {
        return game;
    }


}
