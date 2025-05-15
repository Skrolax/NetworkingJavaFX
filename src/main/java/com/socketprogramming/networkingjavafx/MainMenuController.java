package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
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

    //FXML variables
    @FXML
    TextField messagePromptField;
    @FXML
    TextField senderIDField;
    @FXML
    Button sendMessageButton;
    @FXML
    TextArea messageArea;

    //FXML methods
    @FXML
    public void sendMessage() throws IOException {
        Message message = new Message(
                messagePromptField.getText(), user.getUsername(), senderIDField.getText()
        );
        send.writeObject(gson.toJson(message));
        messageArea.appendText(message.getAuthorID() + ": " + message.getMessage() + "\n");
        messagePromptField.clear();
        senderIDField.clear();
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
