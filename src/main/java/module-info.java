module com.socketprogramming.networkingjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;

    opens com.socketprogramming.networkingjavafx to javafx.fxml, com.google.gson, java.io;
    exports com.socketprogramming.networkingjavafx;
}