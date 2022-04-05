package project.tetris.model.tetromino;

import java.util.List;

public class ITetromino extends Tetromino{

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
