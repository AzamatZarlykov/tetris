package project.tetris.model.board;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.TetrominoInformation;

import java.util.*;

/**
 * Class <code>Board</code> represents the game board.
 *
 * Its responsibility is to maintain the movements of tetromino and store them in the background
 *
 * @author Azamat Zarlykov
 */
public class Board {
    /**
     * Constant width of the board
     */
    public static final int WIDTH = 15;
    /**
     * Constant height of the board
     */
    public static final int HEIGHT = 22;
    /**
     * Constant size of the square brick
     */
    public static final int BRICK_SIZE = 29;

    /**
     * Representation of the board by 2 dimensional array
     */
    private int[][] tetrisBoard;
    /**
     * Tetromino that is located on the board
     */
    private TetrominoInformation currentTetromino;
    /**
     * Current score of the game
     */
    private final IntegerProperty score;

    /**
     * Constructor that instantiates the board and score of the game
     */
    public Board() {
        tetrisBoard = new int[HEIGHT][WIDTH];
        score = new SimpleIntegerProperty(0);
    }

    /**
     * @return the score of the game
     */
    public IntegerProperty getScore() {
        return score;
    }

    /**
     * @return the board representation of the game
     */
    public int[][] getTetrisBoard() {
        return tetrisBoard;
    }

    /**
     * Increments the score of the game
     *
     * @param val additional points to be added to the score
     */
    public void incrementScore(int val) {
        score.setValue(score.getValue() + val);
    }

    /**
     * @param tetromino tetromino to be passed
     */
    public void setCurrentTetromino(TetrominoInformation tetromino) {
        this.currentTetromino = tetromino;
    }

    /**
     * This method embeds the current falling tetromino to a board
     *
     * <code>mergeBrickToBackground</code> creates the copy of the array to store the tetromino
     * since otherwise changes cannot be made to a board that's currently being used
     */
    public void mergeBrickToBackground() {
        int[][] tetromino = currentTetromino.getTetromino();
        Position tetrominoPos = currentTetromino.getPosition();

        int[][] copy = copy(tetrisBoard);
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[i].length; j++) {
                int targetX = tetrominoPos.getXPos() + j;
                int targetY = tetrominoPos.getYPos() + i;
                if (tetromino[i][j] != 0) {
                    copy[targetY - 1][targetX] = tetromino[i][j];
                }
            }
        }
        tetrisBoard = copy;
    }

    /**
     * @param original original board
     * @return copy of the current board
     */
    // neat copy 2D: https://stackoverflow.com/questions/5617016/how-do-i-copy-a-2-dimensional-array-in-java
    public int[][] copy(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }


    /**
     * @param targetX x position
     * @param targetY y position
     * @return true if withing the bounds of the board, false otherwise
     */
    private boolean outOfBounds(int targetX, int targetY) {
        return targetX < 0 || targetY >= tetrisBoard.length || targetX >= tetrisBoard[targetY].length;
    }

    /**
     * This method checks if the tetromino is within the board
     *
     * @param tetromino current tetromino representation
     * @param currentPos current position of tetromino
     * @return true if tetromino is out of the board, false otherwise
     */
    public boolean outOfBoardBorder(int[][] tetromino, Position currentPos) {
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[i].length; j++) {
                int targetX = currentPos.getXPos() + j;
                int targetY = currentPos.getYPos() + i;

                if (tetromino[i][j] != 0 &&
                        (outOfBounds(targetX, targetY) ||
                                tetrisBoard[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param line row of the board
     * @return true if the row is full (filled by tetrominos), false otherwise
     */
    private boolean fullLine(int[] line) {
        boolean clear = true;
        for (int j : line) {
            if (j == 0) {
                clear = false;
                break;
            }
        }
        return clear;
    }

    /**
     * This method updated the board if there is/are lines to remove
     *
     * @return <code>DeletedRowInfo</code> that holds the information of the deleted row
     */
    public DeletedRowInfo checkRemovingBlocks() {
        int toRemoveCount = 0;
        int[][] updated = new int[tetrisBoard.length][tetrisBoard[0].length];

        Deque<int[]> newRows = new ArrayDeque<>();

        for (int[] ints : tetrisBoard) {
            // check if array does not contain zeros which implies it is full
            if (fullLine(ints)) {
                toRemoveCount++;
            } else {
                newRows.add(ints);
            }
        }

        for (int i = tetrisBoard.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row == null) {
                break;
            }
            updated[i] = row;
        }

        int scoreBonus = 50 * toRemoveCount;

        tetrisBoard = updated;

        return new DeletedRowInfo(toRemoveCount, scoreBonus);
    }

    /**
     * This method moves the tetromino down by changing its position
     *
     * @return true if the tetromino can move down without conflicts, false otherwise
     */
    public boolean moveTetrominoDown() {
        Position currentPos = currentTetromino.getPosition();

        Position newPos = new Position(currentPos.getXPos(), currentPos.getYPos() + 1);
        currentTetromino.setTetrominoPosition(newPos);

        boolean anyConflict = outOfBoardBorder(currentTetromino.getTetromino(), newPos  );

        if (anyConflict) {
            return false;
        } else {
            currentTetromino.setTetrominoPosition(newPos);
            return true;
        }
    }

    /**
     * This method moves the tetromino left by changing its position
     */
    public void moveTetrominoLeft() {
        Position currentPos = currentTetromino.getPosition();

        Position newPos = new Position(currentPos.getXPos() - 1, currentPos.getYPos());

        if (!outOfBoardBorder(currentTetromino.getTetromino(), newPos)) {
            currentTetromino.setTetrominoPosition(newPos);
        }
    }

    /**
     * This method moves the tetromino right by changing its position
     */
    public void moveTetrominoRight() {
        Position currentPos = currentTetromino.getPosition();

        Position newPos = new Position(currentPos.getXPos() + 1, currentPos.getYPos());

        if (!outOfBoardBorder(currentTetromino.getTetromino(), newPos)) {
            currentTetromino.setTetrominoPosition(newPos);
        }

    }

    /**
     * This method rotates the tetromino by changing its shape
     *
     * Since every tetromino has its own list of representations, this method changes the shape index
     * to get the next rotated version
     */
    public void rotateTetromino() {
        int currentShape = currentTetromino.getShape();
        currentShape = (currentShape + 1) % currentTetromino.getTetrominoRepresentation().size();

        int[][] updatedShape = currentTetromino.getTetrominoRepresentation().get(currentShape);

        if (!outOfBoardBorder(updatedShape, currentTetromino.getPosition())) {
            currentTetromino.setShape(currentShape);
        }
    }
}
