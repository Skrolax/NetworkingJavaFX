package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    //Socket
    private Socket socket;
    private ObjectOutputStream send;
    private ObjectInputStream receive;

    //Threads

    //Misc.
    private final User user = RegisterFormController.user;
    private final Gson gson = new Gson();

    //FXML variables
    @FXML
    TextField messagePrompt;
    @FXML
    Button sendMessageButton;

    //FXML methods
    @FXML
    public void sendMessage() throws IOException {
        Message message = new Message(messagePrompt.getText(), user.getUsername());
        send.writeObject(gson.toJson(message));
        messagePrompt.clear();
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

}
