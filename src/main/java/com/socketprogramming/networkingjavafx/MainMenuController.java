package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private final User user = RegisterFormController.user;
    private Socket socket;
    private Gson gson = new Gson();
    private ObjectOutputStream send;

    @FXML
    TextField messagePrompt;
    @FXML
    Button sendMessageButton;

    @FXML
    public void sendMessage() throws IOException {
        Message message = new Message(messagePrompt.getText());
        send.writeObject(convertMessageToJSON(message));
        messagePrompt.clear();
    }


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
    }
    private String convertMessageToJSON(Message message){
        return gson.toJson(message);
    }

}
