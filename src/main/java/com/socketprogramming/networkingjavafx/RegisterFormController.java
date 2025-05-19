package com.socketprogramming.networkingjavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    //Misc.
    static User user;
    private int registerStatus;

    //FXML variables
    @FXML
    TextField usernameRegisterField;
    @FXML
    TextField passwordRegisterField;
    @FXML
    TextField emailRegisterField;
    @FXML
    Label registerStatusLabel;

    //FXML methods
    @FXML
    public void registerUser() throws IOException, SQLException { //this will update the User object and will open the Main Menu Window
        user = new User(
                usernameRegisterField.getText(), passwordRegisterField.getText(), emailRegisterField.getText()
        );
        if(!DBAccess.registerUser(user)){
            System.out.println("Couldn't register the user");
            clearRegisterFields();
            return;
        };
        closeRegisterWindow();
        JavafxStageManager.openNewWindow(new Stage(), "StartupMenuView.FXML");
    }

    private void closeRegisterWindow(){
        ((Stage)usernameRegisterField.getScene().getWindow()).close();
    }

    private void clearRegisterFields(){
        usernameRegisterField.clear();
        passwordRegisterField.clear();
        emailRegisterField.clear();
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
