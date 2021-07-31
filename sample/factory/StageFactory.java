package sample.factory;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.MainApplication;
import sample.field.Field;
import sample.game.Game;
import sample.observer.FieldObserver;
import sample.service.FieldService;
import sample.service.MenuService;


public class StageFactory {
    private static StageFactory instance;
    private final FieldService fieldService;

    private StageFactory(FieldService fieldService,MenuService menuService) {
        this.fieldService = fieldService;
    }
    public static StageFactory getInstance(){
        if (instance == null) {
            instance = new StageFactory(new FieldService(),new MenuService());
        }
        return instance;
    }


    public Stage createGameStage(Game game, Field field) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 645);
        Stage stage = new Stage();
        fieldService.initField(field, root, game);
        game.setObserver(new FieldObserver(this, field, game));
        root.getChildren().add(field.getTileGrid());
        root.getChildren().add(field.getScoreboard());
        field.getScoreboard().getChildren().add(field.getCountGrid());
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.setResizable(false);
        return stage;

    }

    public Stage createEndgameStage(Field field, boolean success) {
        if (success) {
            return createVariousEndgameStage(field,"Congratulations to the winner!",success);
        }
        else {
            return createVariousEndgameStage(field,"You loosed :( Try again, comrade!",success);
        }
    }
    private Stage createVariousEndgameStage(Field field,String firstLine,boolean success){
        Group root = new Group();
        Scene scene = new Scene(root, 502, 258);
        Stage stage = new Stage();
        Text congrats = new Text(firstLine);
        congrats.setFont(Font.font("Stencil", 21));
        congrats.setLayoutX(70);
        congrats.setLayoutY(29);
        int points = success ?fieldService.calculateScore(field):0;
        Text score = new Text("Your final score is\n"+points);
        score.setFont(Font.font("Stencil", 21));
        score.setLayoutX(141);
        score.setLayoutY(80);
        score.setTextAlignment(TextAlignment.CENTER);
        Button exit = new Button();
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        };
        exit.setText("Exit");
        exit.setFont(Font.font("Stencil",27));
        exit.setLayoutX(48);
        exit.setLayoutY(158);
        exit.setPrefHeight(59);
        exit.setPrefWidth(130);
        exit.setOnAction(handler);
        Button playAgain = new Button();
        EventHandler<ActionEvent> playAgainHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                Stage fieldStage= (Stage) field.getCountGrid().getScene().getWindow();
                fieldStage.close();
                Platform.runLater( () -> {
                    try {
                        new MainApplication().start( new Stage() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        playAgain.setText("Play Again");
        playAgain.setFont(Font.font("Stencil",18));
        playAgain.setLayoutX(308);
        playAgain.setLayoutY(158);
        playAgain.setPrefHeight(59);
        playAgain.setPrefWidth(130);
        playAgain.setMnemonicParsing(false);
        playAgain.setOnAction(playAgainHandler);
        root.getChildren().add(congrats);
        root.getChildren().add(score);
        root.getChildren().add(exit);
        root.getChildren().add(playAgain);
        stage.setScene(scene);
        return stage;
    }
}
