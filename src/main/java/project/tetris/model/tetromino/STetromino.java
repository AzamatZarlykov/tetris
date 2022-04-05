package project.tetris.model.tetromino;

import java.util.List;

public class STetromino extends Tetromino{

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
