module com.socketprogramming.networkingjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.socketprogramming.networkingjavafx to javafx.fxml, com.google.gson;
    exports com.socketprogramming.networkingjavafx;
}