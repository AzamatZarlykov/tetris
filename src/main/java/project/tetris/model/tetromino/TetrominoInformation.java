package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;

import java.util.List;

/**
 * Holds the information of the current tetromino on the board
 *
 * @author Azamat Zarlykov
 */
public class TetrominoInformation {
    /**
     * current tetromino form
     */
    private final Tetromino tetromino;
    /**
     * current tetromino position
     */
    private Position position;
    /**
     * current tetromino shape
     */
    private int currentShape;

    /**
     * next tetromino form
     */
    private Tetromino next;
    /**
     * saved tetromino form
     */
    private Tetromino saved;

    /**
     * @param tetromino passed generated tetromino form
     * @param currentShape passed shape of the tetromino
     * @param x x position on the board
     * @param y y position on the board
     * @param next next tetromino to be displayed on the board
     * @param saved saved tetromino to be displayed on the board
     */
    public TetrominoInformation(Tetromino tetromino, int currentShape, int x, int y, Tetromino next, Tetromino saved) {
        this.tetromino = tetromino;
        this.currentShape = currentShape;
        this.position = new Position(x, y);
        this.next = next;
        this.saved = saved;
    }

    /**
     * @return all possible representations of the current tetromino
     */
    public List<int[][]> getTetrominoRepresentation() {
        return tetromino.getTetrominoRepresentation();
    }

    /**
     * @return  representation of the current tetromino and its shape
     */
    public int[][] getTetromino() {
        return tetromino.getTetrominoRepresentation().get(currentShape);
    }

    /**
     * @return current position of the tetromino
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param newPos new position of the tetromino within the bounds of the board
     */
    public void setTetrominoPosition(Position newPos) {
        position = newPos;
    }

    /**
     * @return shape of the current tetromino
     */
    public int getShape() {
        return currentShape;
    }

    /**
     * @return next tetromino
     */
    public Tetromino getNext() { return next; }

    /**
     * @return saved tetromino
     */
    public Tetromino getSaved() { return saved; }

    /**
     * @param val shape value that fits the possible shape tetromino can have
     */
    public void setShape(int val) {
        currentShape = val;
    }

    /**
     * @param n setting next generated tetromino
     */
    public void setNext(Tetromino n) { next = n; }

    /**
     * @param s setting saved generated tetromino
     */
    public void setSaved(Tetromino s) {
        saved = s;
    }
}
