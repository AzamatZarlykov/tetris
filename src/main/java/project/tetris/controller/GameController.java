package project.tetris.controller;

import project.tetris.model.board.Board;

// Controller of the game
public class GameController {
    private final Board board;  // board model
    private final TetrisBoardController view;   // view

    public GameController(TetrisBoardController view) {
        this.board = new Board();
        this.view = view;

        this.view.run(board);
    }


}
