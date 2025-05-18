package com.socketprogramming.networkingjavafx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class DBAccess {

    //Database
    private static DatabaseConnection databaseConnection;
    static {
        try {
            databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database");
        }
    }
    private static PreparedStatement sqlStatement;
    private static ResultSet resultSet;

    // Register
    public static boolean registerUser(User user) throws SQLException {
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "INSERT INTO users (Username, UserPassword, Email) VALUES (?, ?, ?)"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query\n");
            System.out.println(e);
            return false;
        }
        sqlStatement.setString(1, user.getUsername());
        sqlStatement.setString(2, user.getPassword());
        sqlStatement.setString(3, user.getEmail());
        sqlStatement.executeUpdate();
        return true;
    }

    // Login
    public static User loginUser(String username, String password) throws SQLException {
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
        if(!resultSet.next()) {
            return null;
        }
        user = new User(
                resultSet.getString("Username"),
                resultSet.getString("UserPassword"),
                resultSet.getString("Email")
        );
        if(!Objects.equals(user.getPassword(), password)){
            return null;
        }
        return user;
    }

    // Friends

    public static ArrayList<String> getFriends(User user) throws SQLException {
        ArrayList<String> friends = new ArrayList<>();
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "SELECT * FROM friends WHERE Username = ?"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query");
            return null;
        }
        sqlStatement.setString(1, user.getUsername());
        resultSet = sqlStatement.executeQuery();
        while(resultSet.next()){
            friends.add(resultSet.getString("Friend_Username"));
        }
        return friends;
    }

    public static boolean addFriend(FriendRequest friendRequest) throws SQLException {
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "INSERT INTO friends (Username, Friend_Username) VALUES (?, ?)"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query");
            return false;
        }

        //The first user has to be friends with the other user and the other user has to be friends with the user

        sqlStatement.setString(1, friendRequest.getReceiverUsername());
        sqlStatement.setString(2, friendRequest.getAuthorUsername());
        sqlStatement.executeUpdate();

        sqlStatement.setString(2, friendRequest.getReceiverUsername());
        sqlStatement.setString(1, friendRequest.getAuthorUsername());
        sqlStatement.executeUpdate();

        return true;
    }

    public static boolean removeFriend(String username, String friendUsername) throws SQLException {
        try {
            sqlStatement = databaseConnection.getConnection().prepareStatement(
                    "DELETE FROM friends WHERE Username = ? AND Friend_Username = ?"
            );
        }catch (Exception e){
            System.out.println("Couldn't create the PreparedStatement query");
            return false;
        }
        sqlStatement.setString(1, username);
        sqlStatement.setString(2, friendUsername);
        sqlStatement.executeUpdate();
        return true;
    }

}
