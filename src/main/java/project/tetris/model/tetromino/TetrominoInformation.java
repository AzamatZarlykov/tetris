package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.Tetromino;

import java.util.List;

public class TetrominoInformation {
    private final Tetromino tetromino;
    private Position position;
    private int currentShape;

    private Tetromino next;
    private Tetromino saved;

    public TetrominoInformation(Tetromino tetromino, int currentShape, int x, int y, Tetromino next, Tetromino saved) {
        this.tetromino = tetromino;
        this.currentShape = currentShape;
        this.position = new Position(x, y);
        this.next = next;
        this.saved = saved;
    }

    public List<int[][]> getTetrominoRepresentation() {
        return tetromino.getTetrominoRepresentation();
    }

    public int[][] getTetromino() {
        return tetromino.getTetrominoRepresentation().get(currentShape);
    }

    public Position getPosition() {
        return position;
    }

    public void setTetrominoPosition(Position newPos) {
        position = newPos;
    }

    public int getShape() {
        return currentShape;
    }

    public Tetromino getNext() { return next; }

    public Tetromino getSaved() { return saved; }

    public void setShape(int val) {
        currentShape = val;
    }

    public void setNext(Tetromino n) { next = n; }

    public void setSaved(Tetromino s) {
        saved = s;
    }
}
