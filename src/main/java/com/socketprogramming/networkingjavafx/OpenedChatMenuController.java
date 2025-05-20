package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OpenedChatMenuController implements Initializable {

    //Socket
    private Socket socket = StartupMenuController.getSocket();
    private ObjectOutputStream send = StartupMenuController.getSend();
    private ObjectInputStream receive = StartupMenuController.getReceive();

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


    public void selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(new Stage());
        fileNameLabel.setText(imageFile.getName());
    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 5555);
    }

    private void sendUserData() throws IOException {
        try {
            assert send != null;
            send.writeObject(user);
            send.flush();
        } catch (IOException e) {
            System.out.println("Error occurred sending the User object");
        }
    }

    private void startReceiveMessageThread() throws IOException {
        clientReceiveMessagesThread = new ClientReceiveMessages(socket, receive, messageArea);
        clientReceiveMessagesThread.start();
    }

    //FXML methods
    @FXML
    public void sendTextMessage() throws IOException {
        if(imageFile != null){
            ImageMessage imageMessage = new ImageMessage(
                    ImageBase64.encodeImageToBase64(imageFile), user.getUsername(), selectedFriend, RequestType.IMAGEMESSAGE
            );
            send.writeObject(gson.toJson(imageMessage));
            UI.createImageView(imageFile, messageArea, imageMessage.getAuthorUsername());
            imageFile = null;
        }
        else {
            TextMessage textMessage = new TextMessage(
                    messagePromptField.getText(), user.getUsername(), selectedFriend
            );
            send.writeObject(gson.toJson(textMessage));
            UI.createTextMessageLabel(
                    textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n",
                    messageArea
            );
        }
        messagePromptField.clear();
    }

    private void selectFriend(Button button) {

    }

    private void updateFriendList(String friend) {
        Button button = UI.createFriendButton(friend, selectFriendVBox);
        selectFriend(button);
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

    }

}
