package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import sample.enums.Difficulty;
import sample.enums.Sound;
import sample.factory.StageFactory;
import sample.field.Field;
import sample.service.FieldService;
import sample.service.MenuService;
import sample.sound.SoundDispatcher;
import sample.factory.SoundDispatcherFactory;

import java.util.Arrays;

public class MainApplication extends Application {
    @FXML
    public RadioButton novice, solider, commander, doomslayer;
    @FXML
    public Button playbutton;
    private static final Field FIELD = Field.getInstance();
    private final MenuService service = new MenuService();
    private final SoundDispatcher soundDispatcher = new SoundDispatcher(new SoundDispatcherFactory());
    public Slider slider;

    public MainApplication() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 560, 625));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @FXML
    public void process(ActionEvent event) {
        String id = ((Node) event.getSource()).getId();
        if (Arrays.stream(Difficulty.values()).anyMatch((x) -> x.name().equals(id.toUpperCase()))) {
            FIELD.setBombCount(service.calculateBombs(id.toUpperCase(), FIELD.getSize()));
            soundDispatcher.playSound(Sound.valueOf(id.toUpperCase()));
        } else if (id.equals("playbutton")) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            service.startGame(FIELD);
        }
    }

}
