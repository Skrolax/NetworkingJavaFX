module com.socketprogramming.networkingjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.socketprogramming.networkingjavafx to javafx.fxml;
    exports com.socketprogramming.networkingjavafx;
}