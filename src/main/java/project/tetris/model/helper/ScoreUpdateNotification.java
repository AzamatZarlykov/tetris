package project.tetris.model.helper;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class generates the extra point notification.
 *
 * @author Azamat Zarlykov
 */
public class ScoreUpdateNotification extends BorderPane {

    /**
     * This method creates a fxml element <code>Label</code> to display score
     *
     * @param scoreText points scored represented as <code>String</code>
     */
    public ScoreUpdateNotification(String scoreText) {
        setMinHeight(300);
        setMinWidth(300);
        Label score = new Label(scoreText);
        score.getStyleClass().add("scoreStyle");
        Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);

        setCenter(score);
    }

    /**
     * This method displays the score
     * <p>
     * In this method, we use transition class to add some effect when displaying the score
     * @param list children of <code>Group</code> fxml element
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(event -> list.remove(ScoreUpdateNotification.this));
        ft.play();
    }
}
