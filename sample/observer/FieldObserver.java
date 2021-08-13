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
    private final StageFactory FACTORY;
    private final SoundDispatcher SOUND_DISPATCHER;
    private final Field FIELD;
    private final Game GAME;
    private final boolean[][] BOMBS;
    private final boolean[][] DEFUSED;
    private boolean success;
    private int bombsLeft;

    public FieldObserver(StageFactory factory, Field field, Game game) {
        this.FACTORY = factory;
        this.FIELD = field;
        this.GAME = game;
        this.SOUND_DISPATCHER = field.getSoundDispatcher();
        BOMBS = field.getBombs();
        bombsLeft = field.getBombsCount();
        DEFUSED = new boolean[field.getSize()][field.getSize()];
    }

    public void observeUpdate(Tile tile){
        int x = tile.getX(), y = tile.getY();
        if (BOMBS[x][y]) {
            if (!DEFUSED[x][y]) {
                DEFUSED[x][y] = true;
                bombsLeft--;
                if (bombsLeft == 0) {
                    success = true;
                    Stage stage = FACTORY.createEndgameStage(FIELD, success);
                    SOUND_DISPATCHER.playSound(Sound.SUCCESS);
                    GAME.interrupt();
                    stage.show();
                }
            } else {
                DEFUSED[x][y] = false;
                bombsLeft++;
            }
        }
    }

    public void observeLoose(){
        Stage stage = FACTORY.createEndgameStage(FIELD, success);
        SOUND_DISPATCHER.playSound(Sound.DETONATE);
        GAME.interrupt();
        stage.show();
    }

    public Field getField() {
        return FIELD;
    }

    public Game getGame() {
        return GAME;
    }


}
