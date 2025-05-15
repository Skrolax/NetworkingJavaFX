package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

public class ClientReceiveMessages extends Thread{

    //Socket
    private Socket socket;
    private ObjectInputStream receive;
    private TextArea messageArea;

    //Misc.
    private Gson gson = new Gson();

    //Constructor
    ClientReceiveMessages(Socket socket, ObjectInputStream receive, TextArea messageArea) throws IOException {
        this.socket = socket;
        this.receive = receive;
        this.messageArea = messageArea;
    }

    //Methods

    private String getServerRequestType(String messageJSON){
        JsonObject jsonObject = JsonParser.parseString(messageJSON).getAsJsonObject();
        return jsonObject.get("requestType").getAsString();
    }

    //Thread's method
    @Override
    public void run(){
        while(true){

            String messageJSON = null;
            String type = null;
            try {
                messageJSON = (String) receive.readObject();
                type = getServerRequestType(messageJSON);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Couldn't receive the Server Request Type");
            }

            if (Objects.equals(type, Objects.toString(RequestType.TEXTMESSAGE))) {
                TextMessage textMessage = gson.fromJson(messageJSON, TextMessage.class);
                messageArea.appendText(textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n");
            }
            else if (Objects.equals(type, Objects.toString(RequestType.IMAGEMESSAGE))) {
                ImageMessage imageMessage = gson.fromJson(messageJSON, ImageMessage.class);
                //TODO
            }
            else if (Objects.equals(type, Objects.toString(RequestType.FRIENDREQUEST))){
                FriendRequest friendRequest = gson.fromJson(messageJSON, FriendRequest.class);
                System.out.println("Received a friend request from: " + friendRequest.getAuthorUsername());
                //TODO
            }
        }
    }

}
