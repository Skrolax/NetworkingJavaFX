package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReceiveUserData extends Thread{

    private Socket socket;
    public User user;

    ReceiveUserData(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        ObjectInputStream receive = null;
        while(user == null) {
            try {
                receive = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Error occurred creating the ObjectInputStream");
            }
            try {
                assert receive != null;
                user = (User) receive.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error occurred receiving the User object");
            }
        }
        try {
            assert receive != null;
            receive.close();
        } catch (IOException e) {
            System.out.println("Couldn't close the ObjectInputStream");
        }
    }
}
