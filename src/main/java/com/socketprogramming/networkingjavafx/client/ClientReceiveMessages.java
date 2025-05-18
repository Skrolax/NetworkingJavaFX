package com.socketprogramming.networkingjavafx.client;

import com.socketprogramming.networkingjavafx.common.IOThread;
import com.socketprogramming.networkingjavafx.common.ImageBase64;
import com.socketprogramming.networkingjavafx.common.RequestType;
import com.socketprogramming.networkingjavafx.database.DBAccess;
import com.socketprogramming.networkingjavafx.messages.FriendRequest;
import com.socketprogramming.networkingjavafx.messages.ImageMessage;
import com.socketprogramming.networkingjavafx.messages.TextMessage;
import com.socketprogramming.networkingjavafx.ui.UI;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;

public class ClientReceiveMessages extends IOThread {

    //Misc.
    private VBox messageArea;

    //Constructor
    public ClientReceiveMessages(Socket socket, ObjectInputStream receive, VBox messageArea) {
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
                        messageArea,
                        false
                );
            }
            else if (Objects.equals(type, Objects.toString(RequestType.IMAGEMESSAGE))) {
                ImageMessage imageMessage = gson.fromJson(messageJSON, ImageMessage.class);
                try {
                    UI.createImageView(
                            new Image(new ByteArrayInputStream(ImageBase64.decodeBase64ToImage(imageMessage.getImageBase64()))),
                            messageArea,
                            imageMessage.getAuthorUsername(),
                            false
                    );
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
