package project.tetris.model.tetromino;

import java.util.List;

/**
 * I tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class ITetromino extends Tetromino{

    /**
     * Instantiating the object results into defining the possible positions of I tetromino
     */
    public ITetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
