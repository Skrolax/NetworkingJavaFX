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

public class LoginFormController implements Initializable {

    //Misc.
    static User user;

    //FXML variables
    @FXML
    TextField usernameLoginField;
    @FXML
    TextField passwordLoginField;
    @FXML
    Label loginStatusLabel;

    //FXML methods
    @FXML
    public void loginUser() throws IOException, SQLException { //this will update the User object and will open the Main Menu Window
        user = DBAccess.loginUser(usernameLoginField.getText(), passwordLoginField.getText());
        if(user == null){
            loginStatusLabel.setText("Login failed");
            clearLoginFields();
            return;
        }
        closeLoginWindow();
        JavafxStageManager.openNewWindow(new Stage(), "StartupMenuView.FXML");
    }

    //Methods

    private void closeLoginWindow(){
        ((Stage)usernameLoginField.getScene().getWindow()).close();
    }

    private void clearLoginFields(){
        usernameLoginField.clear();
        passwordLoginField.clear();
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
