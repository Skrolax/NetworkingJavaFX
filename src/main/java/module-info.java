module com.socketprogramming.networkingjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens com.socketprogramming.networkingjavafx to javafx.fxml, com.google.gson;
    exports com.socketprogramming.networkingjavafx;
}