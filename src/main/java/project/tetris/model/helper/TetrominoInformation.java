package project.tetris.model.helper;

import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.Tetromino;

import java.util.List;

public class TetrominoInformation {
    private final Tetromino tetromino;
    private Position position;
    private int currentShape;
    private final int[][] next;

    public TetrominoInformation(Tetromino tetromino, int currentShape, int x, int y, int[][] next) {
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

    public int[][] getNext() { return next; }

    public void setShape(int val) {
        currentShape = val;
    }
}
