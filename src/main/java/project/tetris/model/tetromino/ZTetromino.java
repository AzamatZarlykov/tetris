package project.tetris.model.tetromino;

import java.util.List;

/**
 * Z tetromino representation that inherits from Tetromino abstract class
 *
 * @author Azamat Zarlykov
 */
public class ZTetromino extends Tetromino {

    /**
     * Instantiating the object results into defining the possible positions of Z tetromino
     */
    public ZTetromino() {
          tetrominoRepresentation.add(new int[][] {
                  {0, 0, 0, 0},
                  {5, 5, 0, 0},
                  {0, 5, 5, 0},
                  {0, 0, 0, 0}
          });

        tetrominoRepresentation.add(new int[][] {
                {0, 5, 0, 0},
                {5, 5, 0, 0},
                {5, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getTetrominoRepresentation() {
        return tetrominoRepresentation;
    }
}
