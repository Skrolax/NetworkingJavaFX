module com.socketprogramming.networkingjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;

    opens com.socketprogramming.networkingjavafx to javafx.fxml, com.google.gson, java.io;
    exports com.socketprogramming.networkingjavafx;
    exports com.socketprogramming.networkingjavafx.messages;
    opens com.socketprogramming.networkingjavafx.messages to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.database;
    opens com.socketprogramming.networkingjavafx.database to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.controllers;
    opens com.socketprogramming.networkingjavafx.controllers to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.client;
    opens com.socketprogramming.networkingjavafx.client to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.server;
    opens com.socketprogramming.networkingjavafx.server to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.common;
    opens com.socketprogramming.networkingjavafx.common to com.google.gson, java.io, javafx.fxml;
    exports com.socketprogramming.networkingjavafx.ui;
    opens com.socketprogramming.networkingjavafx.ui to com.google.gson, java.io, javafx.fxml;
}