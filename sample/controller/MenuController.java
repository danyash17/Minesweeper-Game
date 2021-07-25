package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import sample.field.Field;
import sample.service.MenuService;


public class MenuController {
    @FXML
    public RadioButton novice, solider, commander, doomslayer;
    @FXML
    public Button playbutton;
    private static Field field=Field.getInstance();
    private final MenuService service=new MenuService();

    @FXML
    public void process(ActionEvent event) {
        String id = ((Node) event.getSource()).getId();
        field.setBombs(service.calculateBombs(id.toUpperCase(),field.getSize()));
        System.out.println(field.getBombs());
    }

}
