package com.socketprogramming.networkingjavafx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginRegisterDB {

    //Database
    private DatabaseConnection databaseConnection;
    private PreparedStatement sqlStatement;
    private ResultSet resultSet;

    LoginRegisterDB(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    // Register
    public boolean registerUser(User user) throws SQLException {
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "INSERT INTO users (Username, UserPassword, Email) VALUES (?, ?, ?)"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query");
            return false;
        }
        sqlStatement.setString(1, user.getUsername());
        sqlStatement.setString(2, user.getPassword());
        sqlStatement.setString(3, user.getEmail());
        sqlStatement.executeUpdate();
        return true;
    }



    // Login
    public User loginUser(String username, String password) throws SQLException {
        User user = null;
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE Username = ?"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query");
            return null;
        }
        sqlStatement.setString(1, username);
        resultSet = sqlStatement.executeQuery();
        if(resultSet.next()) {
            user = new User(
                    resultSet.getString("Username"),
                    resultSet.getString("UserPassword"),
                    resultSet.getString("Email")
            );
        }
        assert user != null;
        if(!Objects.equals(user.getPassword(), password)){
            return null;
        }
        return user;
    }

}
