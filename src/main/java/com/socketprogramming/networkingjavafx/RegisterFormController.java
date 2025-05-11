package com.socketprogramming.networkingjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    @FXML
    public void registerUser(){
        user = new User(
                usernameRegisterField.getText(), passwordRegisterField.getText(), emailRegisterField.getText()
        );
    }

}
