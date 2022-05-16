package project.tetris.model.tetromino;

import java.util.List;

/**
 * T tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class TTetromino extends Tetromino {

    /**
     * Instantiating the object results into defining the possible positions of T tetromino
     */
    public TTetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 3, 0, 0},
                {3, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 3, 0, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });

        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {3, 3, 3, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
