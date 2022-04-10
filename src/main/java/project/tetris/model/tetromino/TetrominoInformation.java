package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.Tetromino;

import java.util.List;

public class TetrominoInformation {
    private final Tetromino tetromino;
    private Position position;
    private int currentShape;
    private final Tetromino next;

    public TetrominoInformation(Tetromino tetromino, int currentShape, int x, int y, Tetromino next) {
        this.tetromino = tetromino;
        this.currentShape = currentShape;
        this.position = new Position(x, y);
        this.next = next;
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

    public void setShape(int val) {
        currentShape = val;
    }
}
