package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ServerIOThread extends Thread{

    //Socket
    private Socket socket;
    private ObjectInputStream receive;

    //Misc.
    private Gson gson = new Gson();

    //Constructor
    ServerIOThread(Socket socket, ObjectInputStream receive) throws IOException {
        this.socket = socket;
        this.receive = receive;
    }

    //Thread's method
    @Override
    public void run(){
        while(true){
            try {
                String messageJSON = (String) receive.readObject();
                Message message = gson.fromJson(messageJSON, Message.class);
                for(SocketClientHandler client : Server.clients){
                    if(Objects.equals(client.getUserData().getUsername(), message.getReceiverID())){
                        client.send.writeObject(message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
        }
    }

}
