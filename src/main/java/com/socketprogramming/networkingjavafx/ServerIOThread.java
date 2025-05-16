package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

public class ServerIOThread extends IOThread{

    //Constructor
    ServerIOThread(Socket socket, ObjectInputStream receive) {
        super(socket, receive);
    }

    //Thread's method
    @Override
    public void run(){
        while(true){
            try {
                String messageJSON = (String) receive.readObject();
                String type = getServerRequestType(messageJSON);

                if (Objects.equals(type, Objects.toString(RequestType.TEXTMESSAGE))) {
                    TextMessage textMessage = gson.fromJson(messageJSON, TextMessage.class);
                    for(SocketClientHandler client : Server.clients){
                        if(Objects.equals(client.getUserData().getUsername(), textMessage.getReceiverUsername())){
                            client.send.writeObject(gson.toJson(textMessage));
                        }
                    }
                }
                else if (Objects.equals(type, Objects.toString(RequestType.IMAGEMESSAGE))) {
                    ImageMessage imageMessage = gson.fromJson(messageJSON, ImageMessage.class);
                    //TODO
                }
                else if (Objects.equals(type, Objects.toString(RequestType.FRIENDREQUEST))){
                    FriendRequest friendRequest = gson.fromJson(messageJSON, FriendRequest.class);
                    for(SocketClientHandler client : Server.clients){
                        if(Objects.equals(client.getUserData().getUsername(), friendRequest.getReceiverUsername())){
                            client.send.writeObject(messageJSON);
                        }
                    }
                    //TODO
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
        }
    }

}
