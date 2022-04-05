package project.tetris.model.tetromino;

import java.util.List;

public class JTetromino extends Tetromino{

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
