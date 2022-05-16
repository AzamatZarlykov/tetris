package project.tetris.model.tetromino;

import java.util.List;

/**
 * O tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class OTetromino extends Tetromino {

    /**
     * Instantiating the object results into defining the possible positions of O tetromino
     */
    public OTetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
