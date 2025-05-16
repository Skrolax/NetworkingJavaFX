package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    //Socket
    private Socket socket;
    private ObjectOutputStream send;
    private ObjectInputStream receive;

    //Threads
    ClientReceiveMessages clientReceiveMessagesThread;

    //Misc.
    private final User user = (RegisterFormController.user != null) ? RegisterFormController.user : LoginFormController.user;
    private final Gson gson = new Gson();
    private File imageFile = null;

    //FXML variables
    @FXML
    Label fileNameLabel;
    @FXML
    TextField messagePromptField;
    @FXML
    TextField senderUsernameField;
    @FXML
    TextField addFriendField;
    @FXML
    Button sendMessageButton;
    @FXML
    Button addFriendButton;
    @FXML
    Button openFileButton;
    @FXML
    TextArea messageArea;

    //FXML methods
    @FXML
    public void sendTextMessage() throws IOException {
        if(imageFile != null){
            ImageMessage imageMessage = new ImageMessage(
                    ImageBase64.encodeImageToBase64(imageFile), user.getUsername(), senderUsernameField.getText(), RequestType.IMAGEMESSAGE
            );
            send.writeObject(gson.toJson(imageMessage));
        }
        else {
            TextMessage textMessage = new TextMessage(
                    messagePromptField.getText(), user.getUsername(), senderUsernameField.getText()
            );

            send.writeObject(gson.toJson(textMessage));
            messageArea.appendText(textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n");
        }
        messagePromptField.clear();
        senderUsernameField.clear();
    }

    public void selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(new Stage());
    }

    @FXML
    public void sendFriendRequest() throws IOException {
        FriendRequest friendRequest = new FriendRequest(user.getUsername(), addFriendField.getText());
        send.writeObject(gson.toJson(friendRequest));
        addFriendField.clear();
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Server Connection
        try {
            connectToServer();
        } catch (Exception e) {
            System.out.println("Couldn't connect to the server");
        }

        //Object Streams
        try {
            initializeObjectStreams();
        } catch (IOException e) {
            System.out.println("Couldn't initialize the Object Streams");
        }

        //Sending user data
        try {
            sendUserData();
        } catch (IOException e) {
            System.out.println("Couldn't send the User data");
        }

        //Starting ReceiveMessage Thread
        try {
            startReceiveMessageThread();
        } catch (IOException e) {
            System.out.println("Couldn't start the ClientReceiveMessage thread");
        }

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }

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

    private void initializeObjectStreams() throws IOException {
        send = new ObjectOutputStream(socket.getOutputStream());
        receive = new ObjectInputStream(socket.getInputStream());
    }

    private void startReceiveMessageThread() throws IOException {
        clientReceiveMessagesThread = new ClientReceiveMessages(socket, receive, messageArea);
        clientReceiveMessagesThread.start();
    }

}
