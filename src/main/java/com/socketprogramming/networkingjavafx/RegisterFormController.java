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

public class RegisterFormController implements Initializable {

    //Database
    private DatabaseConnection databaseConnection;
    private DBAccess DBAccess;

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
        openMainMenuWindow();
    }

    //Methods
    private void openMainMenuWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Register.class.getResource("MainMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 939, 648);
        stage.setTitle("Chat App");
        stage.setScene(scene);
        stage.show();
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
        try {
            databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database!");
        }
        DBAccess = new DBAccess(databaseConnection);
    }
}
