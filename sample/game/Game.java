package sample.game;

import sample.field.Field;
import sample.observer.FieldObserver;

public class Game extends Thread {
    private final Field FIELD;
    private FieldObserver observer;

    public Game(Field field) {
        this.FIELD = field;
    }

    @Override
    public void run() {
        while (true){
            if(interrupted()){
                FIELD.getClock().interrupt();
                break;
            }
        }
    }


    public FieldObserver getObserver() {
        return observer;
    }

    public void setObserver(FieldObserver observer) {
        this.observer = observer;
    }

    public Field getField() {
        return FIELD;
    }
}
