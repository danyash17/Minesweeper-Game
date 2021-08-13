package sample.helper;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sample.field.Field;
import sample.path.ImagePath;

import java.util.stream.IntStream;

public class DrawHelper {
    private final SearchHelper SEARCH_HELPER;

    public DrawHelper(SearchHelper searchHelper) {
        this.SEARCH_HELPER = searchHelper;
    }

    public void drawRegion(Field field, Group root, int x, int y) {
        int size = field.getSize();
        boolean[][] bombs = field.getBombs();
        boolean[][] openedTiles = field.getOpenedTiles();
        drawNode(field, root, x, y);
        openedTiles[x][y] = true;
        for (int j = 1; j > -2; j--) {
            for (int i = -1; i < 2; i++) {
                if (x + i < size && x + i >= 0 &&
                        y + j < size && y + j >= 0 &&
                        !openedTiles[x + i][y + j] &&
                        !bombs[x + i][y + j]) {
                    boolean[] around = SEARCH_HELPER.searchBombsAround(field, bombs, x, y);
                    if (IntStream.range(0, around.length)
                            .mapToObj(t -> around[t])
                            .noneMatch(t -> t)) {
                        drawRegion(field, root, x + i, y + j);
                    }
                }
            }
        }
    }

    public void drawImage(Field field, Group root, int i, int j, String url) {
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        GridPane.setRowIndex(imageView, j);
        GridPane.setColumnIndex(imageView, i);
        field.getTileGrid().getChildren().add(imageView);
    }

    public void drawNode(Field field, Group root, int x, int y) {
        int[][] numbers = field.getNumbers();
        boolean[][] bombs = field.getBombs();
        if (bombs[x][y]) {
            drawImage(field, root, x, y, ImagePath.BOMB);
            return;
        }
        switch (numbers[x][y]) {
            case 0: {
                drawImage(field, root, x, y, ImagePath.EMPTY);
                break;
            }
            case 1: {
                drawImage(field, root, x, y, ImagePath.ONE);
                break;
            }
            case 2: {
                drawImage(field, root, x, y, ImagePath.TWO);
                break;
            }
            case 3: {
                drawImage(field, root, x, y, ImagePath.THREE);
                break;
            }
            case 4: {
                drawImage(field, root, x, y, ImagePath.FOUR);
                break;
            }
            case 5: {
                drawImage(field, root, x, y, ImagePath.FIVE);
                break;
            }
            case 6: {
                drawImage(field, root, x, y, ImagePath.SIX);
                break;
            }
            case 7: {
                drawImage(field, root, x, y, ImagePath.SEVEN);
                break;
            }
            case 8: {
                drawImage(field, root, x, y, ImagePath.EIGHT);
                break;
            }
            case 9: {
                drawImage(field, root, x, y, ImagePath.NINE);
                break;
            }
        }
    }
}
