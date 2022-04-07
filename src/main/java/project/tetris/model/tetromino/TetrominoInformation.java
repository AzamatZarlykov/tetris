package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;

public class TetrominoInformation {
    private Tetromino tetromino;
    private Position position;
    private int currentShape;

    public TetrominoInformation(Tetromino tetromino, int currentShape, int x, int y) {
        this.tetromino = tetromino;
        this.currentShape = currentShape;
        this.position = new Position(x, y);
    }

    public void setTetrominoPosition(Position newPos) {
        position = newPos;
    }

    public int[][] getTetromino() {
        return tetromino.getTetrominoRepresentation().get(currentShape);
    }


    public Position getPosition() {
        return position;
    }
}
