package project.tetris.controller;

import project.tetris.controller.events.KeyboardEventListener;
import project.tetris.model.board.Board;
import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.TetrominoGenerator;
import project.tetris.model.tetromino.TetrominoInformation;

// Controller of the game
public class GameController implements KeyboardEventListener {
    private final Board board;  // board model
    private final TetrominoGenerator generator;
    private final TetrisBoardController view;   // view

    public GameController(TetrisBoardController view) {
        this.board = new Board();

        this.generator = new TetrominoGenerator();
        TetrominoInformation tetrominoInformation = generator.getCurrentTetrominoInfo();

        this.board.setCurrentTetromino(tetrominoInformation);

        this.view = view;
        this.view.setEventListener(this);
        this.view.bindScore(board.getScore());
        this.view.run(board.getTetrisBoard(), generator.getCurrentTetrominoInfo());
    }


    @Override
    public TetrominoInformation onDownEvent() {
        boolean allowedMove = board.moveTetrominoDown();

        if (!allowedMove) {
            board.mergeBrickToBackground();
            generator.generateNewTetromino();
            board.setCurrentTetromino(generator.getCurrentTetrominoInfo());
        } else {

        }

        this.view.refreshGameBackground(board.getTetrisBoard());
        return generator.getCurrentTetrominoInfo();
    }
}
