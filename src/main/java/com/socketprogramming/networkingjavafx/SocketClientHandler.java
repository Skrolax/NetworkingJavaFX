package com.socketprogramming.networkingjavafx;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SocketClientHandler{

    private Socket socket;
    public User userData;

    SocketClientHandler(Socket socket){
        this.socket = socket;
        receiveUser();
    }


    private void receiveUser(){
        ObjectInputStream receive = null;
        try {
            receive = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Couldn't create ObjectInputStream object");
        }
        try {
            assert receive != null;
            userData = (User) receive.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't receive the User object");
        }
        try {
            receive.close();
        } catch (IOException e) {
            System.out.println("Couldn't close the ObjectInputStream");
        }
    }
}
