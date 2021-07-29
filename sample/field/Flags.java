package sample.field;

import javafx.scene.text.Text;

public class Flags {
    private Text text;
    private int count;

    public Flags(Text text, int count) {
        this.text = text;
        this.count = count;
    }

    public Flags() {
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        count++;
        text.setText(String.valueOf(count));
    }

    public void decrement() {
        count--;
        text.setText(String.valueOf(count));
    }
}
