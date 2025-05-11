package com.socketprogramming.networkingjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormController {

    static User user;

    @FXML
    TextField usernameRegisterField;

    @FXML
    TextField passwordRegisterField;

    @FXML
    TextField emailRegisterField;

    @FXML
    Label registerStatusLabel;

    //this will update the User object and will open the Main Menu Window
    @FXML
    public void registerUser() throws IOException {
        user = new User(
                usernameRegisterField.getText(), passwordRegisterField.getText(), emailRegisterField.getText()
        );
        usernameRegisterField.clear();
        passwordRegisterField.clear();
        emailRegisterField.clear();
        registerStatusLabel.setText("Registered Successfully!");
        closeRegisterWindow();
        openMainMenuWindow();
    }

    private void openMainMenuWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Register.class.getResource("MainMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Chat App");
        stage.setScene(scene);
        stage.show();
    }

    private void closeRegisterWindow(){
        ((Stage)usernameRegisterField.getScene().getWindow()).close();
    }

}
