
package project.tetris.controller;

import project.tetris.controller.events.KeyboardEventListener;
import project.tetris.model.board.Board;
import project.tetris.model.board.DeletedRowInfo;
import project.tetris.model.board.UpdatedBlockInfo;
import project.tetris.model.tetromino.Tetromino;
import project.tetris.model.tetromino.TetrominoGenerator;
import project.tetris.model.tetromino.TetrominoInformation;

/**
 * This class maintains is a bridge between the tetris game, board and event handlers
 *
 * It instantiates the board, defines the event handlers, generates tetrominos and calls certain methods from the
 * <code>TetrisBoardController</code>
 */
public class GameController implements KeyboardEventListener {
    /**
     * Board of the game
     */
    private final Board board;  // board model
    /**
     * Generator of tetrominos that is used to whenever next, saved or new tetromino is needed
     */
    private final TetrominoGenerator generator;
    /**
     * Tetris game view controller
     */
    private final TetrisBoardController view;   // view

    /**
     * @param view View controller of the game passed from menu controller
     */
    public GameController(TetrisBoardController view) {
        this.board = new Board();
        this.generator = new TetrominoGenerator();

        TetrominoInformation tetrominoInformation = generator.getCurrentTetrominoInfo();

        this.board.setCurrentTetromino(tetrominoInformation);

        this.view = view;
        this.view.setEventListener(this);
        this.view.bindScore(board.getScore());
        this.view.run(board.getTetrisBoard(), tetrominoInformation);
    }

    @Override
    public UpdatedBlockInfo onDownEvent(boolean userInput) {
        board.incrementScore(1);

        boolean allowedMove = board.moveTetrominoDown();
        DeletedRowInfo deletedRowInfo = null;

        if (!allowedMove) {
            board.mergeBrickToBackground();
            deletedRowInfo = board.checkRemovingBlocks();

            if (deletedRowInfo.getRowCount() > 0) {
                board.incrementScore(deletedRowInfo.getTotalScore());
            }

            generator.generateNewTetromino(generator.getCurrentTetrominoInfo().getSaved());
            TetrominoInformation generated = generator.getCurrentTetrominoInfo();
            if (board.outOfBoardBorder(generated.getTetromino(), generated.getPosition())) {
                // game over
                view.displayGameOver();
            } else {
                board.setCurrentTetromino(generated);
            }
        } else {
            // if user pressed down increment the score by 1
            if (userInput) {
                board.incrementScore(1);
            }
        }

        this.view.refreshGameBackground(board.getTetrisBoard());

        return new UpdatedBlockInfo(generator.getCurrentTetrominoInfo(), deletedRowInfo);
    }

    @Override
    public TetrominoInformation onLeftEvent() {
        board.moveTetrominoLeft();
        return generator.getCurrentTetrominoInfo();
    }

    @Override
    public TetrominoInformation onRightEvent() {
        board.moveTetrominoRight();
        return generator.getCurrentTetrominoInfo();
    }

    @Override
    public TetrominoInformation onRotateEvent() {
        board.rotateTetromino();
        return generator.getCurrentTetrominoInfo();
    }

    @Override
    public TetrominoInformation onExchangeEvent() {
        TetrominoInformation c = generator.getCurrentTetrominoInfo();

        Tetromino next = c.getNext();
        Tetromino saved = c.getSaved();

        c.setSaved(next);
        c.setNext(saved);
        generator.changeNext(saved);
        return generator.getCurrentTetrominoInfo();
    }
}
