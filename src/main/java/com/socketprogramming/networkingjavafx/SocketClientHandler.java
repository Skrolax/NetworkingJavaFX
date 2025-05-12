package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketClientHandler {

    private final Socket socket;
    private final ReceiveMessages receiveMessagesThread;
    public User userData;
    private final ObjectInputStream receive;

    private void receiveUser(){
        try {
            assert receive != null;
            userData = (User) receive.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't receive the User object");
        }
    }

    SocketClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        receive = new ObjectInputStream(socket.getInputStream());
        receiveUser();
        receiveMessagesThread = new ReceiveMessages(socket, receive);
        receiveMessagesThread.start();
    }

}
