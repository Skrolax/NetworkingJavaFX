package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketClientHandler {

    private final Socket socket;
    public User userData;

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

    SocketClientHandler(Socket socket){
        this.socket = socket;
        receiveUser();
    }

}
