package project.tetris.controller.events;

import project.tetris.model.helper.UpdatedBlockInfo;
import project.tetris.model.helper.TetrominoInformation;

public interface KeyboardEventListener {
    UpdatedBlockInfo onDownEvent(boolean userInput);

    TetrominoInformation onLeftEvent();

    TetrominoInformation onRightEvent();

    TetrominoInformation onRotateEvent();
}
