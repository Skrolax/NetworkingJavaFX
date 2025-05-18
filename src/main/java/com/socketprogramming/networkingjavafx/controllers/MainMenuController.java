package com.socketprogramming.networkingjavafx.controllers;

import com.google.gson.Gson;
import com.socketprogramming.networkingjavafx.client.ClientReceiveMessages;
import com.socketprogramming.networkingjavafx.ui.UI;
import com.socketprogramming.networkingjavafx.client.User;
import com.socketprogramming.networkingjavafx.common.ImageBase64;
import com.socketprogramming.networkingjavafx.common.RequestType;
import com.socketprogramming.networkingjavafx.database.DBAccess;
import com.socketprogramming.networkingjavafx.messages.FriendRequest;
import com.socketprogramming.networkingjavafx.messages.ImageMessage;
import com.socketprogramming.networkingjavafx.messages.TextMessage;
import com.socketprogramming.networkingjavafx.ui.UIFunctions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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

    //UI Classes objects
    private UI ui;
    private UIFunctions uiFunctions;

    //Message Box Variables
    private final HBox messageHBox = new HBox();
    private final TextField messagePromptField = new TextField();
    private final Button openFileButton = new Button("Open");
    private final Label fileNameLabel = new Label();
    private final Button sendMessageButton = new Button("Send");
    private final VBox messageArea = new VBox();



    //FXML variables
    @FXML
    BorderPane appBorderPane;
    @FXML
    TextField addFriendField;
    @FXML
    Button addFriendButton;
    @FXML
    VBox friendList;

    //Constructor
    public MainMenuController(Stage stage){
        user = (RegisterFormController.user != null) ? RegisterFormController.user : LoginFormController.user;
        gson = new Gson();
        this.stage = stage;
    }

    //Methods
    public void selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(new Stage());
        fileNameLabel.setText(imageFile.getName());
    }

    //Friend list
    private void selectFriend(Button button){
        button.setOnAction(e -> {
            selectedFriend = button.getText();
            UI.openChat(messageHBox, messageArea, messagePromptField, sendMessageButton, fileNameLabel, openFileButton);
            UIFunctions.activateSendMessageFunction(button, user, selectedFriend, messageArea, imageFile, messagePromptField, fileNameLabel);
        });
    }

    private void updateFriendList(String friend){
        Button button = new Button(friend);
        UI.setFriendButtonWidth(button, friendList);
        selectFriend(button);
        friendList.getChildren().add(button);
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

        //UI
        ui = new UI(appBorderPane);
        uiFunctions = new UIFunctions(send);

        //Get the friend list
        try {
            friends = DBAccess.getFriends(user);
        } catch (SQLException e) {
            System.out.println("Couldn't get the friend list");
        }

        //Update the friend list
        assert friends != null;
        for(String friend : friends){
            updateFriendList(friend);
        }

    }

    //Initialize methods
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
