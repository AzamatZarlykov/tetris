package project.tetris.model.board;

import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.TetrominoInformation;
import project.tetris.model.tetromino.*;

import java.util.*;

public class Board {
    // constants
    public static final int WIDTH = 15;
    public static final int HEIGHT = 22;
    public static final int BRICK_SIZE = 29;

    // board
    private final int[][] tetrisBoard;
    // current tetromino on the board
    private TetrominoInformation currentTetromino;

    public Board() {
        tetrisBoard = new int[HEIGHT][WIDTH];
    }

    public void setCurrentTetromino(TetrominoInformation tetromino) {
        this.currentTetromino = tetromino;
    }

    // checks if the position is within the board
    private boolean outOfBounds(int[][] tetrisBoard, int targetX, int targetY) {
        return targetX < 0 || targetY >= tetrisBoard.length || targetX >= tetrisBoard[targetY].length;
    }

    private boolean outOfBoardBorder(int[][] tetromino, Position currentPos) {
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[i].length; j++) {
                int targetX = currentPos.getXPos() + j;
                int targetY = currentPos.getYPos() + i;

                if (tetromino[i][j] != 0 &&
                        (outOfBounds(tetrisBoard, targetX, targetY) ||
                                tetrisBoard[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // move tetromino down the board
    public boolean moveTetrominoDown() {
        Position currentPos = currentTetromino.getPosition();

        Position newPos = new Position(currentPos.getXPos(), currentPos.getYPos() + 1);
        currentTetromino.setTetrominoPosition(newPos);

        boolean anyConflict = outOfBoardBorder(currentTetromino.getTetromino(), newPos);

        if (anyConflict) {
            return false;
        } else {
            currentTetromino.setTetrominoPosition(newPos);
            return true;
        }
    }

    public int[][] getTetrisBoard() {
        return tetrisBoard;
    }
}
