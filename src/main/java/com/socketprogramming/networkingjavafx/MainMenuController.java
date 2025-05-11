package com.socketprogramming.networkingjavafx;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private final User user = RegisterFormController.user;
    private Socket socket;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connectToServer();
        } catch (Exception e) {
            System.out.println("Couldn't connect to the server");
        }
        try {
            sendUserData();
        } catch (IOException e) {
            System.out.println("Couldn't send the User data");
        }
    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 5555);
    }
    private void sendUserData() throws IOException {
        ObjectOutputStream send = null;
        try {
            send = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error occurred creating the ObjectOutputStream");
        }
        try {
            assert send != null;
            send.writeObject(user);
            send.flush();
        } catch (IOException e) {
            System.out.println("Error occurred sending the User object");
        }
        send.close();
    }

}
