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
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    //Database
    private DatabaseConnection databaseConnection;
    private PreparedStatement sqlStatement;
    ResultSet loginResultSet;

    //Misc.
    static User user;
    private int loginStatus;

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
        createLoginStatementDB();
        if(loginStatus == 0){
            loginStatusLabel.setText("Error occurred while logging in");
            clearLoginFields();
            return;
        }
        if(!verifyUserdata()) {
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
    private void createLoginStatementDB() throws SQLException {
        sqlStatement.setString(1, usernameLoginField.getText());
        loginResultSet = sqlStatement.executeQuery();

        if(!loginResultSet.next()){
            loginStatus = 0;
            return;
        }

        user = new User(
                loginResultSet.getString("Username"), loginResultSet.getString("UserPassword"), loginResultSet.getString("Email")
        );

    }
    private void clearLoginFields(){
        usernameLoginField.clear();
        passwordLoginField.clear();
    }
    private boolean verifyUserdata(){
        if(!Objects.equals(usernameLoginField.getText(), user.getUsername())){
            loginStatusLabel.setText("Invalid username or password");
            return false;
        }
        if(!Objects.equals(passwordLoginField.getText(), user.getPassword())){
            loginStatusLabel.setText("Invalid username or password");
            return false;
        }
        return true;
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
                    "SELECT * FROM users WHERE Username = ?"
            );
        } catch (SQLException e) {
            System.out.println("Couldn't create the SQL Statement");
        }
    }
}
