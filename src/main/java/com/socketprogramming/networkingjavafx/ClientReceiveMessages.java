package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiveMessages extends Thread{

    //Socket
    private Socket socket;
    private ObjectInputStream receive;
    private TextArea messageArea;

    //Misc.
    private Gson gson = new Gson();

    //Constructor
    ClientReceiveMessages(Socket socket, ObjectInputStream receive, TextArea messageArea) throws IOException {
        this.socket = socket;
        this.receive = receive;
        this.messageArea = messageArea;
    }

    //Thread's method
    @Override
    public void run(){
        while(true){
            Message message = null;
            try {
                message = (Message) receive.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server couldn't receive the message");
            }
            assert message != null;
            messageArea.appendText(message.getAuthorID() + ": " + message.getMessage() + "\n");

        }
    }

}
