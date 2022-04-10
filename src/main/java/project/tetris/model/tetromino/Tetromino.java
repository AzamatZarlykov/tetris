package project.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino {
    // list of all possible positions of the tetromino
    protected final List<int[][]> tetrominoRepresentation = new ArrayList<>();

    public int[][] getStructure() {
        return getTetrominoRepresentation().get(0);
    }
    public abstract List<int[][]> getTetrominoRepresentation();
}
