package project.tetris.model.tetromino;

public class SavedTetromino {
    private Tetromino tetromino;

    public SavedTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    public void setTetromino(Tetromino toExchangeTetromino) {
        tetromino = toExchangeTetromino;
    }

    public Tetromino getTetromino() { return tetromino; }
}
