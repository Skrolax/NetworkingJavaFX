
package com.socketprogramming.networkingjavafx;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.File;

public class UI{

    private static ScrollPane scrollPane;

    UI(ScrollPane scrollPane){
        this.scrollPane = scrollPane;
    }

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
                VBox.setVgrow(label, Priority.NEVER);
                vBox.getStyleClass().add("message-box");
                vBox.setAlignment(Pos.CENTER_LEFT);
                vBox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
                messageArea.getChildren().add(vBox);
            }
        });
    }

    public static void createImageView(File fileImage, VBox messageArea, String authorUsername) {
        Platform.runLater(() -> {
            Label authorLabel = new Label(authorUsername + ":");
            authorLabel.setWrapText(true);

            ImageView imageView = new ImageView(new Image(fileImage.toURI().toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(400);

            VBox vBox = new VBox(5);
            vBox.getChildren().addAll(authorLabel, imageView);
            vBox.getStyleClass().add("message-box");
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
            vBox.setMinHeight(Region.USE_PREF_SIZE);
            VBox.setVgrow(vBox, Priority.NEVER);
            vBox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
            messageArea.getChildren().add(vBox);
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
                VBox.setVgrow(imageView, Priority.NEVER);
                vBox.getStyleClass().add("message-box");
                vBox.getChildren().add(imageView);
                vBox.setAlignment(Pos.CENTER_LEFT);
                vBox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
                displayMedium.getChildren().add(vBox);
            }
        });
    }

}

