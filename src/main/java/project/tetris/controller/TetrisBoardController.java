package project.tetris.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.tetris.Main;
import project.tetris.controller.events.KeyboardEventListener;
import project.tetris.model.board.Board;
import project.tetris.model.tetromino.Tetromino;
import project.tetris.model.tetromino.TetrominoInformation;

import java.io.IOException;

public class TetrisBoardController {
    private Timeline timeline;
    private Rectangle[][] tetrominoToDisplay;
    private KeyboardEventListener eventListener;
    @FXML
    private GridPane gameGrid;
    @FXML
    public GridPane tetrominoPanel; // panel to display the tetromino shape
    @FXML
    public Label scoreValue;

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

    // returns the color of the tetromino based on the number it has
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

    private void setTetrominoPositionOnBoard(TetrominoInformation tetrominoInfo) {
        // setting the x position
        tetrominoPanel.setLayoutX(
                gameGrid.getLayoutX() + tetrominoInfo.getPosition().getXPos() * Board.BRICK_SIZE +
                        tetrominoInfo.getPosition().getXPos()
        );

        // setting the y position
        tetrominoPanel.setLayoutY(
                -Board.BRICK_SIZE + gameGrid.getLayoutY() +
                        (tetrominoInfo.getPosition().getYPos() * Board.BRICK_SIZE) +
                        tetrominoInfo.getPosition().getYPos()
        );
    }

    // display the tetromino on the panel given the color
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

        setTetrominoPositionOnBoard(tetrominoInfo);
    }

    private void refreshTetrominoPosition(TetrominoInformation updated) {
        setTetrominoPositionOnBoard(updated);
    }


    public void run(int[][] tetrisBoard, TetrominoInformation tetrominoInfo) {
        instantiateBoardGrid(tetrisBoard);

        displayTetrominoShape(tetrominoInfo);

        // every 400 millis update the board
        timeline = new Timeline((new KeyFrame(Duration.millis(400),
                keyframe -> {
                    TetrominoInformation updatedInfo = eventListener.onDownEvent();
                    refreshTetrominoPosition(updatedInfo);
                })));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void setEventListener(KeyboardEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty currentScore) {
        scoreValue.textProperty().bind(currentScore.asString());
    }

    public void onMenuBtnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
