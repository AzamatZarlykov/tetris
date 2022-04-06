package project.tetris.model.board;

import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.TetrominoInformation;
import project.tetris.model.tetromino.*;

import java.util.*;

public class Board {
    // constants
    public static final int WIDTH = 15;
    public static final int HEIGHT = 22;
    public static final int BRICK_SIZE = 30;

    private final int[][] tetrisBoard;

    public Board() {
        tetrisBoard = new int[HEIGHT][WIDTH];
    }

    public int[][] getTetrisBoard() {
        return tetrisBoard;
    }
}
