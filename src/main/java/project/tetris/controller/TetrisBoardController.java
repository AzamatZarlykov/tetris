package project.tetris.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;
import project.tetris.Main;
import project.tetris.controller.events.KeyboardEventListener;
import project.tetris.model.board.Board;
import project.tetris.model.board.DeletedRowInfo;
import project.tetris.model.board.UpdatedBlockInfo;
import project.tetris.model.helper.ScoreUpdateNotification;
import project.tetris.model.tetromino.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
    @FXML
    private GridPane nextTetromino;
    @FXML
    private GridPane savedTetromino;
    @FXML
    private Label scoreDisplay;
    @FXML
    private TextField username;
    @FXML
    private Label scoreText;
    @FXML
    private Label nextText;
    @FXML
    private BorderPane nextBorder;
    @FXML
    private Label saveText;
    @FXML
    private BorderPane saveBorder;
    @FXML
    private Button menuBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button leaveBtn;


    public void displayGameOver() {
        timeline.stop();
        gameOverLabel.setVisible(true);
        isGameOver = true;
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

        displayNextTetromino(updated);
        displaySavedTetromino(updated);
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
                    gameGrid.setFocusTraversable(true);
                    gameGrid.requestFocus();

                    UpdatedBlockInfo updatedInfo = eventListener.onDownEvent(false);
                    refreshTetrominoPosition(updatedInfo.getTetrominoInformation());
                    displayScoreNotification(updatedInfo.getDeletedRowInfo());
                })));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setInputEvents() {
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
                if (event.getCode() == KeyCode.SPACE) {
                    // exchange the saved tetromino with the currently falling one
                    refreshTetrominoPosition(eventListener.onExchangeEvent());
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
        pauseButton.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                timeline.pause();
                pauseButton.setText("Resume");
            } else {
                timeline.play();
                pauseButton.setText("Pause");
            }
        });
    }

    private void displayNextTetromino(TetrominoInformation tetrominoInfo) {
        int offset = 4;

        Tetromino tetromino = tetrominoInfo.getNext();
        int[][] next = tetromino.getStructure();

        nextTetromino.getChildren().clear();

        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next[0].length; j++) {


                Rectangle r = new Rectangle(Board.BRICK_SIZE - offset,
                        Board.BRICK_SIZE - offset);
                drawTetromino(next[i][j], r);

                if (next[i][j] != 0) {
                    nextTetromino.add(r, j, i);
                }
            }
        }
    }

    private void displaySavedTetromino(TetrominoInformation tetrominoInfo) {
        int offset = 4;

        Tetromino tetromino = tetrominoInfo.getSaved();
        int[][] saved = tetromino.getStructure();

        savedTetromino.getChildren().clear();

        for (int i = 0; i < saved.length; i++) {
            for (int j = 0; j < saved[0].length; j++) {

                Rectangle r = new Rectangle(Board.BRICK_SIZE - offset,
                        Board.BRICK_SIZE - offset);
                drawTetromino(saved[i][j], r);

                if (saved[i][j] != 0) {
                    savedTetromino.add(r, j, i);
                }
            }
        }
    }

    // entry
    public void run(int[][] tetrisBoard, TetrominoInformation tetrominoInfo) {

        allTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];
        fallingTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];

        setInputEvents();

        bindPausedButton();

        instantiateBoardGrid(tetrisBoard);

        displayTetrominoShape(tetrominoInfo);

        displayNextTetromino(tetrominoInfo);

        displaySavedTetromino(tetrominoInfo);

        setGameLoop();
    }

    public void setEventListener(KeyboardEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty currentScore) {
        scoreValue.textProperty().bind(currentScore.asString());
    }

    private void removeGamePlay() {
        if (!isGameOver) {
            displayGameOver();
        }

        gameGrid.getChildren().clear();
        tetrominoPanel.getChildren().clear();
        gameOverLabel.setLayoutY(0);

        scoreText.setVisible(false);
        scoreValue.setVisible(false);
        nextText.setVisible(false);
        nextBorder.setVisible(false);
        saveText.setVisible(false);
        saveBorder.setVisible(false);
        pauseButton.setVisible(false);
        menuBtn.setVisible(false);
    }

    private void addTextLimiter(int maxLength) {
        username.textProperty().addListener((ov, oldValue, newValue) -> {
            if (username.getText().length() > maxLength) {
                String s = username.getText().substring(0, maxLength);
                username.setText(s);
            }
        });
    }

    public void onMenuBtnClick(ActionEvent actionEvent) throws IOException {
        removeGamePlay();
        username.setVisible(true);

        scoreDisplay.setVisible(true);
        username.setVisible(true);
        saveBtn.setVisible(true);
        leaveBtn.setVisible(true);

        scoreDisplay.setText(scoreDisplay.getText() + scoreValue.getText());

        addTextLimiter(15);
    }

    private void moveToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }



    public void onSaveBtnClick(ActionEvent actionEvent) throws IOException {
        if (username.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String content = "Please fill your username";
            alert.setContentText(content);
            alert.showAndWait();
        } else {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("leaderboard/scores.txt", true)
            );

            String uname = username.getText();
            String score = scoreValue.getText();

            bw.write(uname + ": " + score + "\n");
            bw.close();

            moveToMainMenu(actionEvent);
        }
    }

    public void onLeaveBtnClick(ActionEvent actionEvent) throws IOException {
        moveToMainMenu(actionEvent);
    }
}
