package com.socketprogramming.networkingjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ServerSocket server;
    static Socket socket;
    static User userData;
    static ArrayList<SocketClientHandler> clients = new ArrayList<>();


    public static void startServer(){
        try {
            server = new ServerSocket(5555);
        } catch (Exception e){
            System.out.println("Server failed to start");
        }
    }

    public static void getClientUserData(Socket socket){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ObjectInputStream getData = null;
                try {
                    getData = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    System.out.println("Cannot open ObjectOutputStream");
                }
                try {
                    assert getData != null;
                    userData = (User) getData.readObject();
                } catch (Exception e) {
                    System.out.println("Cannot send user to server");
                }
                try {
                    getData.close();
                } catch (IOException e) {
                    System.out.println("Cannot close the ObjectInputStream");
                }
            }
        }).start();

    }

    public static void main(String[] args) throws IOException {
        startServer();
        while(true){
            socket = server.accept();
            SocketClientHandler client = new SocketClientHandler(socket);
            getClientUserData(socket);
            clients.add(client);
        }
    }
}
