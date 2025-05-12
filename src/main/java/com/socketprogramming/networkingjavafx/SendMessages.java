package com.socketprogramming.networkingjavafx;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessages extends Thread{

    private Socket socket;
    private TextField textField;
    private ObjectOutputStream sendMessage;

    SendMessages(Socket socket, TextField textField) throws IOException {
        this.socket = socket;
        this.textField = textField;
        sendMessage = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run(){
        while(true){
            try {
                sendMessage.writeObject(textField.getText());
                sendMessage.flush();
            } catch (IOException e) {
                System.out.println("Error caught sending the message to the server");
            }
        }
    }

}
