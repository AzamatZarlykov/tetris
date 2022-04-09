package project.tetris.controller;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.tetris.Main;
import project.tetris.controller.events.KeyboardEventListener;
import project.tetris.model.board.Board;
import project.tetris.model.board.DeletedRowInfo;
import project.tetris.model.board.UpdatedBlockInfo;
import project.tetris.model.helper.ScoreUpdateNotification;
import project.tetris.model.tetromino.TetrominoInformation;

import java.io.IOException;

public class TetrisBoardController {
    // store all the tetromino
    Timeline timeline;
    private Rectangle[][] allTetromino;
    private Rectangle[][] fallingTetromino;
    private KeyboardEventListener eventListener;
    private boolean isGameOver;
    private final BooleanProperty paused = new SimpleBooleanProperty();
    @FXML
    private GridPane gameGrid;
    @FXML
    public GridPane tetrominoPanel; // panel to display the tetromino shape
    @FXML
    public Label scoreValue;
    @FXML
    public Group notification;
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private Label gameOverLabel;

    public void displayGameOver() {
        timeline.stop();
        gameOverLabel.setVisible(true);
    }

    // create the grid on the board
    private void instantiateBoardGrid(int[][] tetrisBoard) {
        for (int row = 0; row < tetrisBoard.length; row++ ){
            for (int col = 0; col < tetrisBoard[row].length; col++) {
                Rectangle rect = new Rectangle(Board.BRICK_SIZE, Board.BRICK_SIZE);
                rect.setFill(Color.TRANSPARENT);
                allTetromino[row][col] = rect;
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
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[0].length; j++) {
                Rectangle rect = new Rectangle(Board.BRICK_SIZE, Board.BRICK_SIZE);
                rect.setFill(getColor(tetromino[i][j]));
                fallingTetromino[i][j] = rect;
                tetrominoPanel.add(rect, j, i);
            }
        }

        setTetrominoPositionOnBoard(tetrominoInfo);
    }

    private void drawTetromino(int color, Rectangle rect) {
        rect.setFill(getColor(color));
        rect.setArcHeight(9);
        rect.setArcWidth(9);
    }

    public void refreshGameBackground(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                drawTetromino(board[row][col], allTetromino[row][col]);
            }
        }
    }

    private void refreshTetrominoPosition(TetrominoInformation updated) {
        setTetrominoPositionOnBoard(updated);

        int[][] tetromino = updated.getTetromino();
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[0].length; j++) {
                drawTetromino(tetromino[i][j], fallingTetromino[i][j]);
            }
        }
    }

    private void displayScoreNotification(DeletedRowInfo deletedRowInfo) {
        if (deletedRowInfo != null && deletedRowInfo.getRowCount() > 0) {
            ScoreUpdateNotification n = new ScoreUpdateNotification(
                    " + " + deletedRowInfo.getTotalScore()
            );
            notification.getChildren().add(n);
            n.showScore(notification.getChildren());
        }

    }

    private void setGameLoop() {
        // every 400 millis update the board
        timeline = new Timeline((new KeyFrame(Duration.millis(400),
                keyframe -> {
                    UpdatedBlockInfo updatedInfo = eventListener.onDownEvent(false);
                    refreshTetrominoPosition(updatedInfo.getTetrominoInformation());
                    displayScoreNotification(updatedInfo.getDeletedRowInfo());
                })));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setInputEvents() {
        gameGrid.setFocusTraversable(true);
        gameGrid.requestFocus();
        gameGrid.setOnKeyPressed(event -> {
            if (paused.getValue() == Boolean.FALSE && !isGameOver) {
                if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    UpdatedBlockInfo update = eventListener.onDownEvent(true);
                    refreshTetrominoPosition(update.getTetrominoInformation());
                    displayScoreNotification(update.getDeletedRowInfo());
                    event.consume();
                }
                if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    refreshTetrominoPosition(eventListener.onLeftEvent());
                    event.consume();
                }
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    refreshTetrominoPosition(eventListener.onRightEvent());
                    event.consume();
                }
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    refreshTetrominoPosition(eventListener.onRotateEvent());
                    event.consume();
                }
            }
            if (event.getCode() == KeyCode.P) {
                pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
            }
        });
    }

    private void bindPausedButton(){
        pauseButton.selectedProperty().bindBidirectional(paused);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {
                    timeline.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeline.play();
                    pauseButton.setText("Pause");
                }
            }
        });
    }

    public void run(int[][] tetrisBoard, TetrominoInformation tetrominoInfo) {
        allTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];
        fallingTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];

        instantiateBoardGrid(tetrisBoard);

        displayTetrominoShape(tetrominoInfo);

        setGameLoop();

        setInputEvents();

        bindPausedButton();
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
