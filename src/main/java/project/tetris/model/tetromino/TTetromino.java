package project.tetris.model.tetromino;

import java.util.List;

public class TTetromino extends Tetromino {

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
