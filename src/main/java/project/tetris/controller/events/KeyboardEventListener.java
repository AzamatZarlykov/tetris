package project.tetris.controller.events;

import project.tetris.model.board.UpdatedBlockInfo;
import project.tetris.model.tetromino.TetrominoInformation;

public interface KeyboardEventListener {
    UpdatedBlockInfo onDownEvent(boolean userInput);

    TetrominoInformation onLeftEvent();

    TetrominoInformation onRightEvent();

    TetrominoInformation onRotateEvent();
}
