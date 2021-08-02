package sample.handler;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.enums.Sound;
import sample.field.Field;
import sample.field.Tile;
import sample.game.Game;
import sample.helper.DrawHelper;

public class TileLogicHandler implements EventHandler<MouseEvent> {
    private final Field field;
    private final Group root;
    private final Game game;
    private final Tile tile;
    private final DrawHelper drawHelper;

    public TileLogicHandler(Field field, Group root, Game game, Tile tile, DrawHelper drawHelper) {
        this.field = field;
        this.root = root;
        this.game = game;
        this.tile = tile;
        this.drawHelper = drawHelper;
    }
    private class TileImageHandler implements EventHandler<MouseEvent>{
        private final ImageView imageView;

        public TileImageHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                field.getFlags().increment();
                field.getTileGrid().getChildren().remove(imageView);
            }
        }
    }
    @Override
    public void handle(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            field.getTileGrid().getChildren().remove(tile);
            int x = tile.getX(), y = tile.getY();
            drawHelper.drawRegion(field, root, x, y);
            field.getSoundDispatcher().playSound(Sound.OPEN);
            if (field.getBombs()[x][y]) {
                game.getObserver().observeLoose();
                game.interrupt();
            }
        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (field.getFlags().getCount() == 0) {
                return;
            }
            game.getObserver().observeUpdate(tile);
            field.getFlags().decrement();
            field.getSoundDispatcher().playSound(Sound.FLAG);
            String url = "resources/images/flag.png";
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            GridPane.setRowIndex(imageView, tile.getY());
            GridPane.setColumnIndex(imageView, tile.getX());
            EventHandler<MouseEvent> imageEventHandler = new TileImageHandler(imageView);
            imageView.setOnMousePressed(imageEventHandler);
            field.getTileGrid().getChildren().add(imageView);
        }
    }
}
