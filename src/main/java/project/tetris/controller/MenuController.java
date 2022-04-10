package project.tetris.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.tetris.Main;

import java.io.IOException;

public class MenuController {
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
    public void onQuitButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onLeaderBoardBtnClick(ActionEvent actionEvent) {
    }

    private void toggleVisibility(boolean display) {
        gameNameLabel.setVisible(display);
        startBtn.setVisible(display);
        leaderBoardBtn.setVisible(display);
        instructionBtn.setVisible(display);
        quitBtn.setVisible(display);

        menu.setVisible(!display);
    }

    @FXML
    public void onInstructionButtonClick(ActionEvent actionEvent) {
        toggleVisibility(false);


    }

    @FXML
    private void onMenuButtonClick(ActionEvent actionEvent) {
        toggleVisibility(true);

    }
}
