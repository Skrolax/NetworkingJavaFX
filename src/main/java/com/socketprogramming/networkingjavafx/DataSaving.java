package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaving {

    //Firstly, the user joins and accesses the directory which is represented by their username
    //After the directory has been accessed, the user has now to select a friend to exchange messages with
    //So the main directory represented by their username will contain other directories represented by their friends username
    //Each directory will contain a JSON file where all the messages between each other are stored

    private static final String path = "src/main/resources/Message Logs/";
    private static FileWriter fileWriter;
    private static final Gson gson = new Gson();

    public static File createDirectory(String directoryName){
        File file = new File(path + directoryName);
        file.mkdir();
        return file;
    }

    public static boolean createJSON(String directoryName) throws IOException {
        try {
            fileWriter = new FileWriter(path + directoryName + ".json");
            return true;
        }catch (Exception e){
            System.out.println("Couldn't create the JSON file");
        }
        return false;
    }

    public static void updateConversation(String clientUsername, String friendUsername, VBox messageArea) throws IOException {
        fileWriter = new FileWriter(clientUsername + ".json");
        gson.toJson(messageArea, fileWriter);

        //It doesn't work
        //I have to access every single node of the messageArea so that I can serialize each of them separately
        //It's somewhat the same process as the system of detecting what kind of messages are being sent to the server

    }

    public static void loadConversation(String clientUsername, String friendUsername){
        //very important things will happen around here
        //like the messages will be all managed and manipulated around here
        //stay tuned
        //TODO
    }


}
