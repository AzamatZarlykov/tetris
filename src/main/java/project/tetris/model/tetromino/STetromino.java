package project.tetris.model.tetromino;

import java.util.List;

/**
 * S tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class STetromino extends Tetromino{

    /**
     * Instantiating the object results into defining the possible positions of S tetromino
     */
    public STetromino() {
        tetrominoRepresentation.add(new int[][] {
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {4, 4, 0, 0},
                {0, 0, 0, 0}
        });


        tetrominoRepresentation.add(new int[][] {
                {4, 0, 0, 0},
                {4, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
