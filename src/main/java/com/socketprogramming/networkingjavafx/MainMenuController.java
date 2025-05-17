package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private Stage stage;
    private final User user;
    private final Gson gson;
    private File imageFile = null;
    private ArrayList<String> friends;
    private String selectedFriend;

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

    //Constructor
    MainMenuController(Stage stage){
        user = (RegisterFormController.user != null) ? RegisterFormController.user : LoginFormController.user;
        gson = new Gson();
        this.stage = stage;
    }

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

    private void initializeObjectStreams() throws IOException {
        send = new ObjectOutputStream(socket.getOutputStream());
        receive = new ObjectInputStream(socket.getInputStream());
    }

    private void startReceiveMessageThread() throws IOException {
        clientReceiveMessagesThread = new ClientReceiveMessages(socket, receive, messageArea);
        clientReceiveMessagesThread.start();
    }

    private void selectFriend(Button button){
        button.setOnAction(e -> {
            selectedFriend = button.getText();
            messageArea.getChildren().removeAll();
        });
    }

    private void updateFriendList(String friend){
        Button button = new Button(friend);
        selectFriend(button);
        friendList.getChildren().add(button);
    }

    //FXML methods
    @FXML
    public void sendTextMessage() throws IOException {
        if(imageFile != null){
            ImageMessage imageMessage = new ImageMessage(
                    ImageBase64.encodeImageToBase64(imageFile), user.getUsername(), selectedFriend, RequestType.IMAGEMESSAGE
            );
            send.writeObject(gson.toJson(imageMessage));
            UI.createImageView(imageFile, messageArea, true);
            imageFile = null;
        }
        else {
            TextMessage textMessage = new TextMessage(
                    messagePromptField.getText(), user.getUsername(), selectedFriend
            );
            send.writeObject(gson.toJson(textMessage));
            UI.createTextMessageLabel(
                    textMessage.getAuthorUsername() + ": " + textMessage.getMessage() + "\n",
                    messageArea,
                    true
            );
        }
        messagePromptField.clear();
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

        //Set window title
        stage.setTitle(user.getUsername());

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
