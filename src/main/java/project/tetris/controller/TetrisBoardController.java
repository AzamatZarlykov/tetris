package project.tetris.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import project.tetris.model.tetromino.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is responsible for all the events happening on the <code>tetris.fxml</code>
 *
 * Besides, to display blocks on the grid, it instantiates grid elements from fxml element, starts the keyframe to
 * refresh the board
 */
public class TetrisBoardController {
    /**
     * <code>Timeline</code> to set cycles of the game. Used to refresh the tetris board
     */
    Timeline timeline;
    /**
     * representation of the board with rectangles. Contains empty and filled rectangles
     */
    private Rectangle[][] allTetromino;
    /**
     * currently falling tetromino representation
     */
    private Rectangle[][] fallingTetromino;
    /**
     * interface to call certain actions based on keyboard actions
     */
    private KeyboardEventListener eventListener;

    /**
     * indicate if game is over
     */
    private boolean isGameOver;

    /**
     * binded property to a pause button element
     */
    private final BooleanProperty paused = new SimpleBooleanProperty();

    /**
     * panel that represents the game grid
     */
    @FXML
    private GridPane gameGrid;
    /**
     * panel that holds the tetromino shape
     */
    @FXML
    public GridPane tetrominoPanel;
    /**
     * Label that displays the score
     */
    @FXML
    public Label scoreValue;
    /**
     * displays score on top
     */
    @FXML
    public Group notification;
    /**
     * button that toggles from paused/resume
     */
    @FXML
    private ToggleButton pauseButton;
    /**
     * Game over label to display at the end of the game
     */
    @FXML
    private Label gameOverLabel;
    /**
     * Panel that displays the next tetromino
     */
    @FXML
    private GridPane nextTetromino;
    /**
     * Panel that displays the saved tetromino
     */
    @FXML
    private GridPane savedTetromino;
    /**
     * Label that shows the current score of the player
     */
    @FXML
    private Label scoreDisplay;
    /**
     * text filed for a user to input non-empty username
     */
    @FXML
    private TextField username;
    /**
     * Label that displays players score while writing username
     */
    @FXML
    private Label scoreText;
    /**
     * Label for the next tetromino box
     */
    @FXML
    private Label nextText;
    /**
     * Border for the next tetromino to display
     */
    @FXML
    private BorderPane nextBorder;
    /**
     * Label for the saved tetromino box
     */
    @FXML
    private Label saveText;
    /**
     * Border for the saved tetromino to display
     */
    @FXML
    private BorderPane saveBorder;
    /**
     * button to leave the game
     */
    @FXML
    private Button menuBtn;
    /**
     * button that saves the username and score
     */
    @FXML
    private Button saveBtn;
    /**
     * leaves the game without saving username and score
     */
    @FXML
    private Button leaveBtn;


    /**
     * The call to this function stops the keyframe update and displays the game over label
     */
    public void displayGameOver() {
        timeline.stop();
        gameOverLabel.setVisible(true);
        isGameOver = true;
    }

    /**
     * This method creates the grid on the board
     *
     * @param tetrisBoard representation of the tetris board
     */
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

    /**
     * Given the number of the block the function returns its corresponding color
     *
     * @param i number the tetromino block is represented with
     * @return Paint that represents the color of the tetromino block
     */
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

    /**
     * Given updated information, this method sets the positions relative to the panel
     * @param tetrominoInfo current tetromino information
     */
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

    /**
     * Displays the currently falling tetromino on the panel
     *
     * @param tetrominoInfo current tetromino information
     */
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

    /**
     * Displays the tetromino on the board by coloring its rectangle/block
     *
     * @param color color of the tetromino to be passed
     * @param rect the particular block of the tetromino to be colored
     */
    private void drawTetromino(int color, Rectangle rect) {
        rect.setFill(getColor(color));
        rect.setArcHeight(9);
        rect.setArcWidth(9);
    }

    /**
     * This method scans through the board and displays already located and falling tetrominos
     *
     * @param board board representation with all its tetrominos
     */
    public void refreshGameBackground(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                drawTetromino(board[row][col], allTetromino[row][col]);
            }
        }
    }

    /**
     * This method is called when the game needs to update the current, next and saved tetromino
     *
     * It sets current tetromino on the panel and displays it on the board just like next and saved
     * @param updated Tetromino information with updated data
     */
    private void refreshTetrominoPosition(TetrominoInformation updated) {
        setTetrominoPositionOnBoard(updated);

        int[][] tetromino = updated.getTetromino();
        for (int i = 0; i < tetromino.length; i++) {
            for (int j = 0; j < tetromino[0].length; j++) {
                drawTetromino(tetromino[i][j], fallingTetromino[i][j]);
            }
        }

        displayNextTetromino(updated.getNext());
        displaySavedTetromino(updated.getSaved());
    }

    /**
     * This method displays extra points that player scored on the board
     *
     * @param deletedRowInfo provides information on obtained score and number of lines removed
     */
    private void displayScoreNotification(DeletedRowInfo deletedRowInfo) {
        if (deletedRowInfo != null && deletedRowInfo.getRowCount() > 0) {
            ScoreUpdateNotification n = new ScoreUpdateNotification(
                    " + " + deletedRowInfo.getTotalScore()
            );
            notification.getChildren().add(n);
            n.showScore(notification.getChildren());
        }

    }

    /**
     * This method sets the cycle of the game for every 400 milliseconds
     *
     * Every cycle, game updates. It calls set of methods that refresh the game
     */
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

    /**
     * This method checks every keyboard press and calls the corresponding functions
     */
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

    /**
     * Binds the <code>paused</code> to a toggle button <code>pauseButton</code>.
     *
     * Additionally, it changes the message of the text based on the state of the button
     */
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

    /**
     * This method displays the next tetromino on its panel inside the border box
     *
     * @param n next tetromino to be passed
     */
    private void displayNextTetromino(Tetromino n) {
        int offset = 4;

        int[][] next = n.getStructure();

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

    /**
     * This method displays the saved tetromino on its panel inside the border box
     *
     * @param s saved tetromino to be passed
     */
    private void displaySavedTetromino(Tetromino s) {
        int offset = 4;

        int[][] saved = s.getStructure();

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

    /**
     * This method is responsible for all the part of the game: Setting board, keyboard inputs, displaying, etc.
     *
     * It is a main entry since it is called from <code>GameController</code> class to start the game
     *
     * @param tetrisBoard tetris board representation of the game
     * @param tetrominoInfo current tetromino data to be passed
     */
    // entry
    public void run(int[][] tetrisBoard, TetrominoInformation tetrominoInfo) {

        allTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];
        fallingTetromino = new Rectangle[tetrisBoard.length][tetrisBoard[0].length];

        setInputEvents();

        bindPausedButton();

        instantiateBoardGrid(tetrisBoard);

        displayTetrominoShape(tetrominoInfo);

        displayNextTetromino(tetrominoInfo.getNext());

        displaySavedTetromino(tetrominoInfo.getSaved());

        setGameLoop();
    }

    /**
     * @param eventListener keyboard event listener to be passed
     */
    public void setEventListener(KeyboardEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * @param currentScore current score to bind with the label that displays the score
     */
    public void bindScore(IntegerProperty currentScore) {
        scoreValue.textProperty().bind(currentScore.asString());
    }

    /**
     * This method removes the fxml elements of the game at the end of the game
     */
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

    /**
     * This method sets the limit to a text field for a username input
     *
     * @param maxLength max length to set on the text field
     */
    private void addTextLimiter(int maxLength) {
        username.textProperty().addListener((ov, oldValue, newValue) -> {
            if (username.getText().length() > maxLength) {
                String s = username.getText().substring(0, maxLength);
                username.setText(s);
            }
        });
    }

    /**
     * When menu button is clicked, this method sets the visibility to certain elements
     */
    public void onMenuBtnClick() {
        removeGamePlay();
        username.setVisible(true);

        scoreDisplay.setVisible(true);
        username.setVisible(true);
        saveBtn.setVisible(true);
        leaveBtn.setVisible(true);

        scoreDisplay.setText(scoreDisplay.getText() + scoreValue.getText());

        addTextLimiter(15);
    }

    /**
     * This method saves the username and their score to a txt file
     *
     * It checks if the username is not empty
     *
     * @param actionEvent action event to be passed
     * @throws IOException If I/O exception occurred
     */
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

            String uname = username.getText().trim();
            String score = scoreValue.getText();

            bw.write(uname + ": " + score + "\n");
            bw.close();

            moveToMainMenu(actionEvent);
        }
    }

    /**
     * If user does not want to save their score, this method is called when leave button is clicked
     *
     * @param actionEvent action event to be passed
     * @throws IOException If I/O exception occurred
     */
    public void onLeaveBtnClick(ActionEvent actionEvent) throws IOException {
        moveToMainMenu(actionEvent);
    }

    /**
     * Changes the scene to a main menu by obtaining its fxml file
     *
     * @param event action even that is caused from clicking the button
     * @throws IOException If I/O exception occurred
     */
    private void moveToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }
}
