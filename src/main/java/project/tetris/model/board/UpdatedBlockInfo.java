package project.tetris.model.board;

import project.tetris.model.tetromino.TetrominoInformation;

public record UpdatedBlockInfo(TetrominoInformation tetrominoInformation, DeletedRowInfo deletedRowInfo) {
    public TetrominoInformation getTetrominoInformation() {
        return tetrominoInformation;
    }

    public DeletedRowInfo getDeletedRowInfo() {
        return deletedRowInfo;
    }
}
