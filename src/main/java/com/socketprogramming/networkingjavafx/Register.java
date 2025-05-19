package com.socketprogramming.networkingjavafx;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Register extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JavafxStageManager.openNewWindow(stage, "RegisterFormView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}