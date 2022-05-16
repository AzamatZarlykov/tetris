package project.tetris.controller.events;

import project.tetris.model.board.UpdatedBlockInfo;
import project.tetris.model.tetromino.TetrominoInformation;

/**
 * <code>KeyboardEventListener</code> interface contains the methods that are responsible for keyboard presses
 */
public interface KeyboardEventListener {
    /**
     * Event when down button is pressed
     * @param userInput boolean that indicates if user pressed or thread is moving
     * @return updated information about the tetromino
     */
    UpdatedBlockInfo onDownEvent(boolean userInput);

    /**
     * Event when left button is pressed
     * @return Tetromino information with updated position
     */
    TetrominoInformation onLeftEvent();
    /**
     * Event when right button is pressed
     * @return Tetromino information with updated position
     */
    TetrominoInformation onRightEvent();
    /**
     * Event when rotate button is pressed
     * @return Tetromino information with updated position
     */
    TetrominoInformation onRotateEvent();
    /**
     * Event to exchange the saved and the next tetromino
     * @return Tetromino information with updated next and saved tetrominos
     */
    TetrominoInformation onExchangeEvent();
}
