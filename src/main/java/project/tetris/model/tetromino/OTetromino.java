package project.tetris.model.tetromino;

import java.util.List;

public class OTetromino extends Tetromino {

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
