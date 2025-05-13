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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    //Database
    private DatabaseConnection databaseConnection;
    private PreparedStatement sqlStatement;

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

        createRegisterStatementDB();

        if(registerStatus == 0){
            registerStatusLabel.setText("Error occurred while registering");
            clearRegisterFields();
            return;
        }

        closeRegisterWindow();
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
    private void closeRegisterWindow(){
        ((Stage)usernameRegisterField.getScene().getWindow()).close();
    }
    private void createRegisterStatementDB() throws SQLException {
        sqlStatement.setString(1, user.getUsername());
        sqlStatement.setString(2, user.getPassword());
        sqlStatement.setString(3, user.getEmail());
        registerStatus = sqlStatement.executeUpdate();
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
        assert databaseConnection != null;
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "INSERT INTO users (Username, UserPassword, Email) VALUES (?, ?, ?)"
            );
        } catch (SQLException e) {
            System.out.println("Couldn't create the SQL Statement");
        }
    }
}
