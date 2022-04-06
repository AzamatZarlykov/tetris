package project.tetris.controller;

import project.tetris.model.board.Board;
import project.tetris.model.helper.Position;
import project.tetris.model.tetromino.TetrominoGenerator;

// Controller of the game
public class GameController {
    private final Board board;  // board model
    private final TetrominoGenerator generator;
    private final TetrisBoardController view;   // view

    public GameController(TetrisBoardController view) {
        this.board = new Board();
        this.generator = new TetrominoGenerator();
        this.view = view;

        this.view.run(board.getTetrisBoard(), generator.getCurrentTetrominoInfo());
    }


}
