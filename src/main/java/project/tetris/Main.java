package project.tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Starting point of the project as it launches the main menu of the game
 *
 * @author Azamat Zarlykov
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tetris");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Our main method that is run when the program is launched
     *
     * @param args arguments that are passed from the command-line/configuration
     */
    public static void main(String[] args) {
        launch();
    }
}