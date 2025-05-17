package com.socketprogramming.networkingjavafx.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    protected IOThread(Socket socket, ObjectInputStream receive) {
        this.socket = socket;
        this.receive = receive;
    }

}
