package project.tetris.model.board;

import project.tetris.model.tetromino.TetrominoInformation;

/**
 * Record that holds both information about deleted rows and current tetromino
 *
 * @author Azamat Zarlykov
 */
public record UpdatedBlockInfo(TetrominoInformation tetrominoInformation, DeletedRowInfo deletedRowInfo) {
    /**
     * @return current tetromino information
     */
    public TetrominoInformation getTetrominoInformation() {
        return tetrominoInformation;
    }

    /**
     * @return deleted row information
     */
    public DeletedRowInfo getDeletedRowInfo() {
        return deletedRowInfo;
    }
}
