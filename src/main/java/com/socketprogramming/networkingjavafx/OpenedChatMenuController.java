package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class OpenedChatMenuController implements Initializable {

    //I don't usually open myself too much, but god, if this son of a bitch program doesn't work the way I intended, then we'll have a very, very serious problem
    //I'm done playing around. I want all this to be over, so, please, tell me, HOW THE FUCK DO I CREATE MY MESSAGES SAVING SYSTEM??? HOW?

    //Socket
    private final Socket socket = StartupMenuController.getSocket();
    private final ObjectOutputStream send = StartupMenuController.getSend();
    private final ObjectInputStream receive = StartupMenuController.getReceive();

    //Threads
    ClientReceiveMessages clientReceiveMessagesThread;

    //Misc.
    private final User user = StartupMenuController.getUser();
    private final Gson gson = new Gson();
    private File imageFile = null;
    private ArrayList<String> friends;
    private String selectedFriend = StartupMenuController.getSelectedFriend();

    //FXML variables
    @FXML
    Label fileNameLabel;
    @FXML
    TextField messagePromptField;
    @FXML
    TextField addFriendField;
    @FXML
    Button sendMessageButton;
    @FXML
    Button addFriendButton;
    @FXML
    Button openFileButton;
    @FXML
    VBox friendList;
    @FXML
    VBox messageArea;
    @FXML
    VBox selectFriendVBox;
    @FXML
    VBox sendImageVBox;
    @FXML
    ImageView sendImageView;
    @FXML
    ScrollPane scrollPane;


    public void selectFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(new Stage());
        if(imageFile != null) {
            sendImageView.setImage(new Image(imageFile.toURI().toString()));
            sendImageView.setFitHeight(200);
        }
        else{
            sendImageView.setImage(null);
            sendImageView.setFitHeight(0);
        }
    }

    private void startReceiveMessageThread() throws IOException {
        clientReceiveMessagesThread = new ClientReceiveMessages(socket, receive, messageArea);
        clientReceiveMessagesThread.start();
    }

    private void sendTextMessage() throws IOException {
        TextMessage textMessage = new TextMessage(
                messagePromptField.getText(), user.getUsername(), selectedFriend
        );
        send.writeObject(gson.toJson(textMessage));
        UI.createTextMessageLabel(
                textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n",
                messageArea
        );
    }

    private void sendImageMessage() throws IOException {
        ImageMessage imageMessage = new ImageMessage(
                ImageBase64.encodeImageToBase64(imageFile), user.getUsername(), selectedFriend, RequestType.IMAGEMESSAGE
        );
        send.writeObject(gson.toJson(imageMessage));
        UI.createImageView(imageFile, messageArea, imageMessage.getAuthorUsername());
        imageFile = null;
    }

    //FXML methods
    @FXML
    public void sendMessage() throws IOException {
        if(imageFile == null && Objects.equals(messagePromptField.getText(), "")){
            return;
        }

        if(imageFile != null){
            sendImageMessage();
            if(!Objects.equals(messagePromptField.getText(), "")){
                sendTextMessage();
            }
        }
        else {
            sendTextMessage();
        }
        messagePromptField.clear();
        sendImageView.setImage(null);
        sendImageView.setFitHeight(0);
        Platform.runLater(()->{
            try {
                DataSaving.updateConversation(user.getUsername(), selectedFriend, messageArea);
            } catch (IOException e) {
                System.out.println("Couldn't save the messages!");
            }
        });
        DataSaving.updateConversation(user.getUsername(), selectedFriend, messageArea);
    }

    private void updateFriendList(String friend) {
        Button button = UI.createFriendButton(friend, selectFriendVBox);
        button.setOnAction(actionEvent -> {
            selectedFriend = friend;

            //the code down below is a placeholder for the whole saving messages system
            messageArea.getChildren().removeAll(messageArea.getChildren());
            try {
                DataSaving.loadConversation(user.getUsername(), friend);
            } catch (IOException e) {
                System.out.println("Couldn't load the conversation");
            }
        });
    }

    @FXML
    public void sendFriendRequest() throws IOException {
        FriendRequest friendRequest = new FriendRequest(user.getUsername(), addFriendField.getText());
        send.writeObject(gson.toJson(friendRequest));
        updateFriendList(addFriendField.getText());
        addFriendField.clear();
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Starting ReceiveMessage Thread
        try {
            startReceiveMessageThread();
        } catch (IOException e) {
            System.out.println("Couldn't start the ClientReceiveMessage thread");
        }

        //Get the friend list
        try {
            friends = DBAccess.getFriends(user);
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Couldn't get the friend list");
        }

        //Update the friend list
        assert friends != null;
        for(String friend : friends){
            updateFriendList(friend);
        }

        new UI(scrollPane);

    }

}
