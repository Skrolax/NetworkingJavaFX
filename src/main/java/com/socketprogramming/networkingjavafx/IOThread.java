package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public abstract class IOThread extends Thread {

    //Socket
    public Socket socket;
    public ObjectInputStream receive;

    //Misc.
    public final Gson gson = new Gson();

    //Methods
    public String getServerRequestType(String messageJSON){
        JsonObject jsonObject = JsonParser.parseString(messageJSON).getAsJsonObject();
        return jsonObject.get("requestType").getAsString();
    }

    //Constructor
    IOThread(Socket socket, ObjectInputStream receive) {
        this.socket = socket;
        this.receive = receive;
    }

}
