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

public class ScoreUpdateNotification extends BorderPane {
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

    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(event -> list.remove(ScoreUpdateNotification.this));
        ft.play();
    }
}
