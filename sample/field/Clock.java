package sample.field;

import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends Thread {
    private final Text TEXT;
    private final AtomicInteger SECONDS;

    public Clock() {
        TEXT =new Text("000");
        SECONDS =new AtomicInteger(0);
    }

    public Text getText() {
        return TEXT;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            incrementSeconds();
        }
    }

    private synchronized void incrementSeconds() {
        SECONDS.getAndIncrement();
        StringBuffer newSeconds=new StringBuffer(String.valueOf(SECONDS.get()));
        if(newSeconds.length()<3){
            if (newSeconds.length() == 1) {
                newSeconds.insert(0, "00");
            } else {
                newSeconds.insert(0, "0");
            }
        }
        TEXT.setText(String.valueOf(newSeconds));
        if(SECONDS.get()==999) interrupt();
    }
}
