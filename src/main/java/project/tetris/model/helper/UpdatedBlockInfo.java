package project.tetris.model.helper;

import project.tetris.model.board.DeletedRowInfo;

public record UpdatedBlockInfo(TetrominoInformation tetrominoInformation, DeletedRowInfo deletedRowInfo) {
    public TetrominoInformation getTetrominoInformation() {
        return tetrominoInformation;
    }

    public DeletedRowInfo getDeletedRowInfo() {
        return deletedRowInfo;
    }
}
