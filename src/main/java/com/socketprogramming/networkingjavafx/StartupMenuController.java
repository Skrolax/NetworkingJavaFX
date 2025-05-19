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

public class StartupMenuController implements Initializable {

    //Socket
    private Socket socket;
    private ObjectOutputStream send;

    //Threads
    ClientReceiveMessages clientReceiveMessagesThread;

    //Misc.
    private static final User user  = (RegisterFormController.user != null) ? RegisterFormController.user : LoginFormController.user;
    private final Gson gson = new Gson();
    private ArrayList<String> friends;
    private static String selectedFriend;

    public static String getSelectedFriend(){
        return selectedFriend;
    }

    public static User getUser(){
        return user;
    }

    public Stage getStage(){
        return (Stage) addFriendButton.getScene().getWindow();
    }

    //FXML variables
    @FXML
    TextField addFriendField;
    @FXML
    Button addFriendButton;
    @FXML
    VBox friendList;

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
    }

    private void selectFriend(Button button){
        button.setOnAction(open -> {
            selectedFriend = button.getText();
            try {
                JavafxStageManager.changeScene(getStage(), "OpenedChatMenuView.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateFriendList(String friend){
        Button button = new Button(friend);
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
