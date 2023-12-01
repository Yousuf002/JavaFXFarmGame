import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character {
    private static final double CELL_SIZE = 75;
    private static final int GRID_SIZE = 9;

    private ImageView imageView;
    private int currentRow;
    private int currentCol;
    private static final double CHARACTER_SIZE = 50; 

    public Character(Image characterImage, int initialRow, int initialCol) {
        this.imageView = new ImageView(characterImage);
        
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        updatePosition();
        this.imageView.setFitWidth(CHARACTER_SIZE);
        this.imageView.setFitHeight(CHARACTER_SIZE);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void moveLeft() {
        if (currentCol > 0) {
            currentCol--;
            updatePosition();
        }
    }

    public void moveRight() {
        if (currentCol < GRID_SIZE - 1) {
            currentCol++;
            updatePosition();
        }
    }

    public void moveUp() {
        if (currentRow > 0) {
            currentRow--;
            updatePosition();
        }
    }

    public void moveDown() {
       
        if (currentRow < GRID_SIZE - 1) {
            currentRow++;
            updatePosition();
        }
    }

    private void updatePosition() {
        imageView.setTranslateX(currentCol * CELL_SIZE);
        imageView.setTranslateY(currentRow * CELL_SIZE);
    }
}
