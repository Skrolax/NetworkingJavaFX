package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientHandler {

    //Socket
    private final Socket socket;
    public ObjectInputStream receive;
    public ObjectOutputStream send;

    //Threads
    private ServerIOThread serverIOThread;

    //Misc.
    private User userData;
    public User getUserData() {
        return userData;
    }

    //Constructor
    SocketClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        initializeObjectStreams();
        receiveUser();
        startReceiveMessageThread();
    }

    private void initializeObjectStreams() throws IOException {
        receive = new ObjectInputStream(socket.getInputStream());
        send = new ObjectOutputStream(socket.getOutputStream());
    }

    private void receiveUser(){
        try {
            assert receive != null;
            userData = (User) receive.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't receive the User object");
        }
    }

    private void startReceiveMessageThread() throws IOException {
        serverIOThread = new ServerIOThread(socket, receive);
        serverIOThread.start();
    }

}
