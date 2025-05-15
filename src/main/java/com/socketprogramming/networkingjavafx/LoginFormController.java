package com.socketprogramming.networkingjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    //Database
    private DatabaseConnection databaseConnection;
    private LoginRegisterDB loginRegisterDB;

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
        user = loginRegisterDB.loginUser(usernameLoginField.getText(), passwordLoginField.getText());
        if(user == null){
            loginStatusLabel.setText("Login failed");
            clearLoginFields();
            return;
        }
        closeLoginWindow();
        openMainMenuWindow();
    }

    //Methods
    private void openMainMenuWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Register.class.getResource("MainMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Chat App");
        stage.setScene(scene);
        stage.show();
    }
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
        try {
            databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database!");
        }
        loginRegisterDB = new LoginRegisterDB(databaseConnection);
    }
}
