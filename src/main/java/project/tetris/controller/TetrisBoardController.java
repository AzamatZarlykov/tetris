package project.tetris.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import project.tetris.Main;
import project.tetris.model.board.Board;
import project.tetris.model.tetromino.Tetromino;
import project.tetris.model.tetromino.TetrominoInformation;

import java.io.IOException;

public class TetrisBoardController {
    private Rectangle[][] tetrominoToDisplay;

    @FXML
    private GridPane gameGrid;
    @FXML
    public GridPane tetrominoPanel; // panel to display the tetromino shape

    // create the grid on the board
    private void instantiateBoardGrid(int[][] tetrisBoard) {
        for (int row = 0; row < tetrisBoard.length; row++ ){
            for (int col = 0; col < tetrisBoard[row].length; col++) {
                Rectangle rect = new Rectangle(Board.BRICK_SIZE, Board.BRICK_SIZE);
                rect.setFill(Color.TRANSPARENT);
                gameGrid.add(rect, col, row);
            }
        }
    }

    private Paint getColor(int i) {
        return switch(i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.YELLOW;
            case 3 -> Color.PURPLE;
            case 4 -> Color.GREEN;
            case 5 -> Color.RED;
            case 6 -> Color.BLUE;
            case 7 -> Color.ORANGE;
            default -> Color.WHITE;
        };
    }

    private void displayTetrominoShape(TetrominoInformation tetrominoInfo){
        int[][] tetromino = tetrominoInfo.getTetromino();
        tetrominoToDisplay = new Rectangle[tetromino.length][tetromino[0].length];

        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[0].length; j++) {
                Rectangle rect = new Rectangle(Board.BRICK_SIZE, Board.BRICK_SIZE);
                rect.setFill(getColor(tetromino[i][j]));
                tetrominoToDisplay[i][j] = rect;
                tetrominoPanel.add(rect, j, i);
            }
        }

        tetrominoPanel.setLayoutX(
                gameGrid.getLayoutX() + tetrominoInfo.getPosition().getXPos() * Board.BRICK_SIZE +
                        tetrominoInfo.getPosition().getXPos()
        );

        tetrominoPanel.setLayoutY(
                -Board.BRICK_SIZE + gameGrid.getLayoutY() +
                        (tetrominoInfo.getPosition().getYPos() * Board.BRICK_SIZE) +
                        tetrominoInfo.getPosition().getYPos()
        );
    }

    public void run(int[][] tetrisBoard, TetrominoInformation tetrominoInfo) {
        instantiateBoardGrid(tetrisBoard);
        displayTetrominoShape(tetrominoInfo);
    }

    public void onMenuBtnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
