package project.tetris.model.tetromino;

import java.util.List;

public class ZTetromino extends Tetromino {

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
