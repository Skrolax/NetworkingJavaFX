package com.socketprogramming.networkingjavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    Connection databaseConnection;

    public DatabaseConnection() throws SQLException {
        databaseConnection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/?user=root&password=NoPasswordReally"
        );
    }
}
