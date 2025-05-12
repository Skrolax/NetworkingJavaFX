package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiveMessages extends Thread{

    //Socket
    private Socket socket;
    private ObjectInputStream receive;

    //Misc.
    private Gson gson = new Gson();

    //Constructor
    ClientReceiveMessages(Socket socket, ObjectInputStream receive) throws IOException {
        this.socket = socket;
        this.receive = receive;
    }

    //Thread's method
    @Override
    public void run(){
        while(true){
            try {
                System.out.println((String)receive.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
        }
    }

}
