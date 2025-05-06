package com.socketprogramming.networkingjavafx;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SocketClientHandler implements Initializable {

    private Socket socket;
    private User user;


    public Socket getSocket() {
        return socket;
    }

    SocketClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObjectOutputStream returnUser = null;
        try {
            returnUser = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Cannot open ObjectOutputStream");
        }
        try {
            assert returnUser != null;
            returnUser.writeObject(user);
        } catch (IOException e) {
            System.out.println("Cannot send user to server");
        }
        try {
            returnUser.close();
        } catch (IOException e) {
            System.out.println("Cannot close the ObjectOutputStream");
        }
    }
}
