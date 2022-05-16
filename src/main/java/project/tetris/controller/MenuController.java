package project.tetris.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.tetris.Main;
import project.tetris.model.menu.Highscore;
import project.tetris.model.menu.InfoType;
import project.tetris.model.menu.User;

import java.io.*;
import java.util.*;


/**
 * The class MenuController is responsible for maintaining the functionality of Menu fxml
 * <p>
 * It is responsible for starting the game, displaying leaderboard, providing instructions
 * and quitting the game
 *
 * @author Azamat Zarlykov
 */
public class MenuController {
    /**
     * table instance from the fxml
     */
    @FXML
    private TableView<Highscore> table;
    /**
     * column instance <code>id</code> from the fxml
     */
    @FXML
    private TableColumn<Highscore, Integer> id;
    /**
     * column instance <code>username</code> from the fxml
     */
    @FXML
    private TableColumn<Highscore, String> username;
    /**
     * column instance <code>score</code> from the fxml
     */
    @FXML
    private TableColumn<Highscore, Integer> score;
    /**
     * instruction <code>VBox</code> container instance from the fxml
     */
    @FXML
    private VBox help;
    /**
     * title of the game instance
     */
    @FXML
    private Label gameNameLabel;
    /**
     * start button instances from the fxml
     */
    @FXML
    private Button startBtn;
    /**
     * leaderboard button instances from the fxml
     */
    @FXML
    private Button leaderBoardBtn;
    /**
     * instructionBtn button instances from the fxml
     */
    @FXML
    private Button instructionBtn;
    /**
     * quitBtn button instances from the fxml
     */
    @FXML
    private Button quitBtn;
    /**
     * menu button instances from the fxml
     */
    @FXML
    private Button menu;

    /**
     * container that stores the every <code>HighScore</code> instance to the list to display
     * on the  table
     */
    private final ObservableList<Highscore> data = FXCollections.observableArrayList();


    /**
     * Current information role on the menu: Leaderboard, Instruction or None
     */
    private InfoType infoType;

    /**
     * Method instantiates the <code>GameController</code> that maintains the game
     *
     * @param loader fxml file that displays the game
     */
    private void instantiateGameController(FXMLLoader loader) {
        TetrisBoardController c = loader.getController();
        new GameController(c);
    }

    /**
     * Starts the tetris game when start button is clicked
     *
     * @param event button click event to be passed
     * @throws IOException If I/O exception occurred
     */
    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tetris.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

        instantiateGameController(fxmlLoader);
    }


    /**
     * Loads the score data, to display in the leaderboard section
     * <p>
     * This method displays first 10 best scores on the fxml table in a
     * decreasing order
     * @throws IOException If I/O exception occurred
     */
    private void loadData() throws IOException {
        int limit = 10;
        String leaderboardTxt = "leaderboard/scores.txt";

        String line;

        List<User> users = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(leaderboardTxt));

        while ((line = br.readLine()) != null) {
            if (line.length() == 0) {
                continue;
            }
            String[] val_line = line.split(":");
            String name = val_line[0];
            String score = val_line[1];

            users.add(new User(name.trim(), Integer.parseInt(score.trim())));
        }

        // sort users based on their scores
        users.sort(((o1, o2) -> o2.getScore() - o1.getScore()));

        for (int i = 1; i <= users.size() && i <= limit; i++) {
            User currentUser = users.get(i-1);
            data.add(new Highscore(currentUser.getUsername(), currentUser.getScore(),i));
        }
    }

    /**
     * Hides and shows elements based on the user request.
     *
     * @param display boolean that facilitates the toggling the visibility of elements
     */
    private void toggleVisibility(boolean display) {
        gameNameLabel.setVisible(display);
        startBtn.setVisible(display);
        leaderBoardBtn.setVisible(display);
        instructionBtn.setVisible(display);
        quitBtn.setVisible(display);

        menu.setVisible(!display);

        if (infoType == InfoType.HELP) {
            help.setVisible(!display);
        } else if (infoType == InfoType.SCOREBOARD) {
            table.setVisible(!display);
        } else if (infoType == InfoType.NONE) {
            table.setVisible(!display);
            help.setVisible(!display);
        }
    }

    /**
     * The button click shows the leaderboard of players.
     * @throws IOException If I/O exception occurred
     */
    @FXML
    public void onLeaderBoardBtnClick() throws IOException {
        table.getItems().clear();

        infoType = InfoType.SCOREBOARD;

        toggleVisibility(false);
        table.setVisible(true);

        loadData();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        //bind list into the table
        table.setItems(data);
    }


    /**
     * The button click displays the instruction/help page for the player
     */
    @FXML
    public void onInstructionButtonClick() {
        infoType = InfoType.HELP;
        toggleVisibility(false);
    }


    /**
     * The menu button, in leaderboard and instruction, returns the main menu scene
     */
    @FXML
    private void onMenuButtonClick() {
        infoType = InfoType.NONE;
        toggleVisibility(true);
    }

    /**
     * Closes the application when quit button is clicked
     */
    @FXML
    public void onQuitButtonClick() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }
}
