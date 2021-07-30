package sample.game;

import javafx.stage.Stage;
import sample.factory.StageFactory;
import sample.field.Field;
import sample.observer.FieldObserver;

public class Game extends Thread {
    private final Field field;
    private FieldObserver observer;

    public Game(Field field) {
        this.field = field;
    }

    @Override
    public void run() {
        while (true){
            if(interrupted()){
                field.getClock().interrupt();
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
        return field;
    }
}
