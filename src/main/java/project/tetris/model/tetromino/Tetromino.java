package project.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of game Tetromino as an abstract class
 *
 * @author Azamat Zarlykov
 */
public abstract class Tetromino {
    // list of all possible positions of the tetromino

    /**
     * List of all possible positions of the tetromino
     */
    protected final List<int[][]> tetrominoRepresentation = new ArrayList<>();

    /**
     * @return the first/default representation of the tetromino
     */
    public int[][] getStructure() {
        return getTetrominoRepresentation().get(0);
    }

    /**
     * @return all possible positions of the tetromino
     */
    public abstract List<int[][]> getTetrominoRepresentation();
}
