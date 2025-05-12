package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerReceiveMessages extends Thread{

    private Socket socket;
    private ObjectInputStream receive;
    private Gson gson = new Gson();


    ServerReceiveMessages(Socket socket, ObjectInputStream receive) throws IOException {
        this.socket = socket;
        this.receive = receive;
    }

    @Override
    public void run(){
        while(true){
            try {
                System.out.println(receive.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
        }
    }

}
