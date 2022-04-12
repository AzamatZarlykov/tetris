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
import javafx.stage.Stage;
import project.tetris.Main;
import project.tetris.model.menu.Highscore;
import project.tetris.model.menu.InfoType;
import project.tetris.model.menu.User;

import java.io.*;
import java.util.*;

public class MenuController {
    @FXML
    private TableView<Highscore> table;
    @FXML
    private TableColumn<Highscore, Integer> id;
    @FXML
    private TableColumn<Highscore, String> username;
    @FXML
    private TableColumn<Highscore, Integer> score;

    ObservableList<Highscore> data = FXCollections.observableArrayList();

    @FXML
    private Label gameNameLabel;
    @FXML
    private Button startBtn, leaderBoardBtn, quitBtn, instructionBtn, menu;

    private void instantiateGameController(FXMLLoader loader) {
        TetrisBoardController c = loader.getController();
        new GameController(c);
    }

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tetris.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

        instantiateGameController(fxmlLoader);
    }

    @FXML
    public void onQuitButtonClick() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

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
            String username = val_line[0];
            String score = val_line[1];

            users.add(new User(username.trim(), Integer.parseInt(score.trim())));
        }

        // sort users based on their scores
        users.sort(((o1, o2) -> o2.getScore() - o1.getScore()));

        for (int i = 1; i <= users.size() && i <= limit; i++) {
            data.add(new Highscore(users.get(i-1).getUsername(),users.get(i-1).getScore(),i));
        }
    }

    private void toggleVisibility(boolean display, InfoType type) {
        gameNameLabel.setVisible(display);
        startBtn.setVisible(display);
        leaderBoardBtn.setVisible(display);
        instructionBtn.setVisible(display);
        quitBtn.setVisible(display);

        menu.setVisible(!display);

        if (type == InfoType.HELP) {
            // instruction
        } else if (type == InfoType.SCOREBOARD) {
            table.setVisible(!display);
        } else if (type == InfoType.NONE) {
            table.setVisible(!display);
            // Instruction display
        }
    }

    @FXML
    public void onLeaderBoardBtnClick() throws IOException {
        toggleVisibility(false, InfoType.SCOREBOARD);
        table.setVisible(true);

        loadData();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        //bind list into the table
        table.setItems(data);
    }


    @FXML
    public void onInstructionButtonClick(ActionEvent actionEvent) {
        toggleVisibility(false, InfoType.HELP);


    }

    @FXML
    private void onMenuButtonClick(ActionEvent actionEvent) {
        toggleVisibility(true, InfoType.NONE);

    }
}
