
package com.socketprogramming.networkingjavafx;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.File;

public class UI{

    public static Button createFriendButton(String friend, VBox showcaseFriendsVBox){
        Button button = new Button(friend);
        button.prefWidthProperty().bind(showcaseFriendsVBox.widthProperty());
        showcaseFriendsVBox.getChildren().add(button);
        return button;
    }

    public static void createTextMessageLabel(String message, VBox messageArea){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label(message);
                label.setWrapText(true);
                VBox vBox = new VBox(label);
                vBox.getStyleClass().add("message-box");
                vBox.setAlignment(Pos.CENTER_LEFT);
                messageArea.getChildren().add(vBox);
            }
        });

    }

    public static void createImageView(File fileImage, VBox messageArea, String authorUsername){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = new ImageView(new Image(fileImage.toURI().toString()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(200);
                imageView.setFitWidth(400);
                VBox vBox = new VBox(new Label(authorUsername + ": \n"));
                vBox.getStyleClass().add("message-box");
                vBox.getChildren().add(imageView);
                vBox.setAlignment(Pos.CENTER_LEFT);
                messageArea.getChildren().add(vBox);
            }
        });
    }

    public static void createImageView(Image image, VBox displayMedium, String authorUsername){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(200);
                imageView.setFitWidth(400);
                VBox vBox = new VBox(new Label(authorUsername + ": \n"));
                vBox.getStyleClass().add("message-box");
                vBox.getChildren().add(imageView);
                vBox.setAlignment(Pos.CENTER_LEFT);
                displayMedium.getChildren().add(vBox);
            }
        });
    }

    public static void setHBoxResizable(HBox hBox){
        HBox.setHgrow(hBox, Priority.ALWAYS);
    }

    public static void setVBoxResizable(VBox vBox){
        VBox.setVgrow(vBox, Priority.ALWAYS);
    }

}

