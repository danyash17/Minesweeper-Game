package sample.field;

import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends Thread {
    private Text text;
    private AtomicInteger seconds;

    public Clock() {
        text=new Text("000");
        seconds=new AtomicInteger(0);
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
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
        seconds.getAndIncrement();
        StringBuffer newSeconds=new StringBuffer(String.valueOf(seconds.get()));
        if(newSeconds.length()<3){
            if (newSeconds.length() == 1) {
                newSeconds.insert(0, "00");
            } else {
                newSeconds.insert(0, "0");
            }
        }
        text.setText(String.valueOf(newSeconds));
        if(seconds.get()==999) interrupt();
    }
}
