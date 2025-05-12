package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ServerIO extends Thread{

    private Socket socket;
    private ObjectInputStream receive;
    private ObjectOutputStream send;
    private Gson gson = new Gson();


    ServerIO(Socket socket, ObjectInputStream receive) throws IOException {
        this.socket = socket;
        this.receive = receive;
    }

    @Override
    public void run(){
        while(true){
            try {
                String messageJSON = (String) receive.readObject();
                Message message = gson.fromJson(messageJSON, Message.class);
                for(SocketClientHandler client : Server.clients){
                    if(Objects.equals(client.getUserData().getUsername(), message.getReceiverID())){
                        client.send.writeObject(message.getMessage());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
        }
    }

}
