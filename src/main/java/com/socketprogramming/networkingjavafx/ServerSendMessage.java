package com.socketprogramming.networkingjavafx;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSendMessage {

    Socket socket;
    ObjectOutputStream send;

    ServerSendMessage(Socket socket, ObjectOutputStream send){
        this.socket = socket;
        this.send = send;
    }

}
