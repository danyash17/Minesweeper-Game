package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import javafx.stage.Stage;
import sample.enums.Difficulty;
import sample.field.Field;
import sample.service.FieldService;
import sample.service.MenuService;

import java.util.Arrays;


public class Controller {
    @FXML
    public RadioButton novice, solider, commander, doomslayer;
    @FXML
    public Button playbutton;
    private static final Field field=Field.getInstance();
    private final MenuService service=new MenuService(new FieldService());

    @FXML
    public void process(ActionEvent event) {
        String id = ((Node) event.getSource()).getId();
        if(Arrays.stream(Difficulty.values()).anyMatch((x) -> x.name().equals(id.toUpperCase()))){
            field.setBombCount(service.calculateBombs(id.toUpperCase(),field.getSize()));
        }
        else if(id.equals("playbutton")){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
            service.startGame(field);
        }
    }

}
