package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;

public class ClientReceiveMessages extends IOThread{

    //Misc.
    private VBox messageArea;

    //Constructor
    ClientReceiveMessages(Socket socket, ObjectInputStream receive, VBox messageArea) {
        super(socket, receive);
        this.messageArea = messageArea;
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
                UI.createTextMessageLabel(
                        textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n",
                        messageArea
                );
                Platform.runLater(()->{
                    try {
                       DataSaving.updateConversation(textMessage.getReceiverUsername(), textMessage.getAuthorUsername(), messageArea);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else if (Objects.equals(type, Objects.toString(RequestType.IMAGEMESSAGE))) {
                ImageMessage imageMessage = gson.fromJson(messageJSON, ImageMessage.class);
                try {
                    UI.createImageView(
                            new Image(new ByteArrayInputStream(ImageBase64.decodeBase64ToImage(imageMessage.getImageBase64()))),
                            messageArea,
                            imageMessage.getAuthorUsername()
                    );
                    Platform.runLater(()->{
                        try {
                            DataSaving.updateConversation(imageMessage.getReceiverUsername(), imageMessage.getAuthorUsername(), messageArea);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Couldn't convert the Base64 String to File");
                }
            }
            else if (Objects.equals(type, Objects.toString(RequestType.FRIENDREQUEST))){
                FriendRequest friendRequest = gson.fromJson(messageJSON, FriendRequest.class);
                System.out.println("Received a friend request from: " + friendRequest.getAuthorUsername());
                try {
                    DBAccess.addFriend(friendRequest);
                } catch (SQLException e) {
                    System.out.println("Couldn't add the friend");
                }
            }
        }
    }

}
