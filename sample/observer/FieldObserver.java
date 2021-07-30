package sample.observer;

import sample.field.Field;
import sample.field.Tile;
import sample.game.Game;

public class FieldObserver {
    private static FieldObserver instance;
    private final Field field;
    private final Game game;
    private final boolean[][] bombs;
    private final boolean[][] defused;
    private int bombsLeft;

    public FieldObserver(Field field,Game game) {
        this.field = field;
        this.game=game;
        bombs = field.getBombs();
        bombsLeft=field.getBombsCount();
        defused=new boolean[field.getSize()][field.getSize()];
    }

    public void update(Tile tile){
        int x=tile.getX(),y=tile.getY();
        if(bombs[x][y]){
            if(!defused[x][y]){
                defused[x][y]=true;
                bombsLeft--;
                if(bombsLeft==0){
                    game.interrupt();
                }
            }
            else {
                defused[x][y]=false;
                bombsLeft++;
            }
        }
    }

    public Field getField() {
        return field;
    }

    public Game getGame() {
        return game;
    }
}
