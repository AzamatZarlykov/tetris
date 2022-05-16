package project.tetris.model.helper;

/**
 * Position record stores the current location of the tetromino in the game
 *
 * @author Azamat Zarlykov
 */
public record Position(int x, int y) {


    /**
     * Returns the x position of the tetromino
     *
     * @return int x position to be returned
     */
    public int getXPos() {
        return x;
    }

    /**
     * Returns the y position of the tetromino
     *
     * @return int y position to be returned
     */
    public int getYPos() {
        return y;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
