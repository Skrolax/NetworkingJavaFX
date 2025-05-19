package com.socketprogramming.networkingjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class JavafxStageManager {

    public static void openNewWindow(Stage stage, String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavafxStageManager.class.getResource(resource));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Chat app");
        stage.show();
    }

    public static void changeScene(Stage stage, String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavafxStageManager.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}
