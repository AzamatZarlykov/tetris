package project.tetris.controller.events;

import project.tetris.model.tetromino.TetrominoInformation;

public interface KeyboardEventListener {
    TetrominoInformation onDownEvent(boolean userInput);

    TetrominoInformation onLeftEvent();

    TetrominoInformation onRightEvent();

    TetrominoInformation onRotateEvent();
}
