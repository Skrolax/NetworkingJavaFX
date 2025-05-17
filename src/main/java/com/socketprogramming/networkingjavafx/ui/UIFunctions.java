package com.socketprogramming.networkingjavafx.ui;

import com.google.gson.Gson;
import com.socketprogramming.networkingjavafx.client.User;
import com.socketprogramming.networkingjavafx.common.ImageBase64;
import com.socketprogramming.networkingjavafx.common.RequestType;
import com.socketprogramming.networkingjavafx.messages.ImageMessage;
import com.socketprogramming.networkingjavafx.messages.TextMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UIFunctions {

    private static ObjectOutputStream send;
    private static Gson gson = new Gson();

    public UIFunctions(ObjectOutputStream send){
        this.send = send;
    }

    public static void activateSendMessageFunction(Button button, User user, String selectedFriend, VBox messageArea, File imageFile, TextField messagePromptField, Label fileNameLabel){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                button.setOnAction(actionEvent -> {
                    if(imageFile != null){
                        ImageMessage imageMessage = null;
                        try {
                            imageMessage = new ImageMessage(
                                    ImageBase64.encodeImageToBase64(imageFile), user.getUsername(), selectedFriend, RequestType.IMAGEMESSAGE
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            send.writeObject(gson.toJson(imageMessage));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        UI.createImageView(imageFile, messageArea, imageMessage.getAuthorUsername(),true);
                        fileNameLabel.setText("");
                    }
                    else {
                        TextMessage textMessage = new TextMessage(
                                messagePromptField.getText(), user.getUsername(), selectedFriend
                        );
                        try {
                            send.writeObject(gson.toJson(textMessage));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        UI.createTextMessageLabel(
                                textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n",
                                messageArea,
                                true
                        );
                    }
                    messagePromptField.clear();
                });
            }
        });
    }

}
