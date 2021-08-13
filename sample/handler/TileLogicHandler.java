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
    private final Field FIELD;
    private final Group ROOT;
    private final Game GAME;
    private final Tile TILE;
    private final DrawHelper DRAW_HELPER;

    public TileLogicHandler(Field field, Group root, Game game, Tile tile, DrawHelper drawHelper) {
        this.FIELD = field;
        this.ROOT = root;
        this.GAME = game;
        this.TILE = tile;
        this.DRAW_HELPER = drawHelper;
    }
    private class TileImageHandler implements EventHandler<MouseEvent>{
        private final ImageView IMAGE_VIEW;

        public TileImageHandler(ImageView imageView) {
            this.IMAGE_VIEW = imageView;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                FIELD.getFlags().increment();
                FIELD.getTileGrid().getChildren().remove(IMAGE_VIEW);
            }
        }
    }
    @Override
    public void handle(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            FIELD.getTileGrid().getChildren().remove(TILE);
            int x = TILE.getX(), y = TILE.getY();
            DRAW_HELPER.drawRegion(FIELD, ROOT, x, y);
            FIELD.getSoundDispatcher().playSound(Sound.OPEN);
            if (FIELD.getBombs()[x][y]) {
                GAME.getObserver().observeLoose();
                GAME.interrupt();
            }
        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (FIELD.getFlags().getCount() == 0) {
                return;
            }
            GAME.getObserver().observeUpdate(TILE);
            FIELD.getFlags().decrement();
            FIELD.getSoundDispatcher().playSound(Sound.FLAG);
            String url = "resources/images/flag.png";
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            GridPane.setRowIndex(imageView, TILE.getY());
            GridPane.setColumnIndex(imageView, TILE.getX());
            EventHandler<MouseEvent> imageEventHandler = new TileImageHandler(imageView);
            imageView.setOnMousePressed(imageEventHandler);
            FIELD.getTileGrid().getChildren().add(imageView);
        }
    }
}
