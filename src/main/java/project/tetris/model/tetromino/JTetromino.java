package project.tetris.model.tetromino;

import java.util.List;

/**
 * J tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class JTetromino extends Tetromino{

    /**
     * Instantiating the object results into defining the possible positions of J tetromino
     */
    public JTetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {6, 6, 6, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 6, 6, 0},
                {0, 6, 0, 0},
                {0, 6, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 6, 0, 0},
                {0, 6, 6, 6},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 6, 0},
                {0, 0, 6, 0},
                {0, 6, 6, 0},
                {0, 0, 0, 0}
        });
    }
    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
