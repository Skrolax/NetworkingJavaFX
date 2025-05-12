package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{

    //Socket
    static ServerSocket server;
    static Socket socket;

    //Misc.
    static ArrayList<SocketClientHandler> clients = new ArrayList<>();

    //Methods
    public static void startServer(){
        try {
            server = new ServerSocket(5555);
        } catch (Exception e){
            System.out.println("Server failed to start");
        }
    }

    //Main
    public static void main(String[] args) throws IOException {
        startServer();
        while(true){
            socket = server.accept();
            SocketClientHandler client = new SocketClientHandler(socket);
            clients.add(client);
            System.out.println(client.getUserData().getUsername() + " has connected.");
        }
    }
}
