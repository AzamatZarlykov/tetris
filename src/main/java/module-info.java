/**
 * Module for tetris game
 */
module com.example.tetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens project.tetris to javafx.fxml;
    exports project.tetris;

    opens project.tetris.controller to javafx.fxml;
    exports project.tetris.controller.events;
    opens project.tetris.controller.events to javafx.fxml;
    exports project.tetris.controller;

    opens project.tetris.model.menu to javafx.base;
    exports project.tetris.model.tetromino to javafx.base;
    exports project.tetris.model.helper to javafx.base;
    exports project.tetris.model.board to javafx.base;


}