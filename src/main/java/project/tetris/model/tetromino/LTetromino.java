package project.tetris.model.tetromino;

import java.util.List;

/**
 * L tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class LTetromino extends Tetromino{

    /**
     * Instantiating the object results into defining the possible positions of L tetromino
     */
    public LTetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 7, 7, 7},
                {0, 7, 0, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 7, 0, 0},
                {0, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 0, 7, 0},
                {7, 7, 7, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 7, 0},
                {0, 0, 7, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
