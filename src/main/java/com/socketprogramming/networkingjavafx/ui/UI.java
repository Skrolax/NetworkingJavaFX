package com.socketprogramming.networkingjavafx.ui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;

public class UI{

    private static BorderPane borderPane;

    public UI(BorderPane borderPane){
        this.borderPane = borderPane;
    }

    public static void setFriendButtonWidth(Button button, VBox friendList){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                button.setMinWidth(friendList.getWidth());
            }
        });
    }

    public static void openChat(HBox sendMessageHBox, VBox messageArea, TextField textMessageField, Button selectImageButton, Label imageName, Button sendMessageButton){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                borderPane.setBottom(sendMessageHBox);
                borderPane.setCenter(messageArea);
                textMessageField.setPromptText("enter a message");
                sendMessageHBox.getChildren().add(textMessageField);
                sendMessageHBox.getChildren().add(selectImageButton);
                sendMessageHBox.getChildren().add(imageName);
                sendMessageHBox.getChildren().add(sendMessageButton);
            }
        });
    }

    public static void createTextMessageLabel(String message, VBox messageArea, boolean author){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label(message);
                label.setWrapText(true);
                HBox hBox = new HBox(label);
                if (author) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                }
                messageArea.getChildren().add(hBox);
            }
        });
    }

    public static void createImageView(File fileImage, VBox messageArea, String authorUsername, boolean author){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = new ImageView(new Image(fileImage.toURI().toString()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(200);
                imageView.setFitWidth(400);
                HBox hBox = new HBox(new Label(authorUsername + ": "));
                hBox.getChildren().add(imageView);
                if (author) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                }
                messageArea.getChildren().add(hBox);
            }
        });
    }

    public static void createImageView(Image image, VBox messageArea, String authorUsername, boolean author){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(200);
                imageView.setFitWidth(400);
                HBox hBox = new HBox(new Label(authorUsername + ": "));
                hBox.getChildren().add(imageView);
                if (author) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                }
                messageArea.getChildren().add(hBox);
            }
        });
    }

}

